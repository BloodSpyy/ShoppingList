package com.bloodspy.shoppinglist.domain

class GetShopListUseCase(private val shoppingListRepository: ShoppingListRepository) {
    fun getShopList(): List<ShopItem> = shoppingListRepository.getShopList()
}