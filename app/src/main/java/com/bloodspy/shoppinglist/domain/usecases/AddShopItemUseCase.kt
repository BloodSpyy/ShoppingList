package com.bloodspy.shoppinglist.domain.usecases

import com.bloodspy.shoppinglist.domain.entities.ShopItem
import com.bloodspy.shoppinglist.domain.repositories.ShopListRepository
import javax.inject.Inject

class AddShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
) {
    suspend fun addShopItem(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}