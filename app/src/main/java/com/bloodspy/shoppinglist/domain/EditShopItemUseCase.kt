package com.bloodspy.shoppinglist.domain

class EditShopItemUseCase(private val shoppingListRepository: ShoppingListRepository) {
    fun editShopItem(shopItem: ShopItem) {
        shoppingListRepository.editShopItem(shopItem)
    }
}