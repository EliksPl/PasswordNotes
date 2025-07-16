package com.eliks.passwordnotes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.eliks.passwordnotes.data.local.NoteDatabase.Companion.NOTE_DB_ID_COLUMN_NAME
import com.eliks.passwordnotes.data.local.NoteDatabase.Companion.NOTE_DB_TABLE_NAME
import com.eliks.passwordnotes.data.local.NoteDatabase.Companion.NOTE_DB_TIMESTAMP_CREATED_COLUMN_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM $NOTE_DB_TABLE_NAME ORDER BY $NOTE_DB_TIMESTAMP_CREATED_COLUMN_NAME DESC")
    fun getAllNotes(): Flow<List<NoteDB>>

    @Query("SELECT * FROM $NOTE_DB_TABLE_NAME WHERE $NOTE_DB_ID_COLUMN_NAME = :id")
    suspend fun getNoteById(id: Int) : NoteDB?

    @Insert
    suspend fun insertNote(note : NoteDB)

    @Update
    suspend fun updateNote(note : NoteDB)

    @Delete
    suspend fun deleteNote(note: NoteDB)
}