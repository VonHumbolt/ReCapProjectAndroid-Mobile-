package com.kaankaplan.recapproject.models

data class SingleResponseModel<T> (
    val data: T,
    val message: Any,
    val success: Boolean
)