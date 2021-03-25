package com.example.calatour.model

import java.io.Serializable

class Offer (
    val title: String,
    val description: String,
    val price: Int,
    val image: Int,
    var isFavourite: Boolean = false

): Serializable