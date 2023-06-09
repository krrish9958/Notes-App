package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityCreateNoteBinding
import com.example.notesapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateNote : AppCompatActivity() {
     private lateinit var auth: FirebaseAuth
//    private val noteId = intent.getStringExtra("noteId")
  // variable bnaya jo refrence lega database se ( fire base realtime database using here )
    private lateinit var dbReference : DatabaseReference

    private lateinit var binding : ActivityCreateNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)





// variable ki value ko intialize krdiya
        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser
        var uid = user?.uid
        var gmail = user?.email
        var name = user?.displayName
        dbReference = FirebaseDatabase.getInstance().getReference("users/$uid/notes")

        binding.btnSave.setOnClickListener {
            saveNote()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }
  // Method to Delete Note


    private fun saveNote() {
        var title = binding.etTitle.text.toString()
        var noteDescription = binding.etDescription.text.toString()

        if (title.isEmpty()){
            binding.etTitle.error = "Title is required"
        }
        if (noteDescription.isEmpty()){
            binding.etDescription.error = "Description is Required"
        }
        // apne note ko ek unique id / primary key dene k liye
        var noteId = dbReference.push().key!!

        var note = NotesData(noteId, title, noteDescription)
        dbReference.child(noteId).setValue(note).addOnSuccessListener {

            Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
            .addOnFailureListener {
                error ->
                Toast.makeText(this, "Failed ${error.message}", Toast.LENGTH_SHORT).show()
            }

    }


}