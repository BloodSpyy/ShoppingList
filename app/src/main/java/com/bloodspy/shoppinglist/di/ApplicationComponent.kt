package com.bloodspy.shoppinglist.di

import android.app.Application
import com.bloodspy.shoppinglist.di.module.ViewModelModule
import com.bloodspy.shoppinglist.di.scope.ApplicationScope
import com.bloodspy.shoppinglist.presentation.activities.ShopListActivity
import com.bloodspy.shoppinglist.presentation.fragments.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [ViewModelModule::class])
interface ApplicationComponent {
    fun inject(shopListActivity: ShopListActivity)

    fun inject(shopItemFragment: ShopItemFragment)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}