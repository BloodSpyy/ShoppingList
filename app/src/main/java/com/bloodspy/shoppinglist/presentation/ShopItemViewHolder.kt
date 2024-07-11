package com.bloodspy.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bloodspy.shoppinglist.R

class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textViewShopItem = view.findViewById<TextView>(R.id.textViewShopItem)
    val textViewShopItemCount = view.findViewById<TextView>(R.id.textViewShopItemCount)
}