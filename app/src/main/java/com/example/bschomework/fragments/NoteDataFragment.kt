package com.example.bschomework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.bschomework.MainActivity
import com.example.bschomework.NoteData
import com.example.bschomework.R
import com.example.bschomework.databinding.FragmentNoteDataBinding

class NoteDataFragment : Fragment(R.layout.fragment_note_data) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNoteDataBinding.inflate(inflater, container, false)

        setFragmentResultListener("dataForNoteDataFragment") { _, bundle ->
            val result = bundle.getSerializable("noteData") as NoteData

            binding.header.text = result.header
            binding.note.text = result.note
            binding.date.text = result.date.toString()

            (activity as MainActivity).setNoteDataFragmentVisibility()
        }

        return binding.root
    }
}