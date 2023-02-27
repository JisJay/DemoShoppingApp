package com.example.justshop.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This data class defines a Rating
 */
@Serializable
data class Rating(
    @SerialName(value = "rate") val rate: Double, @SerialName(value = "count") val count: Int
)

/**
 * This data class defines each row of JSON object being parsed
 */
@Serializable
data class JustShopItem(
    val id: Int,
    @SerialName(value = "title") val title: String,
    @SerialName(value = "price") val price: Double,
    @SerialName(value = "description") val description: String,
    @SerialName(value = "category") val category: String,
    @SerialName(value = "image") val image: String,
    @SerialName(value = "rating") val rating: Rating
)

