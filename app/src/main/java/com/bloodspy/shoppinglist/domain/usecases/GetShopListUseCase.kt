package com.bloodspy.shoppinglist.domain.usecases

import androidx.lifecycle.LiveData
import com.bloodspy.shoppinglist.domain.entities.ShopItem
import com.bloodspy.shoppinglist.domain.repositories.ShopListRepository
import javax.inject.Inject

class GetShopListUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
) {
    fun getShopList(): LiveData<List<ShopItem>>  = shopListRepository.getShopList()
}