package com.example.exchangeratesvk.presentation.resultscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exchangeratesvk.R
import com.example.exchangeratesvk.presentation.mainscreen.ExchangeViewModel
import com.example.exchangeratesvk.ui.theme.Black500

@Composable
fun ResultScreen(
    viewModel: ExchangeViewModel,
    onBackPressed: () -> Unit
) {
    val state by viewModel.state.collectAsState()
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
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${state.enteredAmount} ${state.firstCurrency} =",
                color = Black500,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier
                    .width(240.dp)
                    .height(50.dp),
                enabled = false,
                readOnly = true,
                value = "",
                textStyle = TextStyle(fontSize = 12.sp),
                onValueChange = { },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "${state.convertResult} ${state.secondCurrency}",
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    onBackPressed()
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
                Text(text = stringResource(R.string.comeBack))
            }
        }
    }
}