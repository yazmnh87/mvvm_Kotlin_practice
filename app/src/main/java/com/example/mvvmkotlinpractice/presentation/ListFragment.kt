package com.example.mvvmkotlinpractice.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmkotlinpractice.R
import com.example.mvvmkotlinpractice.framework.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*




class ListFragment : Fragment() {
    private val notesListAdapter = NotesListAdapter(arrayListOf())
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesListAdapter

        }
        addNote.setOnClickListener{goToNoteDetails()}

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.notes.observe(this, Observer {notesList ->
            loadingView.visibility = View.GONE
            notesListView.visibility = View.VISIBLE
            notesListAdapter.updateNotes(notesList.sortedByDescending{it.updateTime})
        })
    }


    override fun onResume() {
        super.onResume()
        viewModel.getNotes()

       // Toast.makeText(context,"Hello ther",Toast.LENGTH_LONG).show()

    }



    private fun  goToNoteDetails(id: Long = 0L){
        val action = ListFragmentDirections.actionGoToNote()
        Navigation.findNavController(notesListView).navigate(action)
    }
}