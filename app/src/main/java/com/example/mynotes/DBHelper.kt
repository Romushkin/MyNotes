package com.example.mynotes

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Date

class DBHelper (context: Context, factory: CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME, factory,DATABASE_VERSION ) {

    companion object {
        private val DATABASE_NAME = "NOTE_DATABASE"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "notes_table"
        val KEY_ID = "id"
        val KEY_NOTE = "note"
        val KEY_DATE = "date"
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    override fun onCreate(p0: SQLiteDatabase?) {

        val query = ("CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NOTE + " TEXT, " + KEY_DATE + " TEXT" + ")")

        p0?.execSQL(query)
    }

    fun addNote(note: Note){
        val values = ContentValues()
        values.put(KEY_ID, note.id)
        values.put(KEY_NOTE, note.name)
        values.put(KEY_DATE, note.date)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getNotes(): MutableList<Note> {
        val notesList: MutableList<Note> = mutableListOf()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch ( e: SQLiteException) {
            db.execSQL(selectQuery)
            return notesList
        }
        var id: Int
        var name: String
        var date: String

        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID))
                name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NOTE))
                date = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DATE))
                val note = Note(id, name, date)
                notesList.add(note)
            } while (cursor.moveToNext())
        }
        return notesList
    }

    fun removeAll(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

    fun deleteNote(note: Note){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID,note.id)
        db.delete(TABLE_NAME,"id=" + note.id, null)
        db.close()
    }

    fun updateNote(id: Int, newNote: String){
        val db = this.writableDatabase
        val selectQuery = "UPDATE $TABLE_NAME SET $KEY_NOTE = '$newNote', $KEY_DATE = 'Изменено ${getTimeNow()}' WHERE id = '$id' "
        db.execSQL(selectQuery)
        db.close()

    }

    @SuppressLint("SimpleDateFormat")
    fun getTimeNow(): String{
        val datetime = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date())
        return datetime
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        return super.getReadableDatabase()
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        return super.getWritableDatabase()
    }

    override fun setIdleConnectionTimeout(idleConnectionTimeoutMs: Long) {
        super.setIdleConnectionTimeout(idleConnectionTimeoutMs)
    }

    override fun setOpenParams(openParams: SQLiteDatabase.OpenParams) {
        super.setOpenParams(openParams)
    }

    override fun setLookasideConfig(slotSize: Int, slotCount: Int) {
        super.setLookasideConfig(slotSize, slotCount)
    }

    override fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        super.setWriteAheadLoggingEnabled(enabled)
    }

    override fun getDatabaseName(): String {
        return super.getDatabaseName()
    }

    override fun close() {
        super.close()
    }
}