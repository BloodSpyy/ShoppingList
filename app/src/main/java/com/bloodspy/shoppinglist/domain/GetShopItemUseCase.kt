package com.bloodspy.shoppinglist.domain

class GetShopItemUseCase(private val shoppingListRepository: ShoppingListRepository) {
    fun getShopItem(id: Int): ShopItem = shoppingListRepository.getShopItem(id)

}