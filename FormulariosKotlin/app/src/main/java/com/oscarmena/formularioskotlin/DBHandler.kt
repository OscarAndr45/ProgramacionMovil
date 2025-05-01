package com.oscarmena.formularioskotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.ArrayList

class DBHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "oscarmena"
        const val TABLE_ESTUDIANTE = "estudiante"
        const val TABLE_CATEDRA = "catedra"
        const val DB_VERSION = 2

        // Columnas tabla estudiante
        const val ID_ESTUDIANTE = "id"
        const val NOMBRES = "nombres"
        const val APELLIDOS = "apellidos"
        const val DOCUMENTO = "documento"
        const val CORREO = "correo"

        // Columnas tabla cátedra
        const val ID_CATEDRA = "id"
        const val NOMBRE_CATEDRA = "nombre_catedra"
        const val HORARIO = "horario"
        const val ID_ESTUDIANTE_FK = "id_estudiante"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla estudiante con DOCUMENTO como clave primaria
        val queryEstudiante = "CREATE TABLE $TABLE_ESTUDIANTE (" +
                "$DOCUMENTO TEXT PRIMARY KEY, " +
                "$NOMBRES TEXT, " +
                "$APELLIDOS TEXT, " +
                "$CORREO TEXT)"
        db.execSQL(queryEstudiante)

        // Crear tabla cátedra con clave foránea referenciando DOCUMENTO
        val queryCatedra = "CREATE TABLE $TABLE_CATEDRA (" +
                "$ID_CATEDRA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$NOMBRE_CATEDRA TEXT, " +
                "$HORARIO TEXT, " +
                "$ID_ESTUDIANTE_FK TEXT, " +
                "FOREIGN KEY($ID_ESTUDIANTE_FK) REFERENCES $TABLE_ESTUDIANTE($DOCUMENTO))"
        db.execSQL(queryCatedra)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ESTUDIANTE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEDRA")
        onCreate(db)
    }

    // Insertar estudiante
    fun ingresarEstudiante(nombres: String, apellidos: String, documento: String, correo: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DOCUMENTO, documento)
            put(NOMBRES, nombres)
            put(APELLIDOS, apellidos)
            put(CORREO, correo)
        }

        val resultado = db.insert(TABLE_ESTUDIANTE, null, values)
        db.close()
        return resultado
    }

    // Insertar cátedra asociada a un estudiante
    fun ingresarCatedra(nombre: String, horario: String, documentoEstudiante: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(NOMBRE_CATEDRA, nombre)
            put(HORARIO, horario)
            put(ID_ESTUDIANTE_FK, documentoEstudiante)
        }

        val resultado = db.insert(TABLE_CATEDRA, null, values)
        db.close()
        return resultado
    }

    fun consultarEstudiante(documento: String): List<String> {
        val lista = ArrayList<String>()
        val db = this.readableDatabase

        val query = "SELECT e.$DOCUMENTO, e.$NOMBRES, e.$APELLIDOS, e.$DOCUMENTO, e.$CORREO, c.$NOMBRE_CATEDRA, c.$HORARIO " +
                "FROM $TABLE_ESTUDIANTE e " +
                "LEFT JOIN $TABLE_CATEDRA c ON e.$DOCUMENTO = c.$ID_ESTUDIANTE_FK " +
                "WHERE e.$DOCUMENTO = ?"

        val cursor = db.rawQuery(query, arrayOf(documento))
        if (cursor.moveToFirst()) {
            do {
                val resultado = "ID: ${cursor.getInt(0)}\n" +
                        "Nombre: ${cursor.getString(1)}\n" +
                        "Apellidos: ${cursor.getString(2)}\n" +
                        "Documento: ${cursor.getString(3)}\n" +
                        "Correo: ${cursor.getString(4)}\n" +
                        "Cátedra: ${cursor.getString(5)}\n" +
                        "Horario: ${cursor.getString(6)}"

                lista.add(resultado)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }

    fun consultarCatedra(nombre: String, documento: String): List<String> {
        val lista = ArrayList<String>()
        val db = this.readableDatabase

        val query = "SELECT c.$ID_CATEDRA, c.$NOMBRE_CATEDRA, c.$HORARIO " +
                "FROM $TABLE_CATEDRA c " +
                "JOIN $TABLE_ESTUDIANTE e ON c.$ID_ESTUDIANTE_FK = e.$DOCUMENTO " +
                "WHERE c.$NOMBRE_CATEDRA = ? AND e.$DOCUMENTO = ?"

        val cursor = db.rawQuery(query, arrayOf(nombre, documento))
        if (cursor.moveToFirst()) {
            do {
                val resultado = "ID Cátedra: ${cursor.getInt(0)} " +
                        "| Nombre: ${cursor.getString(1)} " +
                        "| Horario: ${cursor.getString(2)}"
                lista.add(resultado)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }

    fun existeEstudiante(documento: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT 1 FROM $TABLE_ESTUDIANTE WHERE $DOCUMENTO = ?"
        val cursor = db.rawQuery(query, arrayOf(documento))
        val existe = cursor.count > 0
        cursor.close()
        db.close()
        return existe
    }

    // Actualizar cátedra para un estudiante específico
    fun actualizarCatedraEstudiante(documento: String, nombreCatedra: String, horario: String): Boolean {
        val db = this.writableDatabase

        // Primero verificamos si ya existe una cátedra con ese nombre para el estudiante
        val queryCheck = "SELECT $ID_CATEDRA FROM $TABLE_CATEDRA " +
                "WHERE $ID_ESTUDIANTE_FK = ? AND $NOMBRE_CATEDRA = ?"
        val cursor = db.rawQuery(queryCheck, arrayOf(documento, nombreCatedra))

        val resultado = if (cursor.moveToFirst()) {
            // La cátedra existe, actualizamos el horario
            val idCatedra = cursor.getInt(0)
            val values = ContentValues().apply {
                put(HORARIO, horario)
            }

            db.update(TABLE_CATEDRA, values, "$ID_CATEDRA = ?",
                arrayOf(idCatedra.toString())) > 0
        } else {
            // La cátedra no existe, la creamos
            val values = ContentValues().apply {
                put(NOMBRE_CATEDRA, nombreCatedra)
                put(HORARIO, horario)
                put(ID_ESTUDIANTE_FK, documento)
            }

            db.insert(TABLE_CATEDRA, null, values) != -1L
        }

        cursor.close()
        db.close()
        return resultado
    }

    fun eliminarEstudiante(documento: String): Boolean {
        val db = this.writableDatabase
        val filasAfectadas = db.delete(TABLE_ESTUDIANTE, "$DOCUMENTO=?", arrayOf(documento))
        db.close()

        return filasAfectadas > 0
    }

    fun eliminarCatedra(documento: String, nombreCatedra: String): Boolean {
        val db = this.writableDatabase
        val filasAfectadas = db.delete(
            TABLE_CATEDRA,
            "$NOMBRE_CATEDRA=? AND $ID_ESTUDIANTE_FK=?",
            arrayOf(nombreCatedra, documento)
        )
        db.close()

        return filasAfectadas > 0
    }

    fun obtenerTodosLosRegistros(): List<String> {
        val lista = ArrayList<String>()
        val db = this.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM $TABLE_ESTUDIANTE", null)

        if (cursor.moveToFirst()) {
            do {
                val datos = "ID: ${cursor.getInt(0)} | Nombre: ${cursor.getString(1)} | Apellido: ${cursor.getString(2)}"
                lista.add(datos)
            } while (cursor.moveToNext())
        } else {
            // Agregar log si no hay datos
            Log.d("DBHandler", "No se encontraron registros en la tabla estudiante.")
        }

        cursor.close()
        db.close()
        return lista
    }

    fun obtenerCatedrasPorDocumento(documento: String): List<String> {
        val listaCatedras = ArrayList<String>()
        val db = this.readableDatabase

        // Consulta para obtener las cátedras asociadas a un estudiante por su documento
        val query = "SELECT $NOMBRE_CATEDRA, $HORARIO " +
                "FROM $TABLE_CATEDRA " +
                "WHERE $ID_ESTUDIANTE_FK = ?"

        val cursor = db.rawQuery(query, arrayOf(documento))

        if (cursor.moveToFirst()) {
            do {
                val catedra = "Cátedra: ${cursor.getString(0)} | Horario: ${cursor.getString(1)}"
                listaCatedras.add(catedra)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaCatedras
    }
}