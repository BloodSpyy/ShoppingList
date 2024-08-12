package com.bloodspy.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.bloodspy.shoppinglist.domain.entities.ShopItem
import com.bloodspy.shoppinglist.domain.repositories.ShopListRepository

class ShopListRepositoryImpl(application: Application) : ShopListRepository {
    private val shopItemDao = AppDatabase.getInstance(application).shoppingListDao()

    private val mapper = ShopListMapper()

    override suspend fun addShopItem(shopItem: ShopItem) = shopItemDao.addShopItem(
        mapper.mapEntityToDbModel(shopItem)
    )

    override suspend fun deleteShopItem(shopItem: ShopItem) = shopItemDao.deleteShopItem(shopItem.id)

    override suspend fun editShopItem(shopItem: ShopItem) = shopItemDao.addShopItem(
        mapper.mapEntityToDbModel(shopItem)
    )

    override suspend fun getShopItem(id: Int): ShopItem = mapper.mapDbModelToEntity(
        shopItemDao.getShopItem(id)
    )

    override fun getShopList(): LiveData<List<ShopItem>> = shopItemDao.getShopList().map {
        mapper.mapListDbModelsToListEntities(it)
    }
}