package com.eliks.passwordnotes.ui.notes

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.eliks.passwordnotes.databinding.FragmentNoteEditBottomDialogBinding
import com.eliks.passwordnotes.presentation.notes.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

//This fragment is responsible for both creating new and editing existing notes
//Depending on whether argument is passed to it or not
//TODO extract string from resources
//TODO add title symbol limit
@AndroidEntryPoint
class NoteEditBottomDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentNoteEditBottomDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotesViewModel by viewModels()

    private var noteId: Int? = null
    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteId = arguments?.getInt(ARG_NOTE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteEditBottomDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isEditing = isArgumentPresent()

        if (isEditing) {
            initInterfaceTexts(view)
        }

        binding.noteEditSaveBtn.setOnClickListener {
            if (!trySaveOrAddNote()){
                return@setOnClickListener
            }

            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initInterfaceTexts(view : View) {
        viewLifecycleOwner.lifecycleScope.launch{
            val note = viewModel.loadNoteById(noteId!!)

            if (note == null){
                Snackbar.make(view, "Note not found", Snackbar.LENGTH_SHORT).show()
                dismiss()
                return@launch
            }

            binding.noteEditScreenTitleTv.text = "Editing note"
            binding.noteEditTitleEt.setText(note.title)
            binding.noteEditDetailsEt.setText(note.details)

        }
    }

    //true if success, false if failed
    private fun trySaveOrAddNote() : Boolean {
        val title = binding.noteEditTitleEt.text.toString().trim()
        val details = binding.noteEditDetailsEt.text.toString().trim()

        if (title.isEmpty() && details.isEmpty()) {
            binding.noteEditTitleEt.error = "Both fields cannot be empty"
            return false
        }

        if (isEditing) {
            viewModel.updateNote(id = noteId!!, title = title, details = details)
        } else {
            viewModel.createNote(title = title, details = details)
        }

        return true
    }

    private fun isArgumentPresent(): Boolean {
        return noteId != null && noteId !=-1
    }

    companion object {
        private const val ARG_NOTE_ID = "note_id"

        fun newInstance(noteId: Int? = null): NoteEditBottomDialogFragment {
            val fragment = NoteEditBottomDialogFragment()
            if (noteId != null) {
                val args = Bundle()
                args.putInt(ARG_NOTE_ID, noteId)
                fragment.arguments = args
            }
            return fragment
        }
    }
}