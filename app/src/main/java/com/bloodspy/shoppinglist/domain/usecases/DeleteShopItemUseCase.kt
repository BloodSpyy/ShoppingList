package com.bloodspy.shoppinglist.domain.usecases

import com.bloodspy.shoppinglist.domain.ShopItem
import com.bloodspy.shoppinglist.domain.repository.ShopListRepository

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun deleteShopItem(shopItem: ShopItem) {
        shopListRepository.deleteShopItem(shopItem)
    }
}