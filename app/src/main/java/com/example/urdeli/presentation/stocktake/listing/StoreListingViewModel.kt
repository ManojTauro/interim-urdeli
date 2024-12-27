package com.example.urdeli.presentation.stocktake.listing

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.urdeli.data.remote.StoreRepositoryImpl
import com.example.urdeli.domain.model.Store
import com.example.urdeli.domain.repository.StoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StoreListingViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    private val storeRepository: StoreRepository = StoreRepositoryImpl(context)
    val storeList = mutableListOf<Store>()

    private val _stores = MutableStateFlow<List<Store>>(emptyList())  // MutableStateFlow is used internally
    val stores: StateFlow<List<Store>> = _stores

    init {
        viewModelScope.launch {
            _stores.value = storeRepository.getAllStores()
        }
    }

    fun addStore(storeName: String, storeLocation: String, supervisor: String) {
        viewModelScope.launch {
            storeRepository.insertStore(
                Store(
                    stocktakeId = 0,
                    storeName = storeName,
                    storeLocation = storeLocation,
                    supervisor = supervisor
                )
            )
            _stores.value = storeRepository.getAllStores()
        }
    }
}