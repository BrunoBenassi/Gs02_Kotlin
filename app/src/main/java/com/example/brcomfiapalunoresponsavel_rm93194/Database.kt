package com.example.brcomfiapalunoresponsavel_rm93194


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "Database"
        private const val DB_VERSION = 1
        private const val TABLE_TIPS = "DBTips"
        private const val FIELD_ID = "tip_id"
        private const val FIELD_TITLE = "tip_title"
        private const val FIELD_DESCRIPTION = "tip_description"
    }

    override fun onCreate(database: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_TIPS (
                $FIELD_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                $FIELD_TITLE TEXT, 
                $FIELD_DESCRIPTION TEXT
            )
        """
        database.execSQL(createTableQuery)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS $TABLE_TIPS")
        onCreate(database)
    }

    fun insertDica(dica:BDTips): Long {
        val database = writableDatabase
        val values = ContentValues().apply {
            put(FIELD_TITLE, dica.titulo)
            put(FIELD_DESCRIPTION, dica.descricao)
        }
        return database.insert(TABLE_TIPS, null, values)
    }

    fun fetchAllTips(): List<DBTips> {
        val tipsList = mutableListOf<DBTips>()
        val database = readableDatabase
        val cursor = database.rawQuery("SELECT * FROM $TABLE_TIPS", null)

        with(cursor) {
            while (moveToNext()) {
                val title = getString(getColumnIndexOrThrow(FIELD_TITLE))
                val description = getString(getColumnIndexOrThrow(FIELD_DESCRIPTION))
                tipsList.add(DBTips(title, description))
            }
            close()
        }
        return tipsList
    }
}
