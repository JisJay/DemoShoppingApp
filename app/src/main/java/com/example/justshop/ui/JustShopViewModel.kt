package com.example.justshop.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/*
 * The ModelView Class which handles all the UI events and updates the UI State
 */
class JustShopViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(JustShopUiState())

    //Backing Property
    val uiState = _uiState.asStateFlow()

    //Initialization
    init {
        initialize()
    }

    /*
     * Initialize the favourites List
     */
    private fun initialize() {
        _uiState.value.favourites = mutableSetOf()
    }

    /*
     * Adds the selected item to the favourite list as part of the UIState
     */
    fun addToFavorites(justShopItemId: Int) {
        var list: MutableSet<Int>? = _uiState.value.favourites?.toMutableSet()
        list?.add(justShopItemId)
        _uiState.update { currentState ->
            currentState.copy(favourites = list)
        }
    }

    /*
     * Removes the selected item to the favourite list
     */
    fun removeFromFavorites(justShopItemId: Int) {
        var list: MutableSet<Int>? = _uiState.value.favourites?.toMutableSet()
        list?.remove(justShopItemId)
        _uiState.update { currentState ->
            currentState.copy(favourites = list)
        }
    }
}