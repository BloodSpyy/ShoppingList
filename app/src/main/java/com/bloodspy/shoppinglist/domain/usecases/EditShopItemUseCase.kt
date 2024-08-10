package com.bloodspy.shoppinglist.domain.usecases

import com.bloodspy.shoppinglist.domain.ShopItem
import com.bloodspy.shoppinglist.domain.repository.ShoppingListRepository

class EditShopItemUseCase(private val shoppingListRepository: ShoppingListRepository) {
    fun editShopItem(shopItem: ShopItem) {
        shoppingListRepository.editShopItem(shopItem)
    }
}