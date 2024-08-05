package com.bloodspy.shoppinglist.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bloodspy.shoppinglist.R
import com.bloodspy.shoppinglist.databinding.ActivityMainBinding
import com.bloodspy.shoppinglist.presentation.viewmodels.MainViewModel
import com.bloodspy.shoppinglist.presentation.fragments.ShopItemFragment
import com.bloodspy.shoppinglist.presentation.recyclerViewUtils.adapters.ShoppingListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEndWorkListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private lateinit var shoppingListAdapter: ShoppingListAdapter

    private lateinit var orientation: String

    companion object {
        private const val PORTRAIT_ORIENTATION = "portrait"
        private const val LANDSCAPE_ORIENTATION = "landscape"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        observeViewModel()
        parseOrientation()
        setupRecyclerView()
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        binding.buttonAddShopItem.setOnClickListener {
            if(orientation == PORTRAIT_ORIENTATION) {
                startActivity(ShopItemActivity.newIntentAddItem(this@MainActivity))
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun parseOrientation() {
        orientation = if(binding.shopItemContainer == null) {
            PORTRAIT_ORIENTATION
        } else {
            LANDSCAPE_ORIENTATION
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shopItemContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        with(binding.recyclerViewShoppingList) {
            shoppingListAdapter = ShoppingListAdapter()
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
            onShopItemClickListener = if(orientation == PORTRAIT_ORIENTATION) {
                {
                    startActivity(ShopItemActivity.newIntentEditItem(this@MainActivity, it.id))
                }
            } else {
                {
                        launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
                }
            }

        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
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

    override fun onEndWork() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}