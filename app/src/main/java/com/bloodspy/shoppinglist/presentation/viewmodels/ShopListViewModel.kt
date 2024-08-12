package com.bloodspy.shoppinglist.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bloodspy.shoppinglist.data.ShopListRepositoryImpl
import com.bloodspy.shoppinglist.domain.entities.ShopItem
import com.bloodspy.shoppinglist.domain.usecases.DeleteShopItemUseCase
import com.bloodspy.shoppinglist.domain.usecases.EditShopItemUseCase
import com.bloodspy.shoppinglist.domain.usecases.GetShopListUseCase
import kotlinx.coroutines.launch

class ShopListViewModel(application: Application) : AndroidViewModel(application) {
    private val shoppingListRepositoryImpl = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(shoppingListRepositoryImpl)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(shoppingListRepositoryImpl)
    private val editShopItemUseCase = EditShopItemUseCase(shoppingListRepositoryImpl)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            val newShopItem = shopItem.copy(isEnabled = !shopItem.isEnabled)

            editShopItemUseCase.editShopItem(newShopItem)
        }
    }
}