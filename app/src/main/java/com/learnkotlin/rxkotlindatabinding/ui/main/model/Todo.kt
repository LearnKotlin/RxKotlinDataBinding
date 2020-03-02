package com.learnkotlin.rxkotlindatabinding.ui.main.model

import com.google.gson.annotations.SerializedName

data class Todo (

    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean,

    var assignedUsername: String

)