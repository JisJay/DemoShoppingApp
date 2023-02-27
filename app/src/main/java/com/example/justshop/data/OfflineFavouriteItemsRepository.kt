package com.example.justshop.data

import kotlinx.coroutines.flow.Flow

import LocalDbItemsRepository

/**
 * Class implementation of LocalDbItemsRepository which returns the values from DB
 */
class OfflineFavouriteItemsRepository(private val itemDao: FavItemDao) : LocalDbItemsRepository {
    override suspend fun getAllItems(): List<FavItem> = itemDao.getAllItems()

    override fun getItemStream(id: Int): Flow<FavItem?> = itemDao.getItem(id)

    override suspend fun insertItem(item: FavItem) = itemDao.insert(item)

    override suspend fun deleteItem(item: FavItem) = itemDao.delete(item)

    override suspend fun updateItem(item: FavItem) = itemDao.update(item)
}