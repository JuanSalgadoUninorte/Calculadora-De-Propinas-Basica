package com.moviles.calculadoradepropinas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent()
        }
    }
}

@Composable
fun MainActivityContent() {
    var costOfService by remember { mutableStateOf("") }
    var selectedTipOption by remember { mutableStateOf(0) }
    var roundUpTip by remember { mutableStateOf(true) }
    var tipAmount by remember { mutableStateOf(0.0) }

    fun calculateTip() {
        val costOfServiceDouble = costOfService.toDoubleOrNull() ?: 0.0
        val tipPercent = when (selectedTipOption) {
            0 -> 0.20
            1 -> 0.18
            2 -> 0.15
            else -> 0.0
        }
        var tip = costOfServiceDouble * tipPercent
        if (roundUpTip) {
            tip = kotlin.math.ceil(tip)
        }
        tipAmount = tip
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = costOfService,
            onValueChange = { newValue ->
                val newText = newValue.filter { it.isDigit() }
                costOfService = newText
                calculateTip()
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onDone = { calculateTip() }),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .background(Color.White)
                .border(width = 1.dp, color = Color.LightGray)
                .padding(8.dp)
        )

        Text(
            text = stringResource(id = R.string.service_question),
            fontSize = 18.sp,
            color = Color.Blue,
            modifier = Modifier.padding(top = 16.dp)
        )

        val tipOptions = listOf(
            stringResource(id = R.string.excellent_service),
            stringResource(id = R.string.good_service),
            stringResource(id = R.string.normal_service),
            stringResource(id = R.string.bad_service)
        )
        tipOptions.forEachIndexed { index, option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    onClick = {
                        selectedTipOption = index
                        calculateTip()
                    },
                    selected = selectedTipOption == index,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = option,
                    color = Color.Blue,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            selectedTipOption = index
                            calculateTip()
                        }
                )
            }
        }


        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
            Text(text = stringResource(id = R.string.round_tip), color = Color.Blue)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = roundUpTip,
                onCheckedChange = {
                    roundUpTip = it
                    calculateTip()
                }
            )
        }

        Text(
            text = "${stringResource(id = R.string.tip_amount)} \$${tipAmount}",
            fontSize = 18.sp,
            color = Color.Blue,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainActivity() {
    MainActivityContent()
}