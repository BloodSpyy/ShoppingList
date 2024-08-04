package com.bloodspy.shoppinglist.domain.usecases

import com.bloodspy.shoppinglist.domain.entity.ShopItem
import com.bloodspy.shoppinglist.domain.repository.ShoppingListRepository

class AddShopItemUseCase(private val shoppingListRepository: ShoppingListRepository) {
    fun addShopItem(shopItem: ShopItem) {
        shoppingListRepository.addShopItem(shopItem)
    }
}