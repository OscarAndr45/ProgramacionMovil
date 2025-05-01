package com.oscarmena.formulariocedulakotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "oscarmena"
        const val TABLE_NAME = "oscar"
        const val DB_VERSION = 1
        const val ID_COL = "id"
        const val FIRST_COL = "oscarmena"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                "$ID_COL INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$FIRST_COL TEXT)"
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun ingresar(documento: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(FIRST_COL, documento)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun consultar(): String {
        val db = this.readableDatabase
        val columns = arrayOf(ID_COL, FIRST_COL)
        val cursor: Cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)
        val buffer = StringBuilder()
        while (cursor.moveToNext()) {
            val cid = cursor.getInt(0)
            val document = cursor.getString(1)
            buffer.append("$cid   $document \n")
        }
        cursor.close()
        return buffer.toString()
    }
}
