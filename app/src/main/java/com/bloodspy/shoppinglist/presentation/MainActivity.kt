package com.bloodspy.shoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bloodspy.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    private lateinit var recyclerViewShoppingList: RecyclerView
    private lateinit var shoppingListAdapter: ShoppingListAdapter

    private lateinit var buttonAddShopItem: FloatingActionButton

    companion object {
        private const val LOG_TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        setupViewModel()
        observeViewModel()
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        buttonAddShopItem.setOnClickListener {
            startActivity(ShopItemActivity.newIntentAddItem(this))
        }
    }

    private fun setupRecyclerView() {
        recyclerViewShoppingList = findViewById(R.id.recyclerViewShoppingList)
        shoppingListAdapter = ShoppingListAdapter()

        with(recyclerViewShoppingList) {
            adapter = shoppingListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShoppingListAdapter.SHOP_ITEM_ENABLED_VIEW_TYPE,
                ShoppingListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShoppingListAdapter.SHOP_ITEM_DISABLED_VIEW_TYPE,
                ShoppingListAdapter.MAX_POOL_SIZE
            )

            setupSwipeListener(this)
        }

        with(shoppingListAdapter) {
            onShopItemLongClickListener = {
                viewModel.changeEnableState(it)
            }
            onShopItemClickListener = {
                startActivity(ShopItemActivity.newIntentEditItem(this@MainActivity, it.id))
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun initViews() {
        buttonAddShopItem = findViewById(R.id.buttonAddShopItem)
    }

    private fun setupSwipeListener(recyclerViewShoppingList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopItem = shoppingListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(shopItem)
            }
        }

        ItemTouchHelper(callback).attachToRecyclerView(recyclerViewShoppingList)
    }

    private fun observeViewModel() {
        viewModel.shopList.observe(
            this
        ) {
            shoppingListAdapter.submitList(it)
        }
    }
}