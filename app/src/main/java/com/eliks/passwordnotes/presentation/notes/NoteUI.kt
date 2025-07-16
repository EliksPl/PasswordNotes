package com.eliks.passwordnotes.presentation.notes

data class NoteUI (
    val id: Int,
    val title: String,
    val details: String,
    val timestampCreated: Long,
    val timestampLastEdited: Long,
    val displayDateTimestamp: String
)