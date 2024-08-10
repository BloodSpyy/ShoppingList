package com.bloodspy.shoppinglist.domain.usecases

import androidx.lifecycle.LiveData
import com.bloodspy.shoppinglist.domain.ShopItem
import com.bloodspy.shoppinglist.domain.repository.ShoppingListRepository

class GetShopListUseCase(private val shoppingListRepository: ShoppingListRepository) {
    fun getShopList(): LiveData<List<ShopItem>>  = shoppingListRepository.getShopList()
}