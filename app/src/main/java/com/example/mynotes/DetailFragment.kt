package com.example.mynotes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.mynotes.databinding.FragmentDetailBinding

class DetailFragment : Fragment(), OnFragmentDataListener {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var onFragmentDataListener: OnFragmentDataListener


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onFragmentDataListener = requireActivity() as OnFragmentDataListener
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val note = arguments?.getSerializable("note") as Note

        binding.detailFragmentTextViewTitleTV.text = note.name

        binding.detailFragmentSaveButtonBTN.setOnClickListener{
            val db = DBHelper(requireContext(),null)
            note.name = binding.detailFragmentNoteEditText.text.toString()
            var newNote = ""
            if (binding.detailFragmentNoteEditText.text.isNotEmpty()){
                newNote = binding.detailFragmentNoteEditText.text.toString()
            } else {
                newNote = note.name
            }
            db.updateNote(note.id, newNote)
            onData(note)
        }
        return binding.root
    }

    override fun onData(note: Note) {
        val bundle = Bundle()
        bundle.putSerializable("note", note)
        val transaction = this.fragmentManager?.beginTransaction()
        val firstFragment = FirstFragment()
        firstFragment.arguments = bundle

        transaction?.replace(R.id.containerID,firstFragment)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
    }

}