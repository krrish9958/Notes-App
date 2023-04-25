package com.example.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter (private val notesList:ArrayList<NotesData>):
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private lateinit var mListener: onItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
        val notesView = LayoutInflater.from(parent.context).inflate(R.layout.notes_list_item,parent,false)
        return NoteViewHolder(notesView, mListener)
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

    class NoteViewHolder(notesView: View, clickListener: onItemClickListener):RecyclerView.ViewHolder(notesView) {
        val title : TextView = notesView.findViewById(R.id.titleTextv)
        val notesDes : TextView = notesView.findViewById(R.id.noteDesTextv)
        val dateTime : TextView= notesView.findViewById(R.id.dateTimeTextv)

        init {
            notesView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

}