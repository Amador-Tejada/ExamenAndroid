package com.example.examen.Miscelanea

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBlogin (context: Context): SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {

        // El nombre de la base de datos
        private const val DATABASE_NAME = "usuario.db"

        // La versión de la base de datos
        private const val DATABASE_VERSION = 1

        // El nombre de la tabla
        private const val TABLE_NAME = "usuario"

        // Las columnas de la tabla
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_PASSWORD = "contrasena"

        // La sentencia SQL para crear la tabla

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NOMBRE TEXT UNIQUE, " +
                    "$COLUMN_PASSWORD TEXT)"

        // sentencia SQL para borrar la tabla
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    // Sentencia SQL para crear la tabla
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)


    }

    // Sentencia SQL para actualizar la base de datos
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    // Insertar un nuevo usuario en la base de datos
    fun insertarUsuario(nombre: String, contrasena: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_PASSWORD, contrasena)
        }
        return db.insert(TABLE_NAME, null, values)
    }


}