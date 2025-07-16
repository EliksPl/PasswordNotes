package com.eliks.passwordnotes.ui.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eliks.passwordnotes.databinding.ItemNoteBinding
import com.eliks.passwordnotes.presentation.notes.NoteUI

//TODO make part of details a title if no title present
class NoteAdapter(
    private val onClick: (NoteUI) -> Unit
) : ListAdapter<NoteUI, NoteAdapter.NoteViewHolder>(NoteDiffCallback)  {

    inner class NoteViewHolder (private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(note: NoteUI){
            binding.itemNoteTitle.text = note.title
            binding.itemNoteDetails.text = note.details
            binding.itemNoteDateCreated.text = note.displayDateTimestamp

        }

        fun initListeners(note: NoteUI) {
            binding.root.setOnClickListener {
                onClick(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.initListeners(getItem(position))

    }

}