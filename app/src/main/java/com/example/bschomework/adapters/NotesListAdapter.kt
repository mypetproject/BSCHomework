package com.example.bschomework.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bschomework.databinding.NotesListItemBinding
import com.example.bschomework.room.NoteData

class NotesListAdapter(
    private val notes: List<NoteData>,
    private val onItemClick: ((NoteData) -> Unit)
) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        NotesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            return NoteViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position], onItemClick)
    }

    override fun getItemCount() = notes.size

    class NoteViewHolder(private val binding: NotesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteData, onItemClick: (NoteData) -> Unit) {
            binding.headerRv.text = note.header
            itemView.setOnClickListener { onItemClick.invoke(note) }
        }
    }
}