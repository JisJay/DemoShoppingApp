package com.example.justshop.data

import android.util.Log
import com.example.justshop.model.JustShopItem
import com.example.justshop.network.ShopItemsApi


/**
 * The repository that fetches Shopping Items from shopItemsApi
 */
interface JustShopItemsRepository {

    /* Fetches shopping items from the ShopItemsApi     */
    suspend fun getJustShopItems(): List<JustShopItem>
}

/**
 * A repository that connects to network and load the data from server
 */
class NetworkJustShopItemsRepository(
    private val shopItemsApi: ShopItemsApi
) : JustShopItemsRepository {
    override suspend fun getJustShopItems(): List<JustShopItem> {
        try {
            return shopItemsApi.getShopItems()

        } catch (e: Exception) {
            throw e

        }

    }
}

