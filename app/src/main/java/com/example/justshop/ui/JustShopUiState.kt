package com.example.justshop.ui

import com.example.justshop.model.JustShopItem

/**
 * UI State
 */
data class JustShopUiState (
    /**
     * Shopping List Items to be displayed
     */
    val justShopItemsList : List<JustShopItem> = mutableListOf(),
    /**
     * Loading failed is Error screen to be displayed
     */
    val isError : Boolean = false,
    /**
     * Loading screen
     */
    val isLoading : Boolean = false,
    /**
     * Whether favourite screen to be displayed
     */
    val isFavouriteScreen : Boolean = false,
    /**
     * Show favourites for better UI need to store the list
     */
    val favourites : List<JustShopItem> = mutableListOf(),
    //var favourites : MutableSet<Int>
)