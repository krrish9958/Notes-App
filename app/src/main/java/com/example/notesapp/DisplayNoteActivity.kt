package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityDisplayNoteBinding
import com.example.notesapp.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DisplayNoteActivity : AppCompatActivity() {
    private lateinit var dbReference: DatabaseReference
    private lateinit var binding : ActivityDisplayNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val noteId = intent.getStringExtra("noteId")
        val noteTitle = intent.getStringExtra("noteTitle")
        val noteDes = intent.getStringExtra("noteDes")
        val noteDateTime = intent.getStringExtra("noteDateTime")
        dbReference = FirebaseDatabase.getInstance().getReference("Notes/$noteId")

        binding.displayTitle.text = noteTitle
        binding.displayDes.text = noteDes
        binding.displayDateTime.text = noteDateTime



        binding.displayDltBtn.setOnClickListener {
            if (noteId != null) {
                deleteNote(noteId)
            }
        }

    }


    private fun deleteNote(noteId: String) {
        dbReference.removeValue().addOnSuccessListener {
            Toast.makeText(this,"Deleted Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
            .addOnFailureListener {error ->
                Toast.makeText(this, "Failed ${error.message}", Toast.LENGTH_SHORT).show()
            }
    }
}