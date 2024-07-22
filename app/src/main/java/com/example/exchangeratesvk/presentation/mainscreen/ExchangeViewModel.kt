package com.example.exchangeratesvk.presentation.mainscreen

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratesvk.data.Repository.ExchangeRateRepositoryImpl
import com.example.exchangeratesvk.data.model.CurrencyDto
import com.example.exchangeratesvk.domain.entity.Currency
import com.example.exchangeratesvk.domain.entity.ExhangeRateState
import com.example.exchangeratesvk.domain.usecases.ChangeCurrencyUseCase
import com.example.exchangeratesvk.domain.usecases.LoadCurrencyUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExchangeViewModel @Inject constructor(
    private val changeCurrencyUseCase: ChangeCurrencyUseCase,
    private val loadCurrencyUseCase: LoadCurrencyUseCase
) : ViewModel() {

    companion object {
        private const val LOG_TAG = "aufaufauf"
    }

    private val _state = MutableStateFlow(ExchangeScreenState())
    val state: StateFlow<ExchangeScreenState> get() = _state.asStateFlow()

    init {
        Log.d(LOG_TAG, "Initial state: ${_state.value}")
        viewModelScope.launch {
            loadCurrencyUseCase.invoke().collect {
                Log.d(LOG_TAG, "Got state from loadCurrencyUseCase: $it")
                _state.value = it.mapToScreenState(state.value)
            }
        }
    }

    fun onCountRateChange(countRate: String) {
        _state.value = _state.value.copy(enteredAmount = countRate)
    }

    fun onFirstCurrencySelect(rate: String) {
        _state.value = _state.value.copy(firstCurrency = rate)
        viewModelScope.launch {
            changeCurrencyUseCase.invoke(rate).collect {
                Log.d(LOG_TAG, "Got state from changeCurrencyUseCase: $it")
                _state.value = it.mapToScreenState(state.value)
            }
        }
    }

    fun onSecondCurrencySelect(rate: String) {
        _state.value = _state.value.copy(secondCurrency = rate)
    }

    private fun ExhangeRateState<Currency>.mapToScreenState(
        currentState: ExchangeScreenState
    ) = when (this) {
        is ExhangeRateState.Error -> {
            Log.e(LOG_TAG, "Got error: ", exception)
            currentState.copy(
                isLoading = false,
                error = exception,
            )
        }

        is ExhangeRateState.Success -> {
            data.let { currency ->
                currentState.copy(
                    isLoading = false,
                    error = null,
                    date = currency.date,
                    firstCurrency = currency.currencyName,
                    currencies = currency.allCurrenciesList,
                )
            }
        }

        ExhangeRateState.Loading -> {
            currentState.copy(
                isLoading = true,
                error = null,
            )
        }
    }

    fun onRetryClick() {
        viewModelScope.launch {
            changeCurrencyUseCase.invoke(_state.value.firstCurrency).collect {
                Log.d(LOG_TAG, "Got state from changeCurrencyUseCase: $it")
                _state.value = it.mapToScreenState(state.value)
            }
        }
    }
}

@Stable
data class ExchangeScreenState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val date: String? = null,
    val firstCurrency: String = "",
    val secondCurrency: String = "",
    val enteredAmount: String = "",
    private val currencies: Map<String, String> = mapOf(),
) {
    val availableCurrencies = listOf("USD", "RUB", "EUR", "CNY", "GBP")

    val convertResult: Float
        get() {
            val rate = currencies[secondCurrency]?.toFloat()
                ?: throw IllegalStateException("Second currency is not selected")
            val sum = enteredAmount.toFloat()
            return rate * sum
        }
}

