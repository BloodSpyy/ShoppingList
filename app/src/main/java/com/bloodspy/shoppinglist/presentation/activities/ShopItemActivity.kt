package com.bloodspy.shoppinglist.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bloodspy.shoppinglist.R
import com.bloodspy.shoppinglist.domain.entities.ShopItem
import com.bloodspy.shoppinglist.presentation.fragments.ShopItemFragment
import kotlinx.coroutines.launch

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEndWorkListener {
    private var screenMode = UNKNOWN_SCREEN_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_MODE_ADD = "extra_add"
        private const val EXTRA_MODE_EDIT = "extra_edit"
        private const val EXTRA_SHOP_ITEM_ID = "extra_id"

        private const val UNKNOWN_SCREEN_MODE = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, EXTRA_MODE_ADD)

            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)

            intent.putExtra(EXTRA_SCREEN_MODE, EXTRA_MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()

        lifecycleScope.launch {  }
        val fragment = when (screenMode) {
            EXTRA_MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            EXTRA_MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            else -> throw RuntimeException("Unknown screen mode: $screenMode")
        }

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.shop_item_container, fragment)
                .commit()
        }
    }

    private fun parseIntent() {
        with(intent) {
            if (!hasExtra(EXTRA_SCREEN_MODE)) {
                throw RuntimeException("Param screen mode not found")
            }

            val mode = getStringExtra(EXTRA_SCREEN_MODE).toString()

            if (mode != EXTRA_MODE_ADD && mode != EXTRA_MODE_EDIT) {
                throw RuntimeException("Unknown screen mode: $mode")
            }

            screenMode = mode

            if (screenMode == EXTRA_MODE_EDIT) {
                if (!hasExtra(EXTRA_SHOP_ITEM_ID)) {
                    throw RuntimeException("Param shop item id not found")
                }

                shopItemId = getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
            }
        }
    }

    override fun onEndWork() {
        finish()
    }
}