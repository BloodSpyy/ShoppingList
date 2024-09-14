package com.bloodspy.shoppinglist.di.module

import android.app.Application
import com.bloodspy.shoppinglist.data.AppDatabase
import com.bloodspy.shoppinglist.data.ShopListDao
import com.bloodspy.shoppinglist.data.ShopListRepositoryImpl
import com.bloodspy.shoppinglist.di.scope.ApplicationScope
import com.bloodspy.shoppinglist.domain.repositories.ShopListRepository
import com.bloodspy.shoppinglist.presentation.fragments.ShopItemFragment
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DomainModule {
    @Binds
    @ApplicationScope
    fun bindShopListRepository(shopListRepositoryImpl: ShopListRepositoryImpl): ShopListRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideShopItemDao(application: Application): ShopListDao =
            AppDatabase.getInstance(application).shoppingListDao()
    }
}