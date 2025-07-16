package com.eliks.passwordnotes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteDB::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun NoteDao(): NoteDao

    companion object{
        const val NOTE_DB_TABLE_NAME = "note_table"
        const val NOTE_DB_ID_COLUMN_NAME = "id"
        const val NOTE_DB_TITLE_COLUMN_NAME = "title"
        const val NOTE_DB_DETAILS_COLUMN_NAME = "details"
        const val NOTE_DB_TIMESTAMP_CREATED_COLUMN_NAME = "timestamp_created"
        const val NOTE_DB_TIMESTAMP_LAST_EDITED_COLUMN_NAME = "timestamp_last_edited"

    }
}