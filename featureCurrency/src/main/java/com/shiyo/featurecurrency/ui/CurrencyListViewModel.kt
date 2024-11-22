package com.shiyo.featurecurrency.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiyo.coreresources.model.CurrencyType
import com.shiyo.featurecurrency.data.local.CurrencyEvent
import com.shiyo.featurecurrency.data.local.CurrencyInfoItem
import com.shiyo.featurecurrency.data.local.CurrencyViewState
import com.shiyo.featurecurrency.domain.CurrencyUseCaseImpl
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class CurrencyListViewModel(private val currencyUseCase: CurrencyUseCaseImpl) : ViewModel() {

    private val searchQueryFlow = MutableStateFlow("")
    private val currencyTypeFlow = MutableStateFlow(CurrencyType.ALL)

    private val _state = MutableStateFlow<CurrencyViewState>(CurrencyViewState.Empty)
    val state: StateFlow<CurrencyViewState> = combine(
        _state, // Current state
        searchQueryFlow.debounce(300).distinctUntilChanged(), // Search query
        currencyTypeFlow // Currency type
    ) { currentState, query, type ->
        when (currentState) {
            is CurrencyViewState.Success -> {
                // Filter currencies based on query and type
                val filteredCurrencies = filterCurrencies(query, type, currentState.currencies)
                if (filteredCurrencies.isEmpty()) {
                    CurrencyViewState.Empty // Return empty state if no results
                } else {
                    CurrencyViewState.Success(filteredCurrencies) // Return filtered results
                }
            }

            else -> currentState // Return the current state if not Success
        }
    }.stateIn(
        viewModelScope, // Scope for the flow
        SharingStarted.WhileSubscribed(5000), // Share while there are active subscribers
        CurrencyViewState.Empty // Initial state
    )

    private val _isDatabaseEmptyState = MutableStateFlow(true)
    val isDatabaseEmptyState: StateFlow<Boolean> = _isDatabaseEmptyState.combine(
        _state
    ) { isDataEmpty, currentState ->
        when (currentState) {
            is CurrencyViewState.Success -> {
                currentState.currencies.isEmpty()
            }
            else -> {
                isDataEmpty
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, true)


    init {
        viewModelScope.launch {
            checkDatabaseState()
        }
    }

    private fun checkDatabaseState() {
        handleIntent(CurrencyEvent.LoadAllCurrencyList)
    }

    fun searchCurrencies(query: String) {
        searchQueryFlow.value = query
    }

    fun changeCurrencyType(type: CurrencyType) {
        currencyTypeFlow.value = type
    }

    fun handleIntent(intent: CurrencyEvent) {
        viewModelScope.launch {
            when (intent) {
                is CurrencyEvent.LoadAllCurrencyList -> {
                    _state.value = CurrencyViewState.Loading
                    loadCurrencies { currencyUseCase.getAllList() }
                }

                is CurrencyEvent.InsertData -> {
                    currencyUseCase.insertData()
                    checkDatabaseState()
                }

                is CurrencyEvent.ClearData -> {
                    currencyUseCase.clearData()
                    checkDatabaseState()
                }
            }
        }
    }

    private suspend fun loadCurrencies(fetchCurrencies: suspend () -> List<CurrencyInfoItem>) {
        _state.value = try {
            val currencies = fetchCurrencies()
            checkListIfEmpty(currencies)
        } catch (e: Exception) {
            CurrencyViewState.Error(e.message ?: "Unknown error")
        }
    }

    private fun checkListIfEmpty(list: List<CurrencyInfoItem>): CurrencyViewState {
        return if (list.isEmpty()) {
            CurrencyViewState.Empty
        } else {
            CurrencyViewState.Success(list)
        }
    }

    private fun filterCurrencies(
        query: String,
        type: CurrencyType,
        allCurrencies: List<CurrencyInfoItem>
    ): List<CurrencyInfoItem> {
        // Filter by currency type
        val filteredByType = when (type) {
            CurrencyType.CRYPTO -> allCurrencies.filter { it.code.isEmpty() }
            CurrencyType.FIAT -> allCurrencies.filter { it.code.isNotEmpty() }
            CurrencyType.ALL -> allCurrencies
        }

        // Filter by search query
        return filteredByType.filter { currency ->
            currency.name.startsWith(query, ignoreCase = true) ||
                    currency.name.contains(" $query", ignoreCase = true) ||
                    currency.symbol.startsWith(query, ignoreCase = true)
        }
    }
}