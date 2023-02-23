package com.example.justshop

import com.example.justshop.ui.JustShopViewModel
import org.junit.Assert.*
import org.junit.Test

class JustShopModelTest {
    private val viewModel = JustShopViewModel()
    /*
     * Test to check add to favourites function in VM
     */
    @Test
    fun gameViewModel_FavouriteItem_AddedTest()  {
        viewModel.addToFavorites(justShopItemId = 1)
        assertTrue(viewModel.uiState.value.favourites?.contains(1) ?: false)
    }


    /*
     * Test to check remove item from favourites function in VM
     */
    @Test
    fun gameViewModel_RemoveItem_AddedTest()  {
        if(viewModel.uiState.value.favourites?.contains(1)?:false) {
            viewModel.removeFromFavorites(1)
        }
        assertFalse(viewModel.uiState.value.favourites?.contains(1)?: false)
    }
}