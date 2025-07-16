package com.eliks.passwordnotes.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eliks.passwordnotes.data.mappers.NoteMapper.toDB
import com.eliks.passwordnotes.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    private val _notes = MutableStateFlow<List<NoteUI>>(emptyList())
    val notes: StateFlow<List<NoteUI>> = _notes

    init {
        viewModelScope.launch{
            repository.getAllNotes().collect {
                _notes.value = it
            }
        }
    }

    suspend fun loadNoteById(id: Int): NoteUI? {
        val noteDB = repository.getNoteById(id)
        return noteDB
    }

    fun createNote(title: String, details: String) {
        viewModelScope.launch {
            repository.insertNote(title, details)
        }
    }

    fun updateNote(id: Int, title: String, details: String) {
        viewModelScope.launch {
            repository.updateNote(id = id, title = title, details = details)
        }
    }

    fun deleteNote(id: Int){
        viewModelScope.launch {
            repository.deleteNoteById(id = id)
        }
    }

    fun restoreNote(note: NoteUI?) {
        viewModelScope.launch {
            if (note != null) {
                repository.restoreNote(note.toDB())
            }
        }
    }
}