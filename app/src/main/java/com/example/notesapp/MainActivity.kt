package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter : NoteAdapter
    private lateinit var notesList : ArrayList<NotesData>
     private lateinit var dbReference: DatabaseReference

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
        binding.addNote.setOnClickListener {
            var intent = Intent(this@MainActivity, CreateNote::class.java)
            startActivity(intent)
        }

        notesList = arrayListOf<NotesData>()
        adapter = NoteAdapter(notesList)



        binding.reCyclerView.layoutManager = LinearLayoutManager(this ,LinearLayoutManager.VERTICAL, true )
        binding.reCyclerView.setHasFixedSize(true)

        getNotesData()

//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // Filter your notes data based on the search query and update the RecyclerView
//                filterNotes(newText)
//                return true
//            }
//
//        })
    }
//
//    private fun filterNotes(query : String?) {
//        val filteredNotes = if (query.isNullOrBlank()){
//            notesList
//        }else{
//            notesList.filter {
//                it.noteTitle!!.contains(query , true) || it.noteDes!!.contains(query , true)
//            }
//        }
//        adapter.
//    }

    private fun getNotesData() {
        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser
        var uid = user?.uid
        dbReference =FirebaseDatabase.getInstance().getReference("users/$uid/notes")
        dbReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                notesList.clear()
                if (snapshot.exists()){
                    for (noteSnap in snapshot.children){
                        var noteData = noteSnap.getValue(NotesData::class.java)
                        notesList.add(noteData!!)
                    }
                    binding.reCyclerView.adapter=adapter
                    adapter.setOnItemClickListener(object : NoteAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                          val intent = Intent(this@MainActivity, UpdateActivity::class.java)

                            //ek activity se dusri activity me data bhejne k liye

                            intent.putExtra("noteId", notesList[position].noteId)
                            intent.putExtra("noteTitle", notesList[position].noteTitle)
                            intent.putExtra("noteDes", notesList[position].noteDes)
                            intent.putExtra("noteDateTime", notesList[position].dateTime)

                            startActivity(intent)
                        }

                    })

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }


}