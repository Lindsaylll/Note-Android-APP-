package com.example.a4_basic

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*

import androidx.activity.viewModels
import androidx.navigation.Navigation.findNavController

class MainActivity : AppCompatActivity() {

    // create view model using delegation
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // to track how many notes are left after filtering
        var filtedSize = 0
        // if filter is on, the subtile will be in the format: match X of xx notes
        viewModel.isFilted.observe(this) {it ->
            if(it){
                if(viewModel.model.filted_noteList != null) {filtedSize = viewModel.model.filted_noteList.size}
                else {filtedSize = 0}
                supportActionBar?.subtitle =  "(matches ${filtedSize} of ${viewModel.totalNote.value} notes)"
            } else {
                supportActionBar?.subtitle =  "(${viewModel.totalNote.value} notes)"
            }
//            supportActionBar?.subtitle =  "subtitle"
        }

        // if something is searched, the subtile will be in the format: match X of xx notes
        viewModel.filtedString.observe(this) {it ->
            if(it != null){
                if(viewModel.model.filted_noteList != null) {filtedSize = viewModel.model.filted_noteList.size}
                else {filtedSize = 0}
                supportActionBar?.subtitle =  "(matches ${filtedSize} of ${viewModel.totalNote.value} notes)"
            } else {
                supportActionBar?.subtitle =  "(${viewModel.totalNote.value} notes)"
            }
//            supportActionBar?.subtitle =  "subtitle"
        }

        // if new note added, the subtitle is updated
        viewModel.totalNote.observe(this) { it ->
                supportActionBar?.subtitle =  "($it notes)"
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.actionName -> {
                // update viewModel for this action
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //endregion
}