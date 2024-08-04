package com.bloodspy.shoppinglist.domain.usecases

import com.bloodspy.shoppinglist.domain.entity.ShopItem
import com.bloodspy.shoppinglist.domain.repository.ShoppingListRepository

class DeleteShopItemUseCase(private val shoppingListRepository: ShoppingListRepository) {
    fun deleteShopItem(shopItem: ShopItem) {
        shoppingListRepository.deleteShopItem(shopItem)
    }
}