package com.example.justshop.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.justshop.JustShopApplication
import com.example.justshop.data.DefaultAppContainer
import com.example.justshop.data.JustShopItemsRepository
import com.example.justshop.model.JustShopItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class JustShopViewModel(private val justShopItemsRepository: JustShopItemsRepository) : ViewModel(){

    val TAG = "JustShopViewModel"

    /**
     * Cupcake state for this order
     */
    private val _uiState = MutableStateFlow(JustShopUiState())
    val uiState: StateFlow<JustShopUiState> = _uiState.asStateFlow()

    init{
        initialize()
    }

    /**
     * Tracking the favourite ids list here for better performance
     */
    private lateinit var favouritesIds : List<Int>
    /*
     * Load the items initially
     */
    private fun initialize(){
        favouritesIds = _uiState.value.favourites.map{
            it.id
        }
        updateViewStatus()
        loadItems()
    }

    fun showShopItems() {
        updateViewStatus(isLoading = false, isError = false, isFavouriteScreen = false)
    }
    /**
     * Load Favourites
     */
    fun loadFavourites() {
        updateViewStatus(isFavouriteScreen = true)
    }

    /**
     * Gets Items information from the Shop Items API Retrofit service and updates the
     * [JustShopItem]
     */
    fun loadItems() {
        viewModelScope.launch {
            updateViewStatus(isLoading = true)
            try {
                updateShoppingList(justShopItemsRepository.getJustShopItems())
            } catch (e: IOException) {
                Log.e(TAG, "IOException $e")
                updateViewStatus(isError = true)
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException $e")
                updateViewStatus(isError = true)
            } catch (e: Exception) {
                Log.e(TAG, "Exception::: $e")
                updateViewStatus(isError = true)
        }
        }
    }


     private fun updateShoppingList(listItems : List<JustShopItem>){
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = false,
                isError = false,
                justShopItemsList = listItems
            )
        }
    }

    private fun updateViewStatus(isLoading : Boolean = false,
                                 isError: Boolean = false,
                                 isFavouriteScreen : Boolean = false){
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = isLoading,
                isError = isError,
                isFavouriteScreen = isFavouriteScreen
            )
        }
    }

    private fun updateFavourites(favourites : List<JustShopItem>){
        favouritesIds = favourites.map {
            it.id
        }
        _uiState.update { currentState ->
            currentState.copy(
                favourites = favourites
            )
        }
    }


    fun updateFavorites(justShopItem: JustShopItem) {
        var favourites = _uiState.value.favourites.toMutableList()
        if(isFavourite(justShopItem.id)) {
            favourites.remove(justShopItem)
        } else {
            favourites.add(justShopItem)
        }
        updateFavourites(favourites)
    }

    fun isFavourite(justShopItem : Int) : Boolean{
        return favouritesIds.contains(justShopItem)
    }

    /**
     * Factory for [MarsViewModel] that takes [MarsPhotosRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY]
                if(app==null) {
                    JustShopViewModel(justShopItemsRepository = DefaultAppContainer().justShopItemsRepository)
                } else {
                    val application = (app as JustShopApplication)
                    val justShopItemsRepository = application.container.justShopItemsRepository
                    JustShopViewModel(justShopItemsRepository = justShopItemsRepository)
                }
            }
        }
    }
}