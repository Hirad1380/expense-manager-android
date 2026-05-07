package com.example.remainingapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class DatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val DATABASE_VERSION = 1

        // Table: Users
        private const val USER_TABLE = "users"
        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"

        // Table: Data
        private const val DATA_TABLE = "my_Data"
        private const val COLUMN_DATA_ID = "id"
        private const val COLUMN_TITLE = "Data_Title"
        private const val COLUMN_DESCRIPTION = "Data_Description"
        private const val COLUMN_PRICE = "Data_Price"
        private const val COLUMN_DATE = "Data_Date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTable = """
            CREATE TABLE $USER_TABLE (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT,
                $COLUMN_PASSWORD TEXT
            )
        """.trimIndent()

        val createDataTable = """
            CREATE TABLE $DATA_TABLE (
                $COLUMN_DATA_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_PRICE INTEGER,
                $COLUMN_DATE TEXT
            )
        """.trimIndent()

        db?.execSQL(createUserTable)
        db?.execSQL(createDataTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $USER_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $DATA_TABLE")
        onCreate(db)
    }

    // Insert user (username & password)
    fun insertUser(username: String, password: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
        }
        return db.insert(USER_TABLE, null, values)
    }

    // Check if user exists
    fun readUser(username: String, password: String): Boolean {
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(USER_TABLE, null, selection, selectionArgs, null, null, null)
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    // Insert data (title, description, price, date)
    fun addData(title: String, description: String, price: Int, date: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_PRICE, price)
            put(COLUMN_DATE, date)
        }

        val result = db.insert(DATA_TABLE, null, values)
        if (result == -1L) {
            Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Data added successfully", Toast.LENGTH_SHORT).show()
        }
    }

    // Update data (id, title, description, price)
    fun updateData(row_id: String, title: String, description: String, price: String, date: String){
        val db = writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_PRICE, price.toIntOrNull() ?: 0)
            put(COLUMN_DATE, date)
        }

        val result = db.update(DATA_TABLE, cv, "id=?", arrayOf(row_id))
        if (result == -1) {
            Toast.makeText(context, "Failed to Update Data", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show()
        }
    }

    // Delete one Row of data
    fun deleteOneRowData(row_id: String){
        val db = writableDatabase

        val result = db.delete(DATA_TABLE, "id=?", arrayOf(row_id))
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete Data", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    // Delete All Data
    fun deleteAllData(){
        val db = writableDatabase;
        db.execSQL("DELETE FROM " + DATA_TABLE);
    }

    // Read data (title, description, price)
    fun readAllData(): Cursor? {
        val query = "SELECT * FROM $DATA_TABLE"
        val db = this.readableDatabase

        return if (db != null) {
            db.rawQuery(query, null)
        } else {
            null
        }
    }


}

