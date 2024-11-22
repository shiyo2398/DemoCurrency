package com.shiyo.featurecurrency

import com.shiyo.featurecurrency.data.local.CurrencyInfoItem
import com.shiyo.featurecurrency.data.local.CurrencyViewState
import com.shiyo.featurecurrency.domain.CurrencyUseCaseImpl
import com.shiyo.featurecurrency.ui.CurrencyListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyListViewModelTest {

    private lateinit var viewModel: CurrencyListViewModel

    // Mocked use case, no database required
    private val useCase: CurrencyUseCaseImpl = mockk(relaxed = true)

    // Use a custom test dispatcher
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Set the Main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)

        // Initialize the ViewModel with the mocked use case
        viewModel = CurrencyListViewModel(useCase)
    }

    @After
    fun tearDown() {
        // Reset the Main dispatcher after the test
        Dispatchers.resetMain()
    }

    /**
     * Search Test case
     */
    @Test
    fun searchQueryMatchesStartWithCurrencyName() = runTest {
        // Mock the list of coins
        val coins = listOf(
            CurrencyInfoItem(name = "Bitcoin", symbol = "BTC", id = "1"),
            CurrencyInfoItem(name = "Foobar", symbol = "FOO", id = "2"),
            CurrencyInfoItem(name = "Barfoo", symbol = "BAR", id = "3")
        )
        coEvery { useCase.getAllList() } returns coins

        // Search for "foo"
        viewModel.searchCurrencies("Foo")
        advanceUntilIdle()

        println("result : ${viewModel.state.first()}")

        // Assert that only "Foobar" matches
        val expectedState = CurrencyViewState.Success(listOf(coins[1]))
        assertEquals(expectedState, viewModel.state.first())
    }

    @Test
    fun searchQueryMatchesWhenNameContainSpace() = runTest {
        // Mock the list of coins
        val coins = listOf(
            CurrencyInfoItem(name = "Ethereum", symbol = "ETH", id = "1"),
            CurrencyInfoItem(name = "Ethereum Classic", symbol = "ETC", id = "2"),
            CurrencyInfoItem(name = "Tronclassic", symbol = "TRX", id = "3")
        )
        coEvery { useCase.getAllList() } returns coins

        // Search for "Classic"
        viewModel.searchCurrencies("Classic")
        advanceUntilIdle()

        println("result : ${viewModel.state.first()}")

        // Assert that only "Ethereum Classic" matches
        val expectedState = CurrencyViewState.Success(listOf(coins[1]))
        assertEquals(expectedState, viewModel.state.first())
    }

    @Test
    fun searchQueryMatchesStartWithCurrencySymbol() = runTest {
        // Mock the list of coins
        val coins = listOf(
            CurrencyInfoItem(name = "Ethereum", symbol = "ETH", id = "1"),
            CurrencyInfoItem(name = "Ethereum Classic", symbol = "ETC", id = "2"),
            CurrencyInfoItem(name = "Electroneum", symbol = "ETN", id = "3"),
            CurrencyInfoItem(name = "Bitcoin", symbol = "BTC", id = "4"),
            CurrencyInfoItem(name = "Bettercoin", symbol = "BET", id = "5")
        )
        coEvery { useCase.getAllList() } returns coins

        // Search for "ET"
        viewModel.searchCurrencies("ET")
        advanceUntilIdle()

        println("result : ${viewModel.state.first()}")

        // Assert that only symbols starting with "ET" match
        val expectedState = CurrencyViewState.Success(listOf(coins[0], coins[1], coins[2]))
        assertEquals(expectedState, viewModel.state.first())
    }

    @Test
    fun searchCurrenciesResultNotFound() = runTest {
        // Mock the list of coins
        val coins = listOf(
            CurrencyInfoItem(name = "Bitcoin", symbol = "BTC", id = "1"),
            CurrencyInfoItem(name = "Ethereum", symbol = "ETH", id = "2"),
            CurrencyInfoItem(name = "Tronclassic", symbol = "TRX", id = "3")
        )
        coEvery { useCase.getAllList() } returns coins

        // Search for "No Result"
        viewModel.searchCurrencies("No Result")
        advanceUntilIdle()

        // Assert that no coins match the query
        val expectedState = CurrencyViewState.Empty
        println("result : ${viewModel.state.first()}")
        assertEquals(expectedState, viewModel.state.first())
    }

    @Test
    fun searchCurrenciesThrowError() = runTest {
        // Mock an exception for getAllList
        coEvery { useCase.getAllList() } throws Exception("Network error")

        // Call function
        viewModel.searchCurrencies("Error Query")

        // Advance the dispatcher to process coroutine tasks
        advanceUntilIdle()

        println("result : ${viewModel.state.first()}")

        // Assert state
        assertEquals(
            CurrencyViewState.Error("Network error"),
            viewModel.state.first()
        )
    }
}