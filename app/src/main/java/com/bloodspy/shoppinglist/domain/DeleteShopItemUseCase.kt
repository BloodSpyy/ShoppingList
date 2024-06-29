package com.bloodspy.shoppinglist.domain

class DeleteShopItemUseCase(private val shoppingListRepository: ShoppingListRepository) {
    fun deleteShopItem(shopItem: ShopItem) {
        shoppingListRepository.deleteShopItem(shopItem)
    }
}