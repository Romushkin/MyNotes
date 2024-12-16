package com.example.mynotes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.databinding.FragmentFirstBinding
import java.text.SimpleDateFormat
import java.util.Date

class FirstFragment : Fragment() {

    private var noteList: MutableList<Note> = mutableListOf()
    private lateinit var onFragmentDataListener: OnFragmentDataListener
    private lateinit var binding: FragmentFirstBinding
    private lateinit var adapter: CustomAdapter
    private lateinit var db: DBHelper

    @SuppressLint("NotifyDataSetChanged", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFirstBinding.inflate(inflater, container, false)
        onFragmentDataListener = requireActivity() as OnFragmentDataListener

        db = DBHelper(requireContext(), null)
        noteList = db.getNotes()

        adapter = CustomAdapter(noteList)
        binding.fragmentRecyclerView.adapter = adapter
        binding.fragmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        binding.fragmentSaveButtonBTN.setOnClickListener {

            if (binding.fragmentNameNoteEditTextET.text.isNotEmpty()) {
                val note = binding.fragmentNameNoteEditTextET.text.toString()
                val id = noteList.size.plus(1)
                val noteToDB = Note(id, note, getTimeNow())
                db.addNote(noteToDB)

                val newNoteList = db.getNotes()
                adapter.updateData(newNoteList)
                adapter.notifyDataSetChanged()
                binding.fragmentNameNoteEditTextET.text.clear()

            } else {
                Toast.makeText(context, "Заполните поле", Toast.LENGTH_SHORT).show()
            }
        }

        adapter.setOnNoteClickListener(object : CustomAdapter.OnNoteClickListener {
            override fun onNoteClick(note: Note, position: Int) {
                onFragmentDataListener.onData(note)
            }
        })

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        val newNotes = db.getNotes()
        adapter.updateData(newNotes)
        adapter.notifyDataSetChanged()

    }

    @SuppressLint("SimpleDateFormat")
    fun getTimeNow(): String {
        val datetime = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date())
        return datetime
    }
}
