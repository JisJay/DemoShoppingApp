package com.example.justshop.data

import LocalDbItemsRepository
import com.example.justshop.network.ShopItemsApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import android.content.Context

/**
 * Dependency Injection Container at application level
 */
interface AppContainer {
    /**
     * JustShopItemsRepository to load the data from network/server
     */
    val justShopItemsRepository: JustShopItemsRepository

    /**
     * LocalDbItemsRepository to load the favourite data from db
     */
    val localDbItemsRepository: LocalDbItemsRepository
}

/**
 * Implementaion of Dependency Injection at the application level
 */
open class DefaultAppContainer(private val context: Context) : AppContainer {

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
     * DI implentation for shop items repository from network
     */
    override val justShopItemsRepository: JustShopItemsRepository by lazy {
        NetworkJustShopItemsRepository(retrofitService)
    }

    /**
     * DI implentation for shop items repository from Local DB
     */
    override val localDbItemsRepository: LocalDbItemsRepository by lazy {
        OfflineFavouriteItemsRepository(JustShopInventoryDatabase.getDatabase(context).itemDao())
    }

}

