package com.bloodspy.shoppinglist.domain.usecases

import com.bloodspy.shoppinglist.domain.ShopItem
import com.bloodspy.shoppinglist.domain.repository.ShopListRepository

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem(id: Int): ShopItem = shopListRepository.getShopItem(id)

}