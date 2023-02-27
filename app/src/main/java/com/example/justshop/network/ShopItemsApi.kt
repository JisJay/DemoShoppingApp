package com.example.justshop.network

import com.example.justshop.model.JustShopItem
import retrofit2.http.GET

/**
 * A public interface which exposes getShopItems() method
 */
interface ShopItemsApi {

 @GET("products?limit=10")
 suspend fun getShopItems() : List<JustShopItem>

}