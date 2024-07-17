package com.bloodspy.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.bloodspy.shoppinglist.R
import com.bloodspy.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {
//    private lateinit var textInputLayoutName: TextInputLayout
//    private lateinit var textInputLayoutCount: TextInputLayout
//
//    private lateinit var editTextName: EditText
//    private lateinit var editTextCount: EditText
//
//    private lateinit var buttonSaveShopItem: Button
//
//    private lateinit var viewModel: ShopItemViewModel
//
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
//        initViews()
//        initViewModel()
//        observeViewModel()
//        setupDoOnTextChanged()
//
        val fragment = when (screenMode) {
            EXTRA_MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            EXTRA_MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            else -> throw RuntimeException("Unknown screen mode: $screenMode")
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .commit()
    }

//    private fun launchAddMode() {
//        buttonSaveShopItem.setOnClickListener {
//            val name = getShopItemName()
//            val count = getShopItemCount()
//
//            viewModel.addShopItem(name, count)
//        }
//    }
//
//    private fun launchEditMode() {
//        viewModel.loadShopItem(shopItemId)
//
//        viewModel.shopItem.observe(this) {
//            editTextName.setText(it.name)
//            editTextCount.setText(it.count.toString())
//        }
//
//        buttonSaveShopItem.setOnClickListener {
//            val name = getShopItemName()
//            val count = getShopItemCount()
//
//            viewModel.editShopItem(name, count)
//        }
//    }
//
//    private fun observeViewModel() {
//        viewModel.errorInputName.observe(
//            this
//        ) {
//            val errorMessage = if (it) {
//                getString(R.string.wrong_name_error)
//            } else {
//                null
//            }
//
//            textInputLayoutName.error = errorMessage
//        }
//
//        viewModel.errorInputCount.observe(
//            this
//        ) {
//            val errorMessage = if (it) {
//                getString(R.string.wrong_count_error)
//            } else {
//                null
//            }
//
//            textInputLayoutCount.error = errorMessage
//        }
//
//        viewModel.shouldCloseScreen.observe(
//            this
//        ) {
//            finish()
//        }
//    }
//
//    private fun setupDoOnTextChanged() {
//        editTextName.doOnTextChanged { text, start, before, count ->
//            viewModel.resetErrorInputName()
//        }
//
//        editTextCount.doOnTextChanged { text, start, before, count ->
//            viewModel.resetErrorInputCount()
//        }
//    }
//
//    private fun getShopItemName(): String = editTextName.text.toString()
//
//    private fun getShopItemCount(): String = editTextCount.text.toString()
//
//
//    private fun initViewModel() {
//        viewModel = ViewModelProvider(this)[(ShopItemViewModel::class.java)]
//    }
//
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
//
//    private fun initViews() {
//        textInputLayoutName = findViewById(R.id.textInputLayoutName)
//        textInputLayoutCount = findViewById(R.id.textInputLayoutCount)
//        editTextName = findViewById(R.id.editTextName)
//        editTextCount = findViewById(R.id.editTextCount)
//        buttonSaveShopItem = findViewById(R.id.buttonSaveShopItem)
//    }
}