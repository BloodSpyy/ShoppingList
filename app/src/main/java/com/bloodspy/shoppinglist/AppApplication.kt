package com.bloodspy.shoppinglist

import android.app.Application
import com.bloodspy.shoppinglist.di.DaggerApplicationComponent

class AppApplication: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
}