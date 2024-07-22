package com.example.exchangeratesvk.presentation.mainscreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exchangeratesvk.R
import com.example.exchangeratesvk.data.model.CurrencyDto
import com.example.exchangeratesvk.domain.entity.Currency
import com.example.exchangeratesvk.domain.entity.ExhangeRateState
import com.example.exchangeratesvk.ui.theme.Black500

@Composable
fun ExchangeScreen(
    exchangeViewModel: ExchangeViewModel,
    onConvertClick: () -> Unit
) {
    val state by exchangeViewModel.state.collectAsState()
    when {
        state.error != null -> ErrorState(state.error!!, onRetryClick = {
            exchangeViewModel.onRetryClick()
        })

        state.isLoading -> ProgressBar()
        else -> ExchangeCard(
            state = state,
            onConvertClick = onConvertClick,
            viewModel = exchangeViewModel
        )
    }
}

@Composable
fun ExchangeCard(
    state: ExchangeScreenState,
    onConvertClick: () -> Unit,
    viewModel: ExchangeViewModel
) {
    var expandedFirst by rememberSaveable { mutableStateOf(false) }
    var expandedSecond by rememberSaveable { mutableStateOf(false) }
    val ctx = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = stringResource(R.string.exchangeRateName),
                fontSize = 30.sp
            )
            state.date?.let {
                Text(
                    text = stringResource(R.string.courseUpdateDate),
                    color = Black500,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = it,
                    color = Black500,
                    fontSize = 12.sp
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .width(240.dp)
                    .height(50.dp),
                value = state.enteredAmount,
                textStyle = TextStyle(fontSize = 12.sp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { newText ->
                    (viewModel.onCountRateChange(newText.filter { it.isDigit() }))
                },
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.enterAmount),
                        color = Black500,
                        fontSize = 14.sp
                    )
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Button(
                        onClick = { expandedFirst = !expandedFirst },
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Black)

                    ) {
                        Text(text = state.firstCurrency)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.width(1.dp))
                    DropdownMenu(
                        modifier = Modifier
                            .width(100.dp)
                            .height(150.dp),
                        expanded = expandedFirst,
                        onDismissRequest = { expandedFirst = false }
                    ) {
                        for (rate in state.availableCurrencies) {
                            Text(
                                text = rate,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        viewModel.onFirstCurrencySelect(rate)
                                        expandedFirst = false
                                    }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = null
                        )
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Button(
                        onClick = { expandedSecond = !expandedSecond },
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Black)

                    ) {
                        Text(text = state.secondCurrency)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = null
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier
                            .width(100.dp)
                            .height(150.dp),
                        expanded = expandedSecond,
                        onDismissRequest = { expandedSecond = false }
                    ) {
                        for (rate in state.availableCurrencies) {
                            Text(
                                text = rate,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        viewModel.onSecondCurrencySelect(rate)
                                        expandedSecond = false
                                    }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    if (
                        state.firstCurrency.isNotEmpty()
                        && state.secondCurrency.isNotEmpty()
                        && state.enteredAmount.isNotEmpty()
                    ) {
                        if (state.firstCurrency == state.secondCurrency) {
                            ctx.showToast(ctx.getString(R.string.changeOtherCurrency))
                        } else {
                            onConvertClick()
                        }
                    } else {
                        ctx.showToast(ctx.getString(R.string.selectData))
                    }
                },
                modifier = Modifier
                    .width(240.dp)
                    .height(50.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(text = stringResource(R.string.calculateButton))
            }
        }
    }
}

@Composable
fun ErrorState(
    exception: Throwable,
    onRetryClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.exchangeRateName),
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.errorMessage),
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = exception.message.toString(),
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onRetryClick() }) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}

@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.Black)
    }
}

fun Context.showToast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}
