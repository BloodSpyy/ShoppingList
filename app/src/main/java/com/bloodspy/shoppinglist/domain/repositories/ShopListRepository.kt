package com.bloodspy.shoppinglist.domain.repositories

import androidx.lifecycle.LiveData
import com.bloodspy.shoppinglist.domain.entities.ShopItem

interface ShopListRepository {
    suspend fun addShopItem(shopItem: ShopItem)

    suspend fun deleteShopItem(shopItem: ShopItem)

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun getShopItem(id: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>
}