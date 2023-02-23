package com.example.justshop.model

import androidx.annotation.DrawableRes

/*
 * Data class to handle the ratings
 * rate - rating value
 * count - number of feedback
 */
data class Rating(
    var rate : Double,
    var count : Int
)

/*
 * Data class to handle each Item
 * id - unique id of the item
 * title - title
 * description - details
 * price - cost of the product
 * category - which category
 * imageId - resource for image
 * rating - rating details
 */
data class JustShopItem(
    var id : Int,
    var title : String,
    var description : String,
    var price : Double,
    var category : String,
    @DrawableRes var imageId : Int,
    var rating : Rating
)


