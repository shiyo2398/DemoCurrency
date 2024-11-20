package com.shiyo.featurecurrency.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiyo.featurecurrency.data.local.CurrencyEvent
import com.shiyo.featurecurrency.data.local.CurrencyInfoItem
import com.shiyo.featurecurrency.data.local.CurrencyViewState
import com.shiyo.featurecurrency.domain.CurrencyUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CurrencyListViewModel(private val repository: CurrencyUseCaseImpl) : ViewModel() {
    private val _state = MutableStateFlow<CurrencyViewState>(CurrencyViewState.Empty)
    val state: StateFlow<CurrencyViewState> = _state

    fun handleIntent(intent: CurrencyEvent) {
        viewModelScope.launch {
            _state.value = CurrencyViewState.Loading

            when (intent) {
                is CurrencyEvent.LoadCryptoCurrencyList -> {
                    _state.value = try {
                        checkListIfEmpty(repository.getCryptoCurrencyList())
                    } catch (e: Exception) {
                        CurrencyViewState.Error(e.message ?: "Error loading crypto")
                    }
                }

                is CurrencyEvent.LoadFiatCurrencyList -> {
                    _state.value = try {
                        checkListIfEmpty(repository.getFiatCurrencyList())
                    } catch (e: Exception) {
                        CurrencyViewState.Error(e.message ?: "Error loading fiat")
                    }
                }

                is CurrencyEvent.LoadAllCurrencyList -> {
                    _state.value = try {
                        checkListIfEmpty(repository.getAllList())
                    } catch (e: Exception) {
                        CurrencyViewState.Error(e.message ?: "Error loading purchasable")
                    }
                }

                is CurrencyEvent.InsertData -> {
                    repository.insertData()
                    _state.value = CurrencyViewState.Success(emptyList())
                }

                is CurrencyEvent.ClearData -> {
                    repository.clearData()
                    _state.value = CurrencyViewState.Success(emptyList())
                }
            }
        }
    }

    fun searchCurrencies(query: String) {
        viewModelScope.launch {
            _state.value = CurrencyViewState.Loading
            try {
                val allCurrencies = repository.getAllList()

                val filteredCurrencies = allCurrencies.filter { currency ->
                    currency.name.startsWith(query, ignoreCase = true) ||
                            currency.name.contains(" $query", ignoreCase = true) ||
                            currency.symbol.startsWith(query, ignoreCase = true)
                }
                _state.value = if (filteredCurrencies.isEmpty()) {
                    CurrencyViewState.Empty
                } else {
                    CurrencyViewState.Success(filteredCurrencies)
                }
            } catch (e: Exception) {
                _state.value = CurrencyViewState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun checkListIfEmpty(list: List<CurrencyInfoItem>): CurrencyViewState {
        return if (list.isEmpty()) {
            CurrencyViewState.Empty
        } else {
            CurrencyViewState.Success(list)
        }
    }
}