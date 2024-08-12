package com.bloodspy.shoppinglist.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bloodspy.shoppinglist.data.ShopListRepositoryImpl
import com.bloodspy.shoppinglist.domain.entities.ShopItem
import com.bloodspy.shoppinglist.domain.usecases.AddShopItemUseCase
import com.bloodspy.shoppinglist.domain.usecases.EditShopItemUseCase
import com.bloodspy.shoppinglist.domain.usecases.GetShopItemUseCase
import kotlinx.coroutines.launch

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val SHOP_ITEM_IS_ENABLED = true
    }

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private val shoppingListRepositoryImpl = ShopListRepositoryImpl(application)

    private val editShopItemUseCase = EditShopItemUseCase(shoppingListRepositoryImpl)
    private val addShopItemUseCase = AddShopItemUseCase(shoppingListRepositoryImpl)
    private val getShopItemUseCase = GetShopItemUseCase(shoppingListRepositoryImpl)

    fun editShopItem(inputName: String?, inputCount: String?) {
        val newName = parseName(inputName)
        val newCount = parseCount(inputCount)

        if (validateInputData(newName, newCount)) {
            val oldShopItem = _shopItem.value

            oldShopItem?.let {
                val shopItem = it.copy(name = newName, count = newCount)
                viewModelScope.launch {
                    editShopItemUseCase.editShopItem(
                        shopItem
                    )
                    closeScreen()
                }
            }
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (validateInputData(name, count)) {
            val shopItem = ShopItem(name, count, SHOP_ITEM_IS_ENABLED)
            viewModelScope.launch {
                addShopItemUseCase.addShopItem(shopItem)
                closeScreen()
            }
        }
    }

    fun loadShopItem(shopItemId: Int) {
        viewModelScope.launch {
            _shopItem.value = getShopItemUseCase.getShopItem(shopItemId)
        }
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun closeScreen() {
        _shouldCloseScreen.value = Unit
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return inputCount?.trim()?.toIntOrNull() ?: 0
    }

    private fun validateInputData(name: String, count: Int): Boolean {
        var validateResult = true

        if (name.isBlank()) {
            _errorInputName.value = true
            validateResult = false
        }

        if (count <= 0) {
            _errorInputCount.value = true
            validateResult = false
        }

        return validateResult
    }
}