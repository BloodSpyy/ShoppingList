package com.bloodspy.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.bloodspy.shoppinglist.domain.entities.ShopItem
import com.bloodspy.shoppinglist.domain.repositories.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopItemDao: ShopListDao,
    private val mapper: ShopListMapper,
) : ShopListRepository {

    override suspend fun addShopItem(shopItem: ShopItem) = shopItemDao.addShopItem(
        mapper.mapEntityToDbModel(shopItem)
    )

    override suspend fun deleteShopItem(shopItem: ShopItem) =
        shopItemDao.deleteShopItem(shopItem.id)

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