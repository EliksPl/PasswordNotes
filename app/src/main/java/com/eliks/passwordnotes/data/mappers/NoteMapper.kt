package com.eliks.passwordnotes.data.mappers

import com.eliks.passwordnotes.data.local.NoteDB
import com.eliks.passwordnotes.presentation.notes.NoteUI
import com.eliks.passwordnotes.utils.DateAndTimeFormatter

object NoteMapper {
    fun NoteDB.toUI() : NoteUI{
        val dateTimestamp = DateAndTimeFormatter.formatTimestamp(created = timestampCreated, edited = timestampLastEdited)

        return NoteUI(
            id = id,
            title = title,
            details = details,
            timestampCreated = timestampCreated,
            timestampLastEdited = timestampLastEdited,
            displayDateTimestamp = dateTimestamp
        )
    }

    fun NoteUI.toDB() : NoteDB{
        return NoteDB(
            id = id,
            title = title,
            details = details,
            timestampCreated = timestampCreated,
            timestampLastEdited = timestampLastEdited
        )
    }

}