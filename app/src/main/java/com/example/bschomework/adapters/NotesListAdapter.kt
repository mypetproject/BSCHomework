package com.example.bschomework.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bschomework.R
import com.example.bschomework.databinding.NotesListItemBinding
import com.example.bschomework.room.NoteData

class NotesListAdapter(
    private var notes: List<NoteData>,
    private val onItemClick: ((NoteData) -> Unit)
) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>(), QueryFilter<NoteData> {

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
            itemView.contentDescription = prepareContentDescription(note)
        }

        private fun prepareContentDescription(note: NoteData): String {
            val context: Context = binding.root.context

            return if (note.header.isNotEmpty()) "${context.getString(R.string.note_titled)} ${note.header}"
            else context.getString(R.string.untitled_note)
        }
    }

    override fun filter(query: String, list: List<NoteData>?) {
        notes = list?.filter { it.header.contains(query) }.orEmpty()
    }
}