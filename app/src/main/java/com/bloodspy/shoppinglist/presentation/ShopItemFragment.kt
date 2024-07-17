package com.bloodspy.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bloodspy.shoppinglist.R
import com.bloodspy.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment(
    private val screenMode: String = UNKNOWN_SCREEN_MODE,
    private val shopItemId: Int = ShopItem.UNDEFINED_ID,
) : Fragment() {

    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutCount: TextInputLayout

    private lateinit var editTextName: EditText
    private lateinit var editTextCount: EditText

    private lateinit var buttonSaveShopItem: Button

    private lateinit var viewModel: ShopItemViewModel

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_MODE_ADD = "extra_add"
        private const val EXTRA_MODE_EDIT = "extra_edit"
        private const val EXTRA_SHOP_ITEM_ID = "extra_id"

        private const val UNKNOWN_SCREEN_MODE = ""

        fun newInstanceAddItem(): ShopItemFragment = ShopItemFragment(EXTRA_MODE_ADD)

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment = ShopItemFragment(
            EXTRA_MODE_EDIT,
            shopItemId
        )

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_shop_item, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parseParams()
        initViews(view)
        initViewModel()
        observeViewModel()
        setupDoOnTextChanged()

        when (screenMode) {
            EXTRA_MODE_ADD -> launchAddMode()
            EXTRA_MODE_EDIT -> launchEditMode()
        }
    }

    private fun launchAddMode() {
        buttonSaveShopItem.setOnClickListener {
            val name = getShopItemName()
            val count = getShopItemCount()

            viewModel.addShopItem(name, count)
        }
    }

    private fun launchEditMode() {
        viewModel.loadShopItem(shopItemId)

        viewModel.shopItem.observe(viewLifecycleOwner) {
            editTextName.setText(it.name)
            editTextCount.setText(it.count.toString())
        }

        buttonSaveShopItem.setOnClickListener {
            val name = getShopItemName()
            val count = getShopItemCount()

            viewModel.editShopItem(name, count)
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(
            viewLifecycleOwner
        ) {
            val errorMessage = if (it) {
                getString(R.string.wrong_name_error)
            } else {
                null
            }

            textInputLayoutName.error = errorMessage
        }

        viewModel.errorInputCount.observe(
            viewLifecycleOwner
        ) {
            val errorMessage = if (it) {
                getString(R.string.wrong_count_error)
            } else {
                null
            }

            textInputLayoutCount.error = errorMessage
        }

        viewModel.shouldCloseScreen.observe(
            viewLifecycleOwner
        ) {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun setupDoOnTextChanged() {
        editTextName.doOnTextChanged { text, start, before, count ->
            viewModel.resetErrorInputName()
        }

        editTextCount.doOnTextChanged { text, start, before, count ->
            viewModel.resetErrorInputCount()
        }
    }

    private fun getShopItemName(): String = editTextName.text.toString()

    private fun getShopItemCount(): String = editTextCount.text.toString()


    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[(ShopItemViewModel::class.java)]
    }

    private fun parseParams() {
        if (screenMode != EXTRA_MODE_ADD && screenMode != EXTRA_MODE_EDIT) {
            throw RuntimeException("Unknown screen mode: $screenMode")
        }

        if (screenMode == EXTRA_MODE_EDIT && shopItemId == ShopItem.UNDEFINED_ID) {
            throw RuntimeException("Param shop item id not found")
        }
    }

    private fun initViews(view: View) {
        textInputLayoutName = view.findViewById(R.id.textInputLayoutName)
        textInputLayoutCount = view.findViewById(R.id.textInputLayoutCount)
        editTextName = view.findViewById(R.id.editTextName)
        editTextCount = view.findViewById(R.id.editTextCount)
        buttonSaveShopItem = view.findViewById(R.id.buttonSaveShopItem)
    }
}