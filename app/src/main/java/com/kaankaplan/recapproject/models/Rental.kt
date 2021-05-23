package com.kaankaplan.recapproject.models

import java.util.*

data class Rental(
    val id: Int,
    val carId: Int,
    val customerId: Int,
    val rentDate: String?,
    val returnDate: String?
)