package com.example.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter (private val notesList:ArrayList<NotesData>):
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
        val notesView = LayoutInflater.from(parent.context).inflate(R.layout.notes_list_item,parent,false)
        return NoteViewHolder(notesView)
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {

        val currentNote = notesList[position]

        holder.title.text = currentNote.noteTitle
        holder.notesDes.text = currentNote.noteDes
        holder.dateTime.text = currentNote.dateTime
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    class NoteViewHolder(notesView: View):RecyclerView.ViewHolder(notesView) {
        val title : TextView = notesView.findViewById(R.id.titleTextv)
        val notesDes : TextView = notesView.findViewById(R.id.noteDesTextv)
        val dateTime : TextView= notesView.findViewById(R.id.dateTimeTextv)
    }


}