package com.example.justshop.ui

import LocalDbItemsRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.justshop.JustShopApplication
import com.example.justshop.data.DefaultAppContainer
import com.example.justshop.data.FavItem
import com.example.justshop.data.JustShopItemsRepository
import com.example.justshop.model.JustShopItem
import com.example.justshop.model.toFavItem
import com.example.justshop.model.toJustShopItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class JustShopViewModel(
    private val justShopItemsRepository: JustShopItemsRepository,
    private val localFavItemsRepository: LocalDbItemsRepository
) : ViewModel() {

    val TAG = "JustShopViewModel"

    /**
     * StateFlow for JustShopUI State
     */
    private val _uiState = MutableStateFlow(JustShopUiState())
    val uiState: StateFlow<JustShopUiState> = _uiState.asStateFlow()

    init {
        initialize()
    }

    /**
     * Tracking the favourite ids list here for better performance
     */
    private lateinit var favouritesIds: List<Int>

    /*
     * Load the items initially
     */
    private fun initialize() {
        favouritesIds = _uiState.value.favourites.map {
            it.id
        }
        updateViewStatus()
        loadItems()

    }

    /**
     * Just to show the previously loaded items in the home view
     */
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
     * Initial load from local db
     * after loading the items from network server
     */
    private fun loadFavItemsFromDB() {
        viewModelScope.launch {
            try {
                val favItemsFlow = localFavItemsRepository.getAllItems()
                //Storing locally the fav items
                favouritesIds = favItemsFlow.map {
                    it.id
                }
                //Converting the items to JustShopItem
                val favList = favItemsFlow.map {
                    it.toJustShopItem()
                }
                //updating the list in the UI State
                updateFavourites(favList)
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
        Log.e(TAG, "loadFavItemsFromDB...")
        //loading fav items from DB
        loadFavItemsFromDB()
    }


    /**
     * Load the shopping list to the UI State
     */
    private fun updateShoppingList(listItems: List<JustShopItem>) {
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = false, isError = false, justShopItemsList = listItems
            )
        }
    }

    private fun updateViewStatus(
        isLoading: Boolean = false, isError: Boolean = false, isFavouriteScreen: Boolean = false
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = isLoading, isError = isError, isFavouriteScreen = isFavouriteScreen
            )
        }
    }


    /**
     * Copy Favouriteslist to UI State
     */
    private fun updateFavourites(favourites: List<JustShopItem>) {
        favouritesIds = favourites.map {
            it.id
        }
        _uiState.update { currentState ->
            currentState.copy(
                favourites = favourites
            )
        }
    }


    /**
     * The method which UI calls to update the favourite state of an item
     */
    fun updateFavorites(justShopItem: JustShopItem) {
        val favourites = _uiState.value.favourites.toMutableList()
        var isAdd = true
        if (isFavourite(justShopItem.id)) {
            isAdd = false
            favourites.remove(justShopItem)
        } else {
            favourites.add(justShopItem)
        }
        updateFavourites(favourites)

        viewModelScope.launch {
            if (isAdd) {
                saveFavLocally(justShopItem)
            } else {
                removeFavLocally(justShopItem)
            }
        }
    }

    private suspend fun saveFavLocally(justShopItem: JustShopItem) {
        viewModelScope.launch {
            localFavItemsRepository.insertItem(justShopItem.toFavItem())
        }
    }


    private suspend fun removeFavLocally(justShopItem: JustShopItem) {
        viewModelScope.launch {
            localFavItemsRepository.deleteItem(justShopItem.toFavItem())
        }
    }

    /**
     * Check whether favourite or not
     */
    fun isFavourite(justShopItem: Int): Boolean {
        return favouritesIds.contains(justShopItem)
    }

    /**
     * Factory for [JustShopViewModel] that takes [JustShopPhotosRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as JustShopApplication)
                val justShopItemsRepository = application.container.justShopItemsRepository
                val favItemsRepository = application.container.localDbItemsRepository
                JustShopViewModel(
                    justShopItemsRepository = justShopItemsRepository,
                    localFavItemsRepository = favItemsRepository
                )
            }
        }
    }
}