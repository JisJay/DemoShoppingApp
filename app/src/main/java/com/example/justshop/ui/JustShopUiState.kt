package com.example.justshop.ui

/*
 * The UI State which stores the user preference, in this case the set of favourites
 */
data class JustShopUiState(
    var favourites: MutableSet<Int>? = null
)
