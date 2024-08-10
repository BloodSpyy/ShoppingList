package com.bloodspy.shoppinglist.domain.usecases

import com.bloodspy.shoppinglist.domain.ShopItem
import com.bloodspy.shoppinglist.domain.repository.ShopListRepository

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}