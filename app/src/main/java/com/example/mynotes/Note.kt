package com.example.mynotes

import java.io.Serializable

class Note (
    val id: Int,
    var name: String,
    val date: String
): Serializable {
}