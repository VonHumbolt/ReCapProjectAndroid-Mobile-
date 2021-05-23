package com.kaankaplan.recapproject.models

data class ListResponseModel<T>(
    val data: List<T>,
    val message: String,
    val success: Boolean
)