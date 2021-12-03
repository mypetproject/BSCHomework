package com.example.bschomework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesListAdapter(
    private val notes: MutableList<NoteData>,
    private val onItemClick: ((NoteData, Int) -> Unit)?
) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.notes_list_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
        holder.itemView.isSelected = (selectedPosition == position)
    }

    override fun getItemCount() = notes.size

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val header: TextView = view.findViewById(R.id.header_rv)

        init {
            view.setOnClickListener {
                onItemClick?.invoke(notes[absoluteAdapterPosition], absoluteAdapterPosition)
            }
        }

        fun bind(note: NoteData) {
            header.text = note.header
        }
    }
}