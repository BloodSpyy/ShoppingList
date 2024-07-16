package com.bloodspy.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.bloodspy.shoppinglist.data.ShoppingListRepositoryImpl
import com.bloodspy.shoppinglist.domain.DeleteShopItemUseCase
import com.bloodspy.shoppinglist.domain.EditShopItemUseCase
import com.bloodspy.shoppinglist.domain.GetShopListUseCase
import com.bloodspy.shoppinglist.domain.ShopItem

class MainViewModel() : ViewModel() {
    private val shoppingListRepositoryImpl = ShoppingListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(shoppingListRepositoryImpl)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(shoppingListRepositoryImpl)
    private val editShopItemUseCase = EditShopItemUseCase(shoppingListRepositoryImpl)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newShopItem = shopItem.copy(isEnabled = !shopItem.isEnabled)

        editShopItemUseCase.editShopItem(newShopItem)
    }
}