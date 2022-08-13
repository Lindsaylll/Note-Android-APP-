package com.example.a4_basic
/*
 * CS349 Assignment Example Solution
 * NOTICE: It is an academic offence to share, reproduce,
 * or disseminate any lab, assignment, or exam solution unless
 * explicitly permitted by the instructor.
 */

// simple data class for a single note

// this is copy form a1 solution
data class Note(
    val id: Long?,
    var title: String = "",
    var body: String = "",
    var important: Boolean = false,
    var isSelected: Boolean = false
)