package com.bloodspy.shoppinglist.domain.usecases

import com.bloodspy.shoppinglist.domain.ShopItem
import com.bloodspy.shoppinglist.domain.repository.ShoppingListRepository

class GetShopItemUseCase(private val shoppingListRepository: ShoppingListRepository) {
    fun getShopItem(id: Int): ShopItem = shoppingListRepository.getShopItem(id)

}