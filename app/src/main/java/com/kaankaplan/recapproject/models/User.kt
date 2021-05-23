package com.kaankaplan.recapproject.models

data class User (
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email : String,
    val password: String
)