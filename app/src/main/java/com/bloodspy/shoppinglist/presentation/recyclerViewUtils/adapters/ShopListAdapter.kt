package com.bloodspy.shoppinglist.presentation.recyclerViewUtils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.bloodspy.shoppinglist.R
import com.bloodspy.shoppinglist.databinding.ShopItemDisabledBinding
import com.bloodspy.shoppinglist.databinding.ShopItemEnabledBinding
import com.bloodspy.shoppinglist.domain.entities.ShopItem
import com.bloodspy.shoppinglist.presentation.recyclerViewUtils.callbacks.ShopItemDiffCallback
import com.bloodspy.shoppinglist.presentation.recyclerViewUtils.viewholders.ShopItemViewHolder

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(
    ShopItemDiffCallback()
) {
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    companion object {
        const val SHOP_ITEM_ENABLED_VIEW_TYPE = 100
        const val SHOP_ITEM_DISABLED_VIEW_TYPE = 200

        const val MAX_POOL_SIZE = 15
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            SHOP_ITEM_ENABLED_VIEW_TYPE -> R.layout.shop_item_enabled
            SHOP_ITEM_DISABLED_VIEW_TYPE -> R.layout.shop_item_disabled
            else -> throw RuntimeException("Not search view type: $viewType")
        }

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )

        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)

        val binding = holder.binding

        when (binding) {
            is ShopItemEnabledBinding -> {
                binding.shopItem = shopItem
            }

            is ShopItemDisabledBinding -> {
                binding.shopItem = shopItem
            }
        }

        with(binding.root) {
            setOnLongClickListener {
                onShopItemLongClickListener?.invoke(shopItem)
                true
            }
            setOnClickListener {
                onShopItemClickListener?.invoke(shopItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)

        return if (shopItem.isEnabled) {
            SHOP_ITEM_ENABLED_VIEW_TYPE
        } else {
            SHOP_ITEM_DISABLED_VIEW_TYPE
        }
    }
}