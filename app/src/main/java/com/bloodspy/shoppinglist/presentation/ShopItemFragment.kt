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

class ShopItemFragment : Fragment() {

    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutCount: TextInputLayout

    private lateinit var editTextName: EditText
    private lateinit var editTextCount: EditText

    private lateinit var buttonSaveShopItem: Button

    private lateinit var viewModel: ShopItemViewModel

    private var screenMode: String = UNKNOWN_SCREEN_MODE
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    companion object {
        private const val SCREEN_MODE = "screen_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val SHOP_ITEM_ID = "shop_item_id"

        private const val UNKNOWN_SCREEN_MODE = ""

        fun newInstanceAddItem(): ShopItemFragment = ShopItemFragment().apply {
            arguments = Bundle().apply {
                putString(SCREEN_MODE, MODE_ADD)
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment = ShopItemFragment().apply {
            arguments = Bundle().apply {
                putString(SCREEN_MODE, MODE_EDIT)
                putInt(SHOP_ITEM_ID, shopItemId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_shop_item, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        initViewModel()
        observeViewModel()
        setupDoOnTextChanged()

        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
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
        val args = requireArguments()

        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode not found")
        }

        val mode = args.getString(SCREEN_MODE).toString()
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode: $mode")
        }

        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id not found")
            }

            shopItemId = requireArguments().getInt(SHOP_ITEM_ID)
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