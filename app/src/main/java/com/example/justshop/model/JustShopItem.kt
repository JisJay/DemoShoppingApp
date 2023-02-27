package com.example.justshop.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.justshop.data.FavItem
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


/**
 * Extension function to convert [JustShopItem] to [FavItem].
 */
fun JustShopItem.toFavItem(): FavItem = FavItem(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating_rate = rating.rate,
    rating_count = rating.count
)

/**
 * Extension function to convert [FavItem] to [JustShopItem]
 */
fun FavItem.toJustShopItem(): JustShopItem = JustShopItem(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = Rating(rating_rate, rating_count)
)

