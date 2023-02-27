package com.example.justshop.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data Entity Class
 */
@Entity(tableName = "fav_items")
data class FavItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating_rate: Double,
    val rating_count: Int
)
