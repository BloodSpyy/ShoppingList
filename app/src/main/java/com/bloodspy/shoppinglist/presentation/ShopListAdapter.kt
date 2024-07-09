package com.bloodspy.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bloodspy.shoppinglist.R
import com.bloodspy.shoppinglist.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
    val shopList = listOf<ShopItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.shop_item_enabled,
            parent,
            false
        )

        return ShopItemViewHolder(view)
    }

    override fun getItemCount(): Int = shopList.size

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]

        holder.textViewShopItem.text = shopItem.name
        holder.textViewShopItemCount.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
            true
        }
    }

    class ShopItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textViewShopItem = view.findViewById<TextView>(R.id.textViewShopItem)
        val textViewShopItemCount = view.findViewById<TextView>(R.id.textViewShopItemCount)
    }
}