package com.bloodspy.shoppinglist.domain.usecases

import androidx.lifecycle.LiveData
import com.bloodspy.shoppinglist.domain.entity.ShopItem
import com.bloodspy.shoppinglist.domain.repository.ShoppingListRepository

class GetShopListUseCase(private val shoppingListRepository: ShoppingListRepository) {
    fun getShopList(): LiveData<List<ShopItem>>  = shoppingListRepository.getShopList()
}