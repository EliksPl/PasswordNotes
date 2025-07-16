package com.eliks.passwordnotes.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eliks.passwordnotes.data.local.NoteDatabase.Companion.NOTE_DB_DETAILS_COLUMN_NAME
import com.eliks.passwordnotes.data.local.NoteDatabase.Companion.NOTE_DB_ID_COLUMN_NAME
import com.eliks.passwordnotes.data.local.NoteDatabase.Companion.NOTE_DB_TABLE_NAME
import com.eliks.passwordnotes.data.local.NoteDatabase.Companion.NOTE_DB_TIMESTAMP_CREATED_COLUMN_NAME
import com.eliks.passwordnotes.data.local.NoteDatabase.Companion.NOTE_DB_TIMESTAMP_LAST_EDITED_COLUMN_NAME
import com.eliks.passwordnotes.data.local.NoteDatabase.Companion.NOTE_DB_TITLE_COLUMN_NAME

@Entity(tableName = NOTE_DB_TABLE_NAME)
data class NoteDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = NOTE_DB_ID_COLUMN_NAME)
    val id: Int = 0,

    @ColumnInfo(name = NOTE_DB_TITLE_COLUMN_NAME)
    val title : String,

    @ColumnInfo(name = NOTE_DB_DETAILS_COLUMN_NAME)
    val details: String,

    @ColumnInfo(name = NOTE_DB_TIMESTAMP_CREATED_COLUMN_NAME)
    val timestampCreated: Long = System.currentTimeMillis(),

    @ColumnInfo(name = NOTE_DB_TIMESTAMP_LAST_EDITED_COLUMN_NAME)
    val timestampLastEdited: Long = -1


)