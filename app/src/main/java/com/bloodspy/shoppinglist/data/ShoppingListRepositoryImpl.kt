package com.bloodspy.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bloodspy.shoppinglist.domain.ShopItem
import com.bloodspy.shoppinglist.domain.ShopItem.Companion.UNDEFINED_ID
import com.bloodspy.shoppinglist.domain.ShoppingListRepository
import java.util.NoSuchElementException

object ShoppingListRepositoryImpl: ShoppingListRepository {
    private val shoppingListLD = MutableLiveData<List<ShopItem>>()
    private val shoppingList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0
        get() = field++

    init {
        for(i in 0..10) {
            shoppingList.add(i, ShopItem("$i", i, true))
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if(shopItem.id == UNDEFINED_ID) {
            shopItem.id = autoIncrementId
        }

        shoppingList.add(shopItem)
        updateShopListLD()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shoppingList.remove(shopItem)
        updateShopListLD()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        deleteShopItem(oldShopItem)
        addShopItem(shopItem)
    }

    override fun getShopItem(id: Int): ShopItem {
        return shoppingList.find { it.id == id } ?: throw NoSuchElementException(
            "Element with id $id not found"
        )
    }

    override fun getShopList(): LiveData<List<ShopItem>> = shoppingListLD

    private fun updateShopListLD() {
        shoppingListLD.value = shoppingList.toList()
    }
}