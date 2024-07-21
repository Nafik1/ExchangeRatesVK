package com.example.exchangeratesvk.presentation.mainscreen

import android.util.Log
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

    private val _state = MutableStateFlow<ExhangeRateState<Currency>>(ExhangeRateState.Loading)
    val state: StateFlow<ExhangeRateState<Currency>> get() = _state.asStateFlow()

    init {
        Log.d("aufaufauf",_state.value.toString())
        viewModelScope.launch {
            loadCurrency().collect {
                _state.value = it
            }
        }
        Log.d("aufaufauf",_state.value.toString())
    }

    fun loadCurrency(): Flow<ExhangeRateState<Currency>> {
        return loadCurrencyUseCase.invoke()
    }

    fun onCurrencyChange(currencyName: String) {
        viewModelScope.launch {
            changeCurrencyUseCase.invoke(currencyName).collect {
                _state.value = ExhangeRateState.Success<Currency>(
                    data = it.data
                )
            }
        }
    }

    fun calculation(sum: String, rate: String): String {
        return (sum.toFloat() * rate.toFloat()).toString()
    }

}