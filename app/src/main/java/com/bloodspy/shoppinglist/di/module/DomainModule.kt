package com.bloodspy.shoppinglist.di.module

import com.bloodspy.shoppinglist.data.ShopListRepositoryImpl
import com.bloodspy.shoppinglist.di.scope.ApplicationScope
import com.bloodspy.shoppinglist.domain.repositories.ShopListRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @Binds
    @ApplicationScope
    fun bindShopListRepository(shopListRepositoryImpl: ShopListRepositoryImpl): ShopListRepository
}