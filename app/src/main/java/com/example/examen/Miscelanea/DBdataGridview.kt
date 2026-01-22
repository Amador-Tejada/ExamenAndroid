package com.example.examen.Miscelanea

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.examen.adapter.tipoPersona


// base de datos para ingresar datos persona
class DBdataGridview (context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
){

    companion object{
        private const val DATABASE_NAME = "gridview.db"
        private const val DATABASE_VERSION = 1

        // Parametros de la tabla
        private const val TABLE_NAME = "gridview"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_APELLIDOS = "apellidos"
        private const val COLUMN_SEXO = "sexo"
        private const val COLUMN_CICLO = "ciclo"


        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NOMBRE TEXT, " +
                    "$COLUMN_APELLIDOS TEXT, " +
                    "$COLUMN_SEXO TEXT, " +
                    "$COLUMN_CICLO TEXT)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    // Sentencia SQL para crear la tabla
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    // Sentencia SQL para actualizar la base de datos
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    // Insertar un nuevo usuario en la base de datos usando el tipoPersona
    fun insertarUsuario(persona: tipoPersona): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, persona.nombre)
            put(COLUMN_APELLIDOS, persona.apellidos)
            put(COLUMN_SEXO, persona.sexo)
            put(COLUMN_CICLO, persona.ciclo)
            }
        return db.insert(TABLE_NAME, null, values)
        }

    // Obtener todas las personas guardadas en la tabla
    fun obtenerTodos(): List<tipoPersona> {
        val lista = mutableListOf<tipoPersona>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_NOMBRE, $COLUMN_APELLIDOS, $COLUMN_SEXO, $COLUMN_CICLO FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(0) ?: ""
                val apellidos = cursor.getString(1) ?: ""
                val sexo = cursor.getString(2) ?: ""
                val ciclo = cursor.getString(3) ?: ""
                lista.add(tipoPersona(nombre, apellidos, sexo, ciclo))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }

    }
