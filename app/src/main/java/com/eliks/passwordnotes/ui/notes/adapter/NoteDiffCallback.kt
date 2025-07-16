package com.eliks.passwordnotes.ui.notes.adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.eliks.passwordnotes.presentation.notes.NoteUI

object NoteDiffCallback : DiffUtil.ItemCallback<NoteUI>() {
    override fun areItemsTheSame(oldItem: NoteUI, newItem: NoteUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteUI, newItem: NoteUI): Boolean {
        Log.d("DiffUtil", "Diff util check: ${oldItem.title} == ${newItem.title}, ${oldItem.details} == ${newItem.details}")
        return oldItem.title == newItem.title && oldItem.details == newItem.details
    }
}