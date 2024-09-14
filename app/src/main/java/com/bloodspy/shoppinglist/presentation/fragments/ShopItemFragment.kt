package com.bloodspy.shoppinglist.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bloodspy.shoppinglist.AppApplication
import com.bloodspy.shoppinglist.R
import com.bloodspy.shoppinglist.databinding.FragmentShopItemBinding
import com.bloodspy.shoppinglist.domain.entities.ShopItem
import com.bloodspy.shoppinglist.presentation.viewmodels.ShopItemViewModel
import com.bloodspy.shoppinglist.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class ShopItemFragment() : Fragment() {
    private lateinit var onEndWorkListener: OnEndWorkListener

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ShopItemViewModel::class]
    }

    private var _binding: FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
        get() = _binding ?: throw RuntimeException("FragmentShopItemBinding is null")

    private var screenMode: String = UNKNOWN_SCREEN_MODE
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        injectDependency()

        super.onAttach(context)

        if (context is OnEndWorkListener) {
            onEndWorkListener = context
        } else {
            throw RuntimeException("Activity must implement OnEndWorkListener ")
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
    ): View {
        _binding = FragmentShopItemBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupDoOnTextChanged()

        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    interface OnEndWorkListener {
        fun onEndWork()
    }

    private fun injectDependency() {
        (requireActivity().application as AppApplication).component.inject(this)
    }

    private fun launchAddMode() {
        binding.buttonSaveShopItem.setOnClickListener {
            val name = getShopItemName()
            val count = getShopItemCount()

            viewModel.addShopItem(name, count)
        }
    }

    private fun launchEditMode() {
        viewModel.loadShopItem(shopItemId)

        with(binding) {
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

            binding.textInputLayoutName.error = errorMessage
        }

        viewModel.errorInputCount.observe(
            viewLifecycleOwner
        ) {
            val errorMessage = if (it) {
                getString(R.string.wrong_count_error)
            } else {
                null
            }

            binding.textInputLayoutCount.error = errorMessage
        }

        viewModel.shouldCloseScreen.observe(
            viewLifecycleOwner
        ) {
            onEndWorkListener.onEndWork()
        }
    }

    private fun setupDoOnTextChanged() {
        with(binding) {
            editTextName.doOnTextChanged { text, start, before, count ->
                viewModel.resetErrorInputName()
            }

            editTextCount.doOnTextChanged { text, start, before, count ->
                viewModel.resetErrorInputCount()
            }
        }
    }

    private fun getShopItemName(): String = binding.editTextName.text.toString()

    private fun getShopItemCount(): String = binding.editTextCount.text.toString()

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
}