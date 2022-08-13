package com.example.a4_basic

import kotlin.random.Random

class Model {

    //stored all the notes
    var noteList = mutableListOf<Note>()
    // track the note id
    var noteIdCounter  = 1
    // stored notes after filter
    var filted_noteList = mutableListOf<Note>()



    // generate new rand note and return this note
    fun newRandomNote():Note {

        // title is 1 to 3 words in Title Case
        val title = LoremIpsum.getRandomSequence(Random.nextInt(1, 3))
            .split(" ")
            .joinToString(" ") { it.replaceFirstChar { it.uppercase() } }

        // body is 5 to 10 sentences, each 3 to 10 words long
        var body = ""
        for (i in 0..Random.nextInt(5, 10)) {
            val sentence = LoremIpsum.getRandomSequence(Random.nextInt(3, 10))
                .replaceFirstChar { it.uppercase() }
                .plus(". ")
            body = body.plus(sentence)
        }

        // create the new note and pick random importance with prob 20%
        val note = Note(noteIdCounter.toLong(), title, body, important = (Random.nextDouble() < 0.2))
        noteIdCounter += 1
        return note
    }

}