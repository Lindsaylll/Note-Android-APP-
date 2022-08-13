package com.example.a4_basic

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class FragmentNote : Fragment() {

    private val viewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // layout is defined in "res/layouts/fragment_2.xml"
        val root = inflater.inflate(R.layout.fragment_note, container, false)

        // once an old note is changed and saved, it will update the notelist
        viewModel.oldNote.observe(this){bool ->
            if(bool){
//                println("observed!!!")
                val nid = root.findViewById<TextView>(R.id.note)
                val tt = root.findViewById<TextView>(R.id.n_title)
                val bd = root.findViewById<TextView>(R.id.n_body)
                val ipt = root.findViewById<Switch>(R.id.s_imp)
                if(viewModel.sp_note.value!!.important){
                    ipt.isChecked = true
                }
                nid.text = "Edit Note #" + viewModel.sp_note.value?.id
                tt.text = viewModel.sp_note.value?.title
                bd.text = viewModel.sp_note.value?.body

            } else {
                println("it false!!!")
            }




        }

        // if save button is clicked, a new note/old note will be add/update to note list
        val save = root.findViewById<Button>(R.id.b_save)
        save.setOnClickListener {
            val tt = root.findViewById<TextView>(R.id.n_title).text.toString()
            val bd = root.findViewById<TextView>(R.id.n_body).text.toString()
            val ipt = root.findViewById<Switch>(R.id.s_imp).isChecked

            if(viewModel.oldNote.value!!){
                val newNote = Note(viewModel.sp_note.value!!.id,tt,bd,ipt,false)
                viewModel.updateNote(newNote)
            } else {
                val id = viewModel.generateId()
                val newNote = Note(id,tt,bd,ipt,false)
                viewModel.addNewNote(newNote)

            }

//          this will navigate to source page
            findNavController().navigate(R.id.action_fragment_note_pop)

        }

        return root
    }
}