package com.example.a4_basic

import android.app.ActionBar
import android.graphics.Color
import android.graphics.Color.rgb
import android.graphics.Color.toArgb
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class FragmentList : Fragment() {

    private val viewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // layout is defined in "res/layouts/fragment_1.xml"
        val root = inflater.inflate(R.layout.fragment_list, container, false)

        // add UI handles for navigation of add button
        val buttonAdd = root.findViewById<Button>(R.id.b_add)
        buttonAdd.setOnClickListener {
            // (setup navigation actions in "rs/navigation/navigation.xml")
//            val vv = inflater.inflate(R.layout.fragment_note,null)
//            val b = root.findViewById<TextView>(R.id.n_title)
//            b.text = "aaaaa"
            viewModel.oldNote.value = false
            findNavController().navigate(R.id.action_list_to_note)
        }

        // if there is 0 notes clear is disabled, otherwise not
        val clear = root.findViewById<Button>(R.id.b_clear)
        viewModel.totalNote.observe(this){it ->
            clear.isEnabled = it > 0
        }

        //if click on clear it will remove all notes
        clear.setOnClickListener{
            viewModel.removedDisplayed()
        }

        // when observe new added note, the view should update
        viewModel.noteListLive.observe(this) {it->
//            println("here once !!!!!!")
//            println("${model.noteList.size}")
            viewModel.updateTotal()
            viewModel.generateDisplay()
        }

        // add UI handlers that call your viewmodel here
        val nl = root.findViewById<LinearLayout>(R.id.linear_n_list)
        // when filter is on, displayNoteLive is change, and the notes displayed will be updated
        viewModel.displayNoteLive.observe(this) { notes ->

            nl.removeAllViews()

            //update UI, generate view
            for(n in notes){
                val view = inflater.inflate(R.layout.note_item,null)
                val tt = view.findViewById<TextView>(R.id.note_item_title)
                val bd = view.findViewById<TextView>(R.id.note_item_body)
                val impt = n.important
                val cl = rgb(250, 218, 221)
                if(impt) {
                    view.setBackgroundColor(cl)
                }

                //if any of the button D is clicked,note is deleted
                val buttonDelete = view.findViewById<Button>(R.id.b_delete)
                buttonDelete.setOnClickListener {
//                    println("clicked delete!!!! ${n.title}")
                    if (n != null) {
                        viewModel.deleteNote(n)
                    }
                }

                tt.text = n.title
                bd.text = n.body

                // track which note is clicked on
                view.setOnClickListener {
//                    println("clicked!!!! ${n.title}")
                    viewModel.set_sp_note(n)
                    findNavController().navigate(R.id.action_list_to_specific)
                }

                // add to parent
                nl.addView(view)
            }
        }


        // find switch that filter important
        val impt_filter = root.findViewById<Switch>(R.id.s_important)
        // if it is clicked, a live data will be modified
        impt_filter.setOnClickListener {
            viewModel.impt_filter(impt_filter.isChecked)
        }
        // that live data will be observe and ask to update view
        viewModel.isFilted.observe(this) {
            viewModel.generateDisplay()
        }

        // obtrain what is typed into the search area
        val searchArea = root.findViewById<EditText>(R.id.search)
        searchArea.addTextChangedListener { text->
            println("${text}")
            viewModel.filtedString.value = text.toString()
        }
        // if there is anything typed, a live data changed, and view will be notified to update
        viewModel.filtedString.observe(this) {
            viewModel.generateDisplay()
        }

        //once click on rand, a new rand note will ganerated
        val buttonRandom = root.findViewById<Button>(R.id.b_random)
        buttonRandom.setOnClickListener {
            viewModel.addRandNote()

        }

        return root
    }
}