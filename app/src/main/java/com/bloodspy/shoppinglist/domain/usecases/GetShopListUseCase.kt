package com.bloodspy.shoppinglist.domain.usecases

import androidx.lifecycle.LiveData
import com.bloodspy.shoppinglist.domain.ShopItem
import com.bloodspy.shoppinglist.domain.repository.ShopListRepository

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopList(): LiveData<List<ShopItem>>  = shopListRepository.getShopList()
}