package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityCreateNoteBinding
import com.example.notesapp.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateNote : AppCompatActivity() {


  // variable bnaya jo refrence lega database se ( fire base realtime database using here )
    private lateinit var dbReference : DatabaseReference

    private lateinit var binding : ActivityCreateNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteId = intent.getStringExtra("noteId")
        val noteTitle = intent.getStringExtra("noteTitle")
        val noteDes = intent.getStringExtra("noteDes")
        val noteDateTime = intent.getStringExtra("noteDateTime")

        binding.etTitle.setText(noteTitle)
        binding.etTitle.setText(noteDes)
        binding.etTitle.setText(noteDateTime)

// variable ki value ko intialse krdiya
        dbReference = FirebaseDatabase.getInstance().getReference("Notes")

        binding.btnSave.setOnClickListener {
            saveNote()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.deleteBtn.setOnClickListener {
            if (noteId != null) {
                deleteNote(noteId)
            }
        }
    }
  // Method to Delete Note
    private fun deleteNote(noteId: String) {
    dbReference.removeValue().addOnSuccessListener {
        Toast.makeText(this,"Deleted Successfully",Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
        .addOnFailureListener {error ->
            Toast.makeText(this, "Failed ${error.message}",Toast.LENGTH_SHORT).show()
        }
    }

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
        }
            .addOnFailureListener {
                error ->
                Toast.makeText(this, "Failed ${error.message}", Toast.LENGTH_SHORT).show()
            }
    }
}