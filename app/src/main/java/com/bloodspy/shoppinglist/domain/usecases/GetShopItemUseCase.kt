package com.bloodspy.shoppinglist.domain.usecases

import com.bloodspy.shoppinglist.domain.entities.ShopItem
import com.bloodspy.shoppinglist.domain.repositories.ShopListRepository
import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
) {
    suspend fun getShopItem(id: Int): ShopItem = shopListRepository.getShopItem(id)
}