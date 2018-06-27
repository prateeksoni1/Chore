package com.example.prateek.chore.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.prateek.chore.model.*
import java.sql.Time
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChoresDatabaseHandler(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {

        var CREATE_CHORE_TABLE = "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY, $KEY_NAME TEXT, $KEY_ASSIGNED_TO TEXT, $KEY_ASSIGNED_BY TEXT, $KEY_ASSIGNED_TIME LONG)"
        db?.execSQL(CREATE_CHORE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)

    }

    fun createChore(chore: Chore) {
        var db = writableDatabase

        var values = ContentValues()
        values.put(KEY_NAME, chore.name)
        values.put(KEY_ASSIGNED_TO, chore.assTo)
        values.put(KEY_ASSIGNED_BY, chore.assBy)
        values.put(KEY_ASSIGNED_TIME, System.currentTimeMillis())

        db.insert(TABLE_NAME, null, values)
        Log.d("DB INSERT", "SUCCESS")
        db.close()
    }

    fun readAChore(id: Int) : Chore {
        var db = writableDatabase
        var cursor = db.query(TABLE_NAME, arrayOf(KEY_ID, KEY_NAME, KEY_ASSIGNED_TO, KEY_ASSIGNED_BY, KEY_ASSIGNED_TIME),
                "$KEY_ID=?", arrayOf(id.toString()), null, null, null)

        if (cursor != null) {
            cursor.moveToFirst()
        }

        var chore = Chore()
        chore.name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
        chore.assTo = cursor.getString(cursor.getColumnIndex(KEY_ASSIGNED_TO))
        chore.assBy = cursor.getString(cursor.getColumnIndex(KEY_ASSIGNED_BY))
        chore.time = cursor.getLong(cursor.getColumnIndex(KEY_ASSIGNED_TIME))



        return chore
    }


    fun readChores(): ArrayList<Chore> {
        var db = readableDatabase
        var list = ArrayList<Chore>()

        var selectall = "SELECT * FROM $TABLE_NAME"
        var cursor = db.rawQuery(selectall, null)

        if(cursor.moveToFirst()) {
            do {
                var chore = Chore()
                chore.name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                chore.assTo = cursor.getString(cursor.getColumnIndex(KEY_ASSIGNED_TO))
                chore.assBy = cursor.getString(cursor.getColumnIndex(KEY_ASSIGNED_BY))
                chore.time = cursor.getLong(cursor.getColumnIndex(KEY_ASSIGNED_TIME))

                list.add(chore)
            }while (cursor.moveToNext())
        }
        return list
    }

    fun updateChore(chore: Chore) : Int {
        var db = writableDatabase
        var values = ContentValues()
        values.put(KEY_NAME, chore.name)
        values.put(KEY_ASSIGNED_TO, chore.assTo)
        values.put(KEY_ASSIGNED_BY, chore.assBy)
        values.put(KEY_ASSIGNED_TIME, System.currentTimeMillis())

        return db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(chore.id.toString()))
    }

    fun deleteChore(chore: Chore) {
        var db = writableDatabase
        db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(chore.id.toString()))
        db.close()
    }

    fun getChoresCount() : Int {
        var db = readableDatabase
        var query = "SELECT * FROM $TABLE_NAME"
        var cursor = db.rawQuery(query, null)
        return cursor.count
    }
}