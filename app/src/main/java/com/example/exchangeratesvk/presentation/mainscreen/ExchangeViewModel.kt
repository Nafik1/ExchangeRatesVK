package com.example.exchangeratesvk.presentation.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratesvk.data.Repository.ExchangeRateRepositoryImpl
import com.example.exchangeratesvk.data.model.CurrencyDto
import com.example.exchangeratesvk.domain.entity.Currency
import com.example.exchangeratesvk.domain.entity.ExhangeRateState
import com.example.exchangeratesvk.domain.usecases.ChangeCurrencyUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExchangeViewModel @Inject constructor(
    private val changeCurrencyUseCase: ChangeCurrencyUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<ExhangeRateState<Currency>>(ExhangeRateState.Loading)
    val state: StateFlow<ExhangeRateState<Currency>> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            loadCurrency("USD").collect {
                _state.value = it
            }
        }
    }

    fun loadCurrency(currencyName: String): Flow<ExhangeRateState<Currency>> {
        return changeCurrencyUseCase.invoke(currencyName)
    }

    fun onCurrencyChange(currencyName: String) {
        viewModelScope.launch {
            var newCurrency: ExhangeRateState.Success<Currency>? = null
            changeCurrencyUseCase.invoke(currencyName).collect {
                if (it is ExhangeRateState.Success) {
                    newCurrency = it
                }
            }
            val newCurrencyFinal = newCurrency
            if(newCurrencyFinal != null) {
                changeCurrencyUseCase.invoke(currencyName).collect {
                    if (_state.value is ExhangeRateState.Success) {
                        _state.value = (_state.value as ExhangeRateState.Success<Currency>).copy(
                            data = newCurrencyFinal.data
                        )
                    }
                }
            }
        }
    }


}