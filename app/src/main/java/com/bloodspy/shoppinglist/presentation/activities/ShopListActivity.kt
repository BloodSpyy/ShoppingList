package com.bloodspy.shoppinglist.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bloodspy.shoppinglist.AppApplication
import com.bloodspy.shoppinglist.R
import com.bloodspy.shoppinglist.databinding.ActivityShoppingListBinding
import com.bloodspy.shoppinglist.presentation.fragments.ShopItemFragment
import com.bloodspy.shoppinglist.presentation.recyclerViewUtils.adapters.ShopListAdapter
import com.bloodspy.shoppinglist.presentation.viewmodels.ShopListViewModel
import com.bloodspy.shoppinglist.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class ShopListActivity : AppCompatActivity(), ShopItemFragment.OnEndWorkListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ShopListViewModel::class.java]
    }

    private val binding by lazy {
        ActivityShoppingListBinding.inflate(layoutInflater)
    }

    private val orientation by lazy {
        parseOrientation()
    }

    private val shopListAdapter = ShopListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependency()

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeViewModel()
        setupRecyclerView()
        setupOnClickListeners()
    }

    override fun onEndWork() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun injectDependency() {
        (application as AppApplication).component.inject(this)
    }

    private fun setupOnClickListeners() {
        binding.buttonAddShopItem.setOnClickListener {
            if (orientation == PORTRAIT_ORIENTATION) {
                startActivity(ShopItemActivity.newIntentAddItem(this@ShopListActivity))
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun parseOrientation(): String {
        return if (binding.shopItemContainer == null) {
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
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.SHOP_ITEM_ENABLED_VIEW_TYPE,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.SHOP_ITEM_DISABLED_VIEW_TYPE,
                ShopListAdapter.MAX_POOL_SIZE
            )

            setupSwipeListener(this)
        }

        with(shopListAdapter) {
            onShopItemLongClickListener = {
                viewModel.changeEnableState(it)
            }
            onShopItemClickListener = if (orientation == PORTRAIT_ORIENTATION) {
                {
                    startActivity(ShopItemActivity.newIntentEditItem(this@ShopListActivity, it.id))
                }
            } else {
                {
                    launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
                }
            }

        }
    }

    private fun setupSwipeListener(recyclerViewShoppingList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopItem = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(shopItem)
            }
        }

        ItemTouchHelper(callback).attachToRecyclerView(recyclerViewShoppingList)
    }

    private fun observeViewModel() {
        viewModel.shopList.observe(
            this
        ) {
            shopListAdapter.submitList(it)
        }
    }

    companion object {
        private const val PORTRAIT_ORIENTATION = "portrait"
        private const val LANDSCAPE_ORIENTATION = "landscape"
    }
}