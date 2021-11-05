package com.example.notesappfragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class mainview : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var rv:RecyclerView
    lateinit var save:Button
    lateinit var et1:EditText
    private val vm by lazy{ ViewModelProvider(this).get(VM::class.java)}
    var TAG = "tt"


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_mainview, container, false)
         rv = view.findViewById(R.id.rv)
        et1 = view.findViewById(R.id.editTextTextPersonName)
         save = view.findViewById(R.id.button)
        updaterv()
        save.setOnClickListener {
            var note = et1.text.toString()

            if(note.isNotEmpty()) {
                vm.addNote(Note(null,note))
                et1.text.clear()
                it.hideKeyboard()
            }else{
                Toast.makeText(requireContext(),"Write a note", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun updaterv(){
        vm.retrive().observe(viewLifecycleOwner) {
                note ->
            rv.adapter = MyAdapter(note,this)
            rv.layoutManager = LinearLayoutManager(requireContext())

        }

    }

    fun preUpdate(item:Note) {
        var txtt = EditText(context)
        txtt.setText(item.note)
        AlertDialog.Builder(context)
            .setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->
                vm.update(txtt.text.toString(), item)
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })
            .setTitle("Update Note")
            .setView(txtt)
            .create()
            .show()
    }

    fun preDelete(item:Note){
        AlertDialog.Builder(context)
            .setPositiveButton("delete", DialogInterface.OnClickListener{
                    _,_ -> vm.delete(item)

            })
            .setNegativeButton("No", DialogInterface.OnClickListener{
                    dialog,_ -> dialog.cancel()
            })
            .setTitle("Delete Note?")
            .create()
            .show()
    }

}