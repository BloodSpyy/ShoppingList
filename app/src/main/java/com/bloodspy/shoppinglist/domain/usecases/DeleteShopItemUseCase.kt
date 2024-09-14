package com.bloodspy.shoppinglist.domain.usecases

import com.bloodspy.shoppinglist.domain.entities.ShopItem
import com.bloodspy.shoppinglist.domain.repositories.ShopListRepository
import javax.inject.Inject

class DeleteShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
) {
    suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListRepository.deleteShopItem(shopItem)
    }
}