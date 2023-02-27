package com.example.justshop.data

import com.example.justshop.network.ShopItemsApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Dependency Injection Container at application level
 */
interface AppContainer {
    val justShopItemsRepository: JustShopItemsRepository
}

/**
 * Implementaion of Dependency Injection at the application level
 */
class DefaultAppContainer : AppContainer {

    /* Base URL to download the items from the URL */
    private val BASE_URL = "https://fakestoreapi.com/"


    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL).build()


    /**
     * Retrofit service object for creating API calls
     */
    private val retrofitService: ShopItemsApi by lazy {
        retrofit.create(ShopItemsApi::class.java)
    }

    /**
     * DI implentation for shop items repository
     */
    override val justShopItemsRepository: JustShopItemsRepository by lazy {
        NetworkJustShopItemsRepository(retrofitService)
    }

}
