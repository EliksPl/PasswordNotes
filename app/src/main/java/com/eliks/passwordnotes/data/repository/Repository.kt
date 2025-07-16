package com.eliks.passwordnotes.data.repository

import com.eliks.passwordnotes.data.local.NoteDB
import com.eliks.passwordnotes.data.local.NoteDao
import com.eliks.passwordnotes.data.mappers.NoteMapper.toUI
import com.eliks.passwordnotes.presentation.notes.NoteUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val noteDao: NoteDao
) {
    fun getAllNotes(): Flow<List<NoteUI>>{
        return noteDao.getAllNotes().map { list ->
            list.map {
                it.toUI()
            }
        }
    }

    suspend fun getNoteById(id : Int): NoteUI? {
        return noteDao.getNoteById(id)?.toUI()
    }

    suspend fun insertNote(title: String, details: String) {
        val now = System.currentTimeMillis()
        val note = NoteDB(
            title = title,
            details = details,
            timestampCreated = now,
            timestampLastEdited = -1
        )
        noteDao.insertNote(note)
    }

    suspend fun updateNote(id: Int, title: String, details: String) {
        val existing = noteDao.getNoteById(id)
        if (existing != null) {
            val updated = existing.copy(
                title = title,
                details = details,
                timestampLastEdited = System.currentTimeMillis()
            )
            noteDao.updateNote(updated)
        }
    }

    suspend fun deleteNoteById(id: Int) : Boolean{
        val note = noteDao.getNoteById(id)

        if (note != null){
            noteDao.deleteNote(note)
            return true
        }

        return false
    }

    suspend fun restoreNote(note: NoteDB) {
        noteDao.insertNote(note)
    }
}