package com.bloodspy.shoppinglist.domain.usecases

import com.bloodspy.shoppinglist.domain.ShopItem
import com.bloodspy.shoppinglist.domain.repository.ShopListRepository

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun addShopItem(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}