package com.example.a4_basic

import androidx.lifecycle.*

class MyViewModel() : ViewModel() {
    // reference to my model
    val model = Model()
    // live data for all notes
    val noteListLive: MutableLiveData<MutableList<Note>> = MutableLiveData<MutableList<Note>>()
    // live data for total Notes number
    val totalNote: MutableLiveData<Int> = MutableLiveData<Int>(0)
    // live data for if important filter is on
    val isFilted:MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    // live data for if search area is changed
    val filtedString: MutableLiveData<String> = MutableLiveData<String>()
    // live data for notes after filted
    val displayNoteLive: MutableLiveData<MutableList<Note>> = MutableLiveData<MutableList<Note>>()
    // add all observable properties here ignore this one used to debug
    val property: MutableLiveData<Int> = MutableLiveData<Int>(0)   //going to delete

    // live data for note that is clicked on
    val sp_note: MutableLiveData<Note> = MutableLiveData<Note>()
    // live data for note that if the note is new/ updated old
    val oldNote:MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    init {

    }

    // find id for new note
    fun generateId():Long{
        model.noteIdCounter += 1
        return (model.noteIdCounter -1).toLong()
    }


    // remove specfic note form note list
    fun deleteNote(n: Note){
        model.noteList.remove(n)
        noteListLive.value = model.noteList
        println("Deleted Note #${n.title}")
    }


    // call to add new rand note to note list
    fun addRandNote(){
        val n = model.newRandomNote()
        model.noteList.add(0,n)
        noteListLive.value = model.noteList
    }

    // call to add a new modified note to note list
    fun addNewNote(n: Note){
        model.noteList.add(0,n)
        noteListLive.value = model.noteList
    }

    // update note that was in the note list
    fun updateNote(updatedNote:Note){
        var n =  model.noteList.find {  x -> x.id == updatedNote.id }
        if (n != null) {
            n.title = updatedNote.title
            n.body = updatedNote.body
            n.important = updatedNote.important
        }
        noteListLive.value = model.noteList
    }

    // change which note is clicked on
    fun set_sp_note(n: Note){
        sp_note.value = n
    }

    // change if imporntant filter is on
    fun impt_filter(b:Boolean){
        isFilted.value = b
//        noteListLive.value = model.filted_noteList
    }

    // total # of notes is changed
    fun updateTotal(){
        totalNote.value = model.noteList.size

    }
    // delete all filtered notes
    fun removedDisplayed(){
        for (n in model.filted_noteList){
            deleteNote(n)
        }
    }

    // track left notes after filtering
    var match= 0

    // show new view everytime modify notes/ add notes
    fun generateDisplay(){
        var tmp = model.noteList

        if(isFilted.value!! && tmp != null){
            tmp = tmp.filter {  x -> x.important }.toMutableList()
        }

        if(filtedString.value != null&& tmp != null) {
            tmp = tmp.filter {
                    x -> x.title.lowercase().contains(filtedString.value!!) ||
                    x.body.lowercase().contains(filtedString.value!!) }.toMutableList()
        }

        model.filted_noteList = tmp
        displayNoteLive.value = model.filted_noteList
        match = model.filted_noteList.size
    }

}