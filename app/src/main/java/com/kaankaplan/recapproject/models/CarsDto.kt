package com.kaankaplan.recapproject.models

import java.io.Serializable

data class CarsDto(
    val brandId: Int,
    val brandName: String,
    val carId: Int,
    val carName: String,
    val colorId: Int,
    val colorName: String,
    val dailyPrice: Int,
    val findeks: Int,
    val imagePath: String,
    val modelYear: Int
) : Serializable