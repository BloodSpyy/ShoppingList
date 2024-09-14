package com.bloodspy.shoppinglist.di.module

import androidx.lifecycle.ViewModel
import com.bloodspy.shoppinglist.di.key.ViewModelKey
import com.bloodspy.shoppinglist.presentation.viewmodels.ShopItemViewModel
import com.bloodspy.shoppinglist.presentation.viewmodels.ShopListViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindShopItemViewModel(shopItemViewModel: ShopItemViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopListViewModel::class)
    fun bindShopListViewModel(shopListViewModel: ShopListViewModel): ViewModel
}