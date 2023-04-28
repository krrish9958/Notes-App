package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityCreateNoteBinding
import com.example.notesapp.databinding.ActivityUpdateBinding
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUpdateBinding
    private lateinit var noteId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        noteId = intent.getStringExtra("noteId").toString()
        val noteTitle = intent.getStringExtra("noteTitle").toString()
        val noteDes = intent.getStringExtra("noteDes").toString()
        val noteDateTime = intent.getStringExtra("noteDateTime")

        binding.updateTitle.setText(noteTitle)
        binding.updateDescription.setText(noteDes)

        binding.updateBtnSave.setOnClickListener {
            updateNote()
        }

        binding.updateBtnBack.setOnClickListener {
            onBackPressed()
        }


        binding.updateDeleteBtn.setOnClickListener {

            if (noteId != null) {
                deleteNote(noteId)
            }

        }




    }


    private fun deleteNote(noteId: String) {
        val dltReference = FirebaseDatabase.getInstance().getReference("Notes/$noteId")

        dltReference.removeValue().addOnSuccessListener {
            Toast.makeText(this,"Deleted Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
            .addOnFailureListener {error ->
                Toast.makeText(this, "Failed ${error.message}", Toast.LENGTH_SHORT).show()
            }
    }

        private fun updateNote(){

        val  updateReference = FirebaseDatabase.getInstance().getReference("Notes/$noteId")
 val updateTitle = binding.updateTitle.text.toString()
        val updateDes = binding.updateDescription.text.toString()
        if (updateTitle.isEmpty()){
            binding.updateTitle.error = "Title is required"
        }
        if (updateDes.isEmpty()){
            binding.updateDescription.error = "Description is Required"
        }
        var updatedNoteVariable = NotesData(noteId, updateTitle,updateDes )
        updateReference.setValue(updatedNoteVariable).addOnSuccessListener {
            Toast.makeText(this,"Successfully Updated",Toast.LENGTH_SHORT).show()
          val  intent = Intent(this@UpdateActivity, MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Failed ${error.message}",Toast.LENGTH_SHORT).show()
        }
    }

}