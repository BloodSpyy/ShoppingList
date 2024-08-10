package com.bloodspy.shoppinglist.domain.repository

import androidx.lifecycle.LiveData
import com.bloodspy.shoppinglist.domain.ShopItem

interface ShoppingListRepository {
    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(id: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>
}