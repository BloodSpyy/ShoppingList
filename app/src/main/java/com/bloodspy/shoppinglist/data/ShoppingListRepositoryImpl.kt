package com.bloodspy.shoppinglist.data

import com.bloodspy.shoppinglist.domain.ShopItem
import com.bloodspy.shoppinglist.domain.ShopItem.Companion.UNDEFINED_ID
import com.bloodspy.shoppinglist.domain.ShoppingListRepository
import java.util.NoSuchElementException

object ShoppingListRepositoryImpl: ShoppingListRepository {
    private val shoppingList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0
        get() = field++

    override fun addShopItem(shopItem: ShopItem) {
        if(shopItem.id == UNDEFINED_ID) {
            shopItem.id = autoIncrementId
        }

        shoppingList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shoppingList.remove(shopItem)
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

    override fun getShopList(): List<ShopItem> = shoppingList
}