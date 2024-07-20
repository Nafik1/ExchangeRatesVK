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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exchangeratesvk.data.model.CurrencyDto
import com.example.exchangeratesvk.domain.entity.Currency
import com.example.exchangeratesvk.domain.entity.ExhangeRateState
import com.example.exchangeratesvk.ui.theme.Black500

@Composable
fun ExchangeScreen(
    exchangeViewModel: ExchangeViewModel,
    onClickListenner: (String, String, String, String) -> Unit
) {

    val viewModel = exchangeViewModel.state.collectAsState(initial = ExhangeRateState.Loading)
    val currentState = viewModel.value

    when (currentState) {
        is ExhangeRateState.Error -> Error(currentState.exception)
        is ExhangeRateState.Loading -> ProgressBar()
        is ExhangeRateState.Success -> ExchangeCard(
            currency = currentState.data,
            onClickListenner = onClickListenner,
            viewModel = exchangeViewModel
        )
    }
}

@Composable
fun ExchangeCard(
    currency: Currency,
    onClickListenner: (String, String, String, String) -> Unit,
    viewModel: ExchangeViewModel
) {
    var expandedWith by remember { mutableStateOf(false) }
    var expandedIn by remember { mutableStateOf(false) }
    val nameRateWith = remember { mutableStateOf("") }
    val countRate = remember { mutableStateOf("") }
    val nameRateIn = remember { mutableStateOf("") }
    val quantityRate = remember { mutableStateOf("") }
    val ctx = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 200.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = "Конвертер валют",
                fontSize = 30.sp
            )
            Text(
                text = "Дата обновления курса:",
                color = Black500,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = currency.date,
                color = Black500,
                fontSize = 12.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .width(240.dp)
                    .height(50.dp),
                value = countRate.value,
                textStyle = TextStyle(fontSize = 12.sp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { newText -> countRate.value = newText },
                singleLine = true,
                placeholder = { Text(text = "Введите сумму", color = Black500, fontSize = 14.sp) }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Button(
                        onClick = { expandedWith = !expandedWith },
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Black)

                    ) {
                        Text(text = nameRateWith.value)
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
                        expanded = expandedWith,
                        onDismissRequest = { expandedWith = false }
                    ) {
                        for (rate in currency.allCurrenciesList.keys) {
                            Text(
                                text = rate,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        nameRateWith.value = rate
                                        viewModel.onCurrencyChange(nameRateWith.value)
                                        expandedWith = false
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
                        onClick = { expandedIn = !expandedIn },
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Black)

                    ) {
                        Text(text = nameRateIn.value)
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
                        expanded = expandedIn,
                        onDismissRequest = { expandedIn = false }
                    ) {
                        for (rate in currency.allCurrenciesList.keys) {
                            Text(
                                text = rate,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        nameRateIn.value = rate
                                        quantityRate.value =
                                            currency.allCurrenciesList[rate].toString()
                                        expandedWith = false
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
                        nameRateIn.value.isNotEmpty()
                        && nameRateWith.value.isNotEmpty()
                        && countRate.value.isNotEmpty()
                    ) {
                        onClickListenner(
                            countRate.value,
                            nameRateWith.value,
                            nameRateIn.value,
                            quantityRate.value
                        )
                    } else {
                        ctx.showToast("Выберите данные")
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
                Text(text = "Вычислить")
            }
        }
    }
}

@Composable
fun Error(exception: Throwable) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 200.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = "Конвертер валют",
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Произошла ошибка:",
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = exception.message.toString(),
                fontSize = 24.sp
            )
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
