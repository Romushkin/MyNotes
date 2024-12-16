package com.example.mynotes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter (private var notesList: MutableList<Note>):
    RecyclerView.Adapter<CustomAdapter.NoteViewHolder>() {

    private var onNoteClickListener: OnNoteClickListener? = null

    interface OnNoteClickListener {
        fun onNoteClick(note: Note, position: Int)
    }


    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val nameTv: TextView = itemView.findViewById(R.id.itemNameNoteTextViewTV)
        val dateTv: TextView = itemView.findViewById(R.id.itemDateNoteTextViewTV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_view, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun getItemCount() = notesList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notesList[position]
        holder.nameTv.text = note.date
        holder.dateTv.text = "№${note.id}: ${note.name}"
        holder.itemView.setOnClickListener{
            if (onNoteClickListener != null) {
                onNoteClickListener!!.onNoteClick(note, position)
            }
        }
    }

    fun updateData(newNoteList: MutableList<Note>) {
        notesList = newNoteList
    }

}