package com.example.a4_basic

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [clicked_note.newInstance] factory method to
 * create an instance of this fragment.
 */
class clicked_note : Fragment() {
    // TODO: Rename and change types of parameters
    private val viewModel: MyViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_clicked_note, container, false)


        // observe which note is selected
        viewModel.sp_note.observe(this) { note ->
            val sp_id = root.findViewById<TextView>(R.id.sp_id)
            val sp_tt = root.findViewById<TextView>(R.id.sp_title)
            val sp_bd = root.findViewById<TextView>(R.id.sp_body)
            val sp_ip = root.findViewById<Chip>(R.id.chip_imp)
            sp_id.text = "Note #" + viewModel.sp_note.value?.id
            sp_tt.text = note.title
            sp_bd.text = note.body
            if(note.important){
                sp_ip.visibility = View.VISIBLE
            }
        }

        // if edit is clicked, it navigate to note view
        val buttonEdit = root.findViewById<Button>(R.id.b_edit)
        buttonEdit.setOnClickListener {
            viewModel.oldNote.value = true
            findNavController().navigate(R.id.action_view_to_edit)
        }
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment clicked_note.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            clicked_note().apply {
            }
    }
}