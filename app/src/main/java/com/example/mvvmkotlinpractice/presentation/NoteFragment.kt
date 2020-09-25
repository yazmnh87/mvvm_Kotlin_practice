package com.example.mvvmkotlinpractice.presentation

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.core.data.Note
import com.example.mvvmkotlinpractice.R
import com.example.mvvmkotlinpractice.framework.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note.*


class NoteFragment : Fragment() {

    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note("", "", 0L, 0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        checkButton.setOnClickListener{
            if(titleView.text.toString() != "" || contentView.text.toString() != ""){
            val time = System.currentTimeMillis()
            currentNote.title = contentView.text.toString()
                currentNote.updateTime = time
                if(currentNote.id == 0L){
                    currentNote.creationTime = time
                }

                viewModel.saveNote(currentNote)
        }else{
            Navigation.findNavController(it).popBackStack()}
            }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.saved.observe(this, Observer {
            if(it){
                Toast.makeText(context,"Done!", Toast.LENGTH_LONG).show()
                hideKeyboard()
                Navigation.findNavController(titleView).popBackStack()
            }else{
                Toast.makeText(context,"Something went wrong, :(!", Toast.LENGTH_LONG).show()

            }
        })
    }

    private fun hideKeyboard(){
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(titleView.windowToken,0)
    }

}