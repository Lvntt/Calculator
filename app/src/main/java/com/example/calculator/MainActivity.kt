package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.calculator.data.ButtonsSource
import com.example.calculator.data.CalculatorButton
import com.example.calculator.presentation.CalculatorUiState
import com.example.calculator.presentation.CalculatorViewModel
import com.example.calculator.ui.theme.*

class MainActivity : ComponentActivity() {
    private val calculatorViewModel by viewModels<CalculatorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                Calculator(calculatorViewModel)
            }
        }
    }
}

@Composable
fun Calculator(
    viewModel: CalculatorViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Background)
            .padding(horizontal = CalculatorHorizontalPadding),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        AppTitleText()
        ExpressionText(viewModel = viewModel)
        EraseIcon(viewModel = viewModel)
        CalculatorDivider()
        AllCalculatorButtons(
            viewModel = viewModel,
            buttonsGrid = ButtonsSource.buttons
        )
    }
}

@Composable
fun AppTitleText(modifier: Modifier = Modifier) {
    Row {
        Text(
            style = TitleTextStyle,
            text = stringResource(id = R.string.appName)
        )
    }
}

@Composable
fun ExpressionText(
    viewModel: CalculatorViewModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(
            top = ExpressionTextTopPadding,
            bottom = ExpressionTextBottomPadding
        )
    ) {
        when (viewModel.state) {
            CalculatorUiState.Initial -> Unit
            CalculatorUiState.Input -> {
                Text(
                    style = ExpressionDefaultTextStyle,
                    text = viewModel.currentExpression
                )
            }
            CalculatorUiState.Error -> {
                Text(
                    style = ExpressionErrorTextStyle,
                    text = viewModel.currentExpression
                )
            }
        }
    }
}

@Composable
fun EraseIcon(
    viewModel: CalculatorViewModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(end = EraseIconEndPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = { viewModel.handleEraseIconClick() }) {
            Icon(
                painter = painterResource(id = R.drawable.erase),
                modifier = modifier
                    .width(EraseIconWidth)
                    .height(EraseIconHeight),
                contentDescription = null,
                tint = OnSurface
            )
        }
    }
}

@Composable
fun CalculatorDivider(modifier: Modifier = Modifier) {
    Divider(
        color = OutlineVariant
    )
}

@Composable
fun AllCalculatorButtons(
    viewModel: CalculatorViewModel,
    buttonsGrid: List<List<CalculatorButton>>,
    modifier: Modifier = Modifier
) {
    Column {
        for (row in buttonsGrid) {
            CalculatorButtonRow(
                viewModel = viewModel,
                buttonsRow = row
            )
            Spacer(modifier = modifier.height(SpacerHeight))
        }
    }
}

@Composable
fun CalculatorButtonRow(
    viewModel: CalculatorViewModel,
    buttonsRow: List<CalculatorButton>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        if (buttonsRow.size == 3) {
            CalculatorButton(
                viewModel = viewModel,
                button = buttonsRow[0],
                modifier = modifier
                    .weight(LargeCalculatorButtonWeight)
                    .aspectRatio(LargeCalculatorButtonWeight)
            )

            Spacer(modifier = modifier.weight(SpacerWeight))

            CalculatorButton(
                viewModel = viewModel,
                button = buttonsRow[1],
                modifier = modifier
                    .weight(CalculatorButtonWeight)
                    .aspectRatio(CalculatorButtonWeight)
            )

            Spacer(modifier = modifier.weight(SpacerWeight))

            AccentedCalculatorButton(
                viewModel = viewModel,
                button = buttonsRow[2],
                modifier = modifier
                    .weight(CalculatorButtonWeight)
                    .aspectRatio(CalculatorButtonWeight)
            )
        } else {
            CalculatorButton(
                viewModel = viewModel,
                button = buttonsRow[0],
                modifier = modifier
                    .weight(CalculatorButtonWeight)
                    .aspectRatio(CalculatorButtonWeight)
            )

            Spacer(modifier = modifier.weight(SpacerWeight))

            CalculatorButton(
                viewModel = viewModel,
                button = buttonsRow[1],
                modifier = modifier
                    .weight(CalculatorButtonWeight)
                    .aspectRatio(CalculatorButtonWeight)
            )

            Spacer(modifier = modifier.weight(SpacerWeight))

            CalculatorButton(
                viewModel = viewModel,
                button = buttonsRow[2],
                modifier = modifier
                    .weight(CalculatorButtonWeight)
                    .aspectRatio(CalculatorButtonWeight)
            )

            Spacer(modifier = modifier.weight(SpacerWeight))

            AccentedCalculatorButton(
                viewModel = viewModel,
                button = buttonsRow[3],
                modifier = modifier
                    .weight(CalculatorButtonWeight)
                    .aspectRatio(CalculatorButtonWeight)
            )
        }
    }
}

@Composable
fun CalculatorButton(
    viewModel: CalculatorViewModel,
    button: CalculatorButton,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.fillMaxSize(),
        onClick = { viewModel.handleButtonClick(button) },
        shape = CalculatorButtonShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = SecondaryContainer)
    ) {
        Text(
            style = CalculatorButtonTextStyle,
            text = button.value
        )
    }
}

@Composable
fun AccentedCalculatorButton(
    viewModel: CalculatorViewModel,
    button: CalculatorButton,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.fillMaxSize(),
        onClick = { viewModel.handleButtonClick(button) },
        shape = CalculatorButtonShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = TertiaryContainer)
    ) {
        Text(
            style = AccentedButtonTextStyle,
            text = button.value
        )
    }
}