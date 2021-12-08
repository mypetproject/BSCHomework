package com.example.bschomework

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bschomework.databinding.NotesListItemBinding

class NotesListAdapter(
    private val notes: MutableList<NoteData>,
    private val onItemClick: ((NoteData) -> Unit)
) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        NotesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            return NoteViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
        holder.itemView.isSelected = (selectedPosition == position)
    }

    override fun getItemCount() = notes.size

    inner class NoteViewHolder(private val binding: NotesListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteData) {
            binding.headerRv.text = note.header

            itemView.setOnClickListener {
                notifyItemChanged(selectedPosition)
                notifyItemChanged(absoluteAdapterPosition)

                selectedPosition = absoluteAdapterPosition

                onItemClick.invoke(notes[absoluteAdapterPosition])
            }
        }
    }
}