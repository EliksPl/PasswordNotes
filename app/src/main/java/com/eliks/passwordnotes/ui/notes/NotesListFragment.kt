package com.eliks.passwordnotes.ui.notes

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.eliks.passwordnotes.databinding.FragmentNotesListBinding
import com.eliks.passwordnotes.presentation.notes.NoteUI
import com.eliks.passwordnotes.presentation.notes.NotesViewModel
import com.eliks.passwordnotes.ui.notes.adapter.NoteAdapter
import com.eliks.passwordnotes.ui.notes.adapter.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesListFragment : Fragment() {
    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModels()
    private lateinit var notesAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initRVAdapterAndData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRVAdapterAndData() {
        notesAdapter = NoteAdapter{note ->
            openEditOnNoteListItemClicked(note)
        }

        binding.fragmentNotesListRv.adapter = notesAdapter
        binding.fragmentNotesListRv.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notes.collect { list ->
                    notesAdapter.submitList(list)
                }
            }
        }

        setupSwipeHandler()

    }

    private fun setupSwipeHandler(){
        val swipeHandler = SwipeToDeleteCallback(requireContext()) { position ->
            val note = notesAdapter.currentList[position]
            viewModel.deleteNote(note.id)

            Snackbar.make(binding.root, "Нотатку видалено", Snackbar.LENGTH_LONG)
                .setAction("ВІДМІНИТИ") {
                    viewModel.restoreNote(note)
                }.show()
        }

        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.fragmentNotesListRv)
    }

    private fun openEditOnNoteListItemClicked(note: NoteUI) {
        NoteEditBottomDialogFragment.newInstance(note.id)
            .show(parentFragmentManager, "edit_note")
    }

    private fun initListeners() {
        binding.fragmentNotesListFabAddNote.setOnClickListener{
            NoteEditBottomDialogFragment.newInstance().show(parentFragmentManager, "create_note")
        }
            }

}