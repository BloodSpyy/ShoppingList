package com.bloodspy.shoppinglist.domain

class AddShopItemUseCase(private val shoppingListRepository: ShoppingListRepository) {
    fun addShopItem(shopItem: ShopItem) {
        shoppingListRepository.addShopItem(shopItem)
    }
}