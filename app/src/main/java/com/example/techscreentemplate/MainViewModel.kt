package com.example.techscreentemplate

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class MainUiState(
    val amount: String = "",
    val showMaxError: Boolean = false,
    val currency: Currency = Currency.USD,
)

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    val maxAmountFormatted: String
        get() = formatAmount(uiState.value.currency.maxUnits.toString(), uiState.value.currency)

    fun clearAmount() {
        _uiState.value = _uiState.value.copy(amount = "", showMaxError = false)
    }

    fun onAmountChanged(input: String) {
        if (!input.all { it.isDigit() }) return
        val units = input.toLongOrNull() ?: 0L
        val exceeded = units > _uiState.value.currency.maxUnits
        _uiState.value = _uiState.value.copy(
            amount = if (exceeded) _uiState.value.currency.maxUnits.toString() else input,
            showMaxError = exceeded,
        )
    }

    fun onCurrencyChanged(currency: Currency) {
        _uiState.value = MainUiState(currency = currency)
    }
}
