package com.example.techscreentemplate

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel()
    }

    // --- initial state ---

    @Test
    fun `initial amount is empty`() {
        assertEquals("", viewModel.uiState.value.amount)
    }

    @Test
    fun `initial currency is USD`() {
        assertEquals(Currency.USD, viewModel.uiState.value.currency)
    }

    @Test
    fun `maxAmountFormatted defaults to USD max`() {
        assertEquals("\$999,999.99", viewModel.maxAmountFormatted)
    }

    // --- USD amount input ---

    @Test
    fun `onAmountChanged accepts valid digit string`() {
        viewModel.onAmountChanged("1234")
        assertEquals("1234", viewModel.uiState.value.amount)
    }

    @Test
    fun `onAmountChanged ignores input with letters`() {
        viewModel.onAmountChanged("12ab")
        assertEquals("", viewModel.uiState.value.amount)
    }

    @Test
    fun `onAmountChanged ignores fully non-digit input`() {
        viewModel.onAmountChanged("abc")
        assertEquals("", viewModel.uiState.value.amount)
    }

    @Test
    fun `onAmountChanged clamps value exceeding USD max`() {
        viewModel.onAmountChanged("100000000")
        assertEquals("99999999", viewModel.uiState.value.amount)
    }

    @Test
    fun `onAmountChanged accepts value equal to USD max`() {
        viewModel.onAmountChanged("99999999")
        assertEquals("99999999", viewModel.uiState.value.amount)
    }

    @Test
    fun `onAmountChanged accepts value just below USD max`() {
        viewModel.onAmountChanged("99999998")
        assertEquals("99999998", viewModel.uiState.value.amount)
    }

    // --- clear ---

    @Test
    fun `clearAmount resets amount to empty`() {
        viewModel.onAmountChanged("5000")
        viewModel.clearAmount()
        assertEquals("", viewModel.uiState.value.amount)
    }

    @Test
    fun `clearAmount on initial state stays empty`() {
        viewModel.clearAmount()
        assertEquals("", viewModel.uiState.value.amount)
    }

    // --- error state ---

    @Test
    fun `showMaxError is true when amount exceeds max`() {
        viewModel.onAmountChanged("100000000")
        assertEquals(true, viewModel.uiState.value.showMaxError)
    }

    @Test
    fun `showMaxError is false for valid amount`() {
        viewModel.onAmountChanged("500")
        assertEquals(false, viewModel.uiState.value.showMaxError)
    }

    @Test
    fun `showMaxError clears after clearAmount`() {
        viewModel.onAmountChanged("100000000")
        viewModel.clearAmount()
        assertEquals(false, viewModel.uiState.value.showMaxError)
    }

    // --- currency switching ---

    @Test
    fun `onCurrencyChanged updates currency`() {
        viewModel.onCurrencyChanged(Currency.JPY)
        assertEquals(Currency.JPY, viewModel.uiState.value.currency)
    }

    @Test
    fun `onCurrencyChanged resets amount`() {
        viewModel.onAmountChanged("5000")
        viewModel.onCurrencyChanged(Currency.JPY)
        assertEquals("", viewModel.uiState.value.amount)
    }

    @Test
    fun `onCurrencyChanged clears error`() {
        viewModel.onAmountChanged("100000000")
        viewModel.onCurrencyChanged(Currency.JPY)
        assertEquals(false, viewModel.uiState.value.showMaxError)
    }

    @Test
    fun `maxAmountFormatted reflects JPY after currency switch`() {
        viewModel.onCurrencyChanged(Currency.JPY)
        assertEquals("¥9,999,999", viewModel.maxAmountFormatted)
    }

    @Test
    fun `onAmountChanged clamps value exceeding JPY max`() {
        viewModel.onCurrencyChanged(Currency.JPY)
        viewModel.onAmountChanged("10000000")
        assertEquals("9999999", viewModel.uiState.value.amount)
    }

    @Test
    fun `onAmountChanged accepts value equal to JPY max`() {
        viewModel.onCurrencyChanged(Currency.JPY)
        viewModel.onAmountChanged("9999999")
        assertEquals("9999999", viewModel.uiState.value.amount)
    }
}
