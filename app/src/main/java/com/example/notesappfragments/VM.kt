package com.example.notesappfragments

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VM(application: Application): AndroidViewModel(application) {
    var app = application
    var TAG = "VM"
    val db = Firebase.firestore
    private var note: MutableLiveData<MutableList<Note>> = MutableLiveData()


    fun addNote(note: Note) {
        db.collection("users")
            .add(note)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(app, "${note.note} Saved Successfully ", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
        retrive()
    }

    fun retrive(): MutableLiveData<MutableList<Note>> {
        CoroutineScope(Dispatchers.IO).launch {
            var mynotes: MutableList<Note> = mutableListOf()
            db.collection("users")
                .get()
                .addOnSuccessListener { notes ->
                    for (note in notes) {
                        note.data.map { (key, value) ->
                            if (value != null) {
                                mynotes.add(Note(note.id, value.toString()))
                            }
                        }
                    }
                    note.postValue(mynotes)
                }}
            Log.d(TAG, "$note")
            return note

        }


    fun update(edittextString: String, note: Note) {
        CoroutineScope(Dispatchers.IO).launch {

            db.collection("users")
                .get()
                .addOnSuccessListener { res ->
                    for (i in res) {
                        if (i.id == note.id) {
                            db.collection("users").document(note.id!!)
                                .update("note", edittextString)
                            Toast.makeText(app, "Updating successfuly", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            .addOnFailureListener {
                Log.d(TAG, "error Update")
                Toast.makeText(app, "Updating Failure", Toast.LENGTH_SHORT).show()
            }}
        retrive()
    }

    fun delete(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("users")
                .get()
                .addOnSuccessListener { res ->
                    for (i in res) {
                        if (i.id == note.id) {
                            db.collection("users").document(note.id!!).delete()
                            Toast.makeText(app, "deleting successfuly", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "error deleting")
                    Toast.makeText(app, "deleting Failure", Toast.LENGTH_SHORT).show()
                }
            retrive()
        }
    }
}


