package com.example.justshop.data

import androidx.room.*
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: FavItem)

    @Update
    suspend fun update(item: FavItem)

    @Delete
    suspend fun delete(item: FavItem)

    @Query("SELECT * from fav_items WHERE id = :id")
    fun getItem(id: Int): Flow<FavItem>

    @Query("SELECT * from fav_items ORDER BY id ASC")
    suspend fun getAllItems(): List<FavItem>

}