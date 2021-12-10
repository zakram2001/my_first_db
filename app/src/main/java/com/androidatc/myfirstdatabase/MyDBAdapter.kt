package com.androidatc.myfirstdatabase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBAdapter (_context:Context) {
    private val databaseName: String = "name"
    private var mContext: Context? = null
    private var mDbHelper: MyDBHelper? = null
    private var mSqLiteDatabase: SQLiteDatabase? = null
    private val databaseVersion = 1

    init {
        this.mContext = _context
        mDbHelper = MyDBHelper(_context, databaseName, null, databaseVersion)
    }

    public fun open() {
        mSqLiteDatabase = mDbHelper?.writableDatabase
    }

    inner class MyDBHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, name, factory, version) {

        override fun onCreate(_db: SQLiteDatabase?) {
            val query = "CREATE TABLE students(id integer primary key autoincrement, name text, faculty integer);"
            _db?.execSQL(query)
        }

        override fun onUpgrade(_db: SQLiteDatabase?, _oldVersion: Int, _newVersion: Int) {
            val query = "DROP TABLE IF EXISTS students;"
            _db?.execSQL(query)
            onCreate(_db)
        }
    }

    public fun insertStudent (name: String, faculty: Int) {
        val cv: ContentValues = ContentValues()
        cv.put("name", name)
        cv.put("faculty", faculty)
        mSqLiteDatabase?.insert("students", null, cv)
    }

    public fun selectAllStudents(): List<String> {
        var allStudents: MutableList<String> = ArrayList();
        var cursor: Cursor = mSqLiteDatabase?.query("students", null, null, null, null, null, null)!!
        if (cursor.moveToFirst()) {
            do {
                allStudents.add(cursor.getString(1));
            } while (cursor.moveToNext());
            }
        return allStudents;
        }

    public fun deleteAllEngineers() {
        mSqLiteDatabase?.delete("students", null, null);
    }
}
