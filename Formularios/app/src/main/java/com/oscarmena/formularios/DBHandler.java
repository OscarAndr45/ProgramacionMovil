package com.oscarmena.formularios;


import java.util.List;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    public static final String DB_NAME = "oscarmena";
    public static final String TABLE_ESTUDIANTE = "estudiante";
    public static final String TABLE_CATEDRA = "catedra";
    public static final int DB_VERSION = 2;

    // Columnas tabla estudiante
    public static final String ID_ESTUDIANTE = "id";
    public static final String NOMBRES = "nombres";
    public static final String APELLIDOS = "apellidos";
    public static final String DOCUMENTO = "documento";
    public static final String CORREO = "correo";

    // Columnas tabla cátedra
    public static final String ID_CATEDRA = "id";
    public static final String NOMBRE_CATEDRA = "nombre_catedra";
    public static final String HORARIO = "horario";
    public static final String ID_ESTUDIANTE_FK = "id_estudiante";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla estudiante con DOCUMENTO como clave primaria
        String queryEstudiante = "CREATE TABLE " + TABLE_ESTUDIANTE + " (" +
                DOCUMENTO + " TEXT PRIMARY KEY, " +
                NOMBRES + " TEXT, " +
                APELLIDOS + " TEXT, " +
                CORREO + " TEXT)";
        db.execSQL(queryEstudiante);

        // Crear tabla cátedra con clave foránea referenciando DOCUMENTO
        String queryCatedra = "CREATE TABLE " + TABLE_CATEDRA + " (" +
                ID_CATEDRA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOMBRE_CATEDRA + " TEXT, " +
                HORARIO + " TEXT, " +
                ID_ESTUDIANTE_FK + " TEXT, " + // Cambiamos INTEGER por TEXT
                "FOREIGN KEY(" + ID_ESTUDIANTE_FK + ") REFERENCES " + TABLE_ESTUDIANTE + "(" + DOCUMENTO + "))";
        db.execSQL(queryCatedra);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTUDIANTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEDRA);
        onCreate(db);
    }

    // Insertar estudiante
    public long ingresarEstudiante(String nombres, String apellidos, String documento, String correo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DOCUMENTO, documento); // Ahora es la clave primaria
        values.put(NOMBRES, nombres);
        values.put(APELLIDOS, apellidos);
        values.put(CORREO, correo);

        long resultado = db.insert(TABLE_ESTUDIANTE, null, values);
        db.close();
        return resultado;
    }

    // Insertar cátedra asociada a un estudiante
    public long ingresarCatedra(String nombre, String horario, String documentoEstudiante) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOMBRE_CATEDRA, nombre);
        values.put(HORARIO, horario);
        values.put(ID_ESTUDIANTE_FK, documentoEstudiante); // Guardamos el documento

        long resultado = db.insert(TABLE_CATEDRA, null, values);
        db.close();
        return resultado;
    }

    public List<String> consultarEstudiante(String documento) {
        List<String> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT e." + DOCUMENTO + ", e." + NOMBRES + ", e." + APELLIDOS + ", e." + DOCUMENTO + ", e." + CORREO + ", c." + NOMBRE_CATEDRA + ", c." + HORARIO +
                " FROM " + TABLE_ESTUDIANTE + " e " +
                " LEFT JOIN " + TABLE_CATEDRA + " c ON e." + DOCUMENTO + " = c." + ID_ESTUDIANTE_FK +
                " WHERE e." + DOCUMENTO + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{documento});
        if (cursor.moveToFirst()) {
            do {
                String resultado = "ID: " + cursor.getInt(0) + "\n" +
                        "Nombre: " + cursor.getString(1) + "\n" +
                        "Apellidos: " + cursor.getString(2) + "\n" +
                        "Documento: " + cursor.getString(3) + "\n" +
                        "Correo: " + cursor.getString(4) + "\n" +
                        "Cátedra: " + cursor.getString(5) + "\n" +
                        "Horario: " +  cursor.getString(6);

                lista.add(resultado);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }
    public List<String> consultarCatedra(String nombre, String documento) {
        List<String> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT c." + ID_CATEDRA + ", c." + NOMBRE_CATEDRA + ", c." + HORARIO +
                " FROM " + TABLE_CATEDRA + " c " +
                " JOIN " + TABLE_ESTUDIANTE + " e ON c." + ID_ESTUDIANTE_FK + " = e." + DOCUMENTO +
                " WHERE c." + NOMBRE_CATEDRA + " = ? AND e." + DOCUMENTO + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{nombre, documento});
        if (cursor.moveToFirst()) {
            do {
                String resultado = "ID Cátedra: " + cursor.getInt(0) +
                        " | Nombre: " + cursor.getString(1) +
                        " | Horario: " + cursor.getString(2);
                lista.add(resultado);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    public boolean existeEstudiante(String documento) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM " + TABLE_ESTUDIANTE + " WHERE " + DOCUMENTO + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{documento});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return existe;
    }

    // Actualizar cátedra para un estudiante específico
    public boolean actualizarCatedraEstudiante(String documento, String nombreCatedra, String horario) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Primero verificamos si ya existe una cátedra con ese nombre para el estudiante
        String queryCheck = "SELECT " + ID_CATEDRA + " FROM " + TABLE_CATEDRA +
                " WHERE " + ID_ESTUDIANTE_FK + " = ? AND " + NOMBRE_CATEDRA + " = ?";
        Cursor cursor = db.rawQuery(queryCheck, new String[]{documento, nombreCatedra});

        boolean resultado;

        if (cursor.moveToFirst()) {
            // La cátedra existe, actualizamos el horario
            int idCatedra = cursor.getInt(0);
            ContentValues values = new ContentValues();
            values.put(HORARIO, horario);

            resultado = db.update(TABLE_CATEDRA, values, ID_CATEDRA + " = ?",
                    new String[]{String.valueOf(idCatedra)}) > 0;
        } else {
            // La cátedra no existe, la creamos
            ContentValues values = new ContentValues();
            values.put(NOMBRE_CATEDRA, nombreCatedra);
            values.put(HORARIO, horario);
            values.put(ID_ESTUDIANTE_FK, documento);

            resultado = db.insert(TABLE_CATEDRA, null, values) != -1;
        }

        cursor.close();
        db.close();
        return resultado;
    }
    public boolean eliminarEstudiante(String documento) {
        SQLiteDatabase db = this.getWritableDatabase();
        int filasAfectadas = db.delete(TABLE_ESTUDIANTE, DOCUMENTO + "=?", new String[]{documento});
        db.close();

        return filasAfectadas > 0;
    }

    public boolean eliminarCatedra(String documento, String nombreCatedra) {
        SQLiteDatabase db = this.getWritableDatabase();
        int filasAfectadas = db.delete(
                TABLE_CATEDRA,
                NOMBRE_CATEDRA + "=? AND " + ID_ESTUDIANTE_FK + "=?",
                new String[]{nombreCatedra, documento}
        );
        db.close();

        return filasAfectadas > 0;
    }
    public List<String> obtenerTodosLosRegistros() {
        List<String> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ESTUDIANTE, null);

        if (cursor.moveToFirst()) {
            do {
                String datos = "ID: " + cursor.getInt(0) + " | Nombre: " + cursor.getString(1) + " | Apellido: " + cursor.getString(2);
                lista.add(datos);
            } while (cursor.moveToNext());
        } else {
            // Agregar log si no hay datos
            android.util.Log.d("DBHandler", "No se encontraron registros en la tabla estudiante.");
        }

        cursor.close();
        db.close();
        return lista;
    }
    public List<String> obtenerCatedrasPorDocumento(String documento) {
        List<String> listaCatedras = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para obtener las cátedras asociadas a un estudiante por su documento
        String query = "SELECT " + NOMBRE_CATEDRA + ", " + HORARIO +
                " FROM " + TABLE_CATEDRA +
                " WHERE " + ID_ESTUDIANTE_FK + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{documento});

        if (cursor.moveToFirst()) {
            do {
                String catedra = "Cátedra: " + cursor.getString(0) + " | Horario: " + cursor.getString(1);
                listaCatedras.add(catedra);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaCatedras;
    }

}