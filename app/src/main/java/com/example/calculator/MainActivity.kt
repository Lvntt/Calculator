package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = CalculatorHorizontalPadding),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        AppTitleText()
        ExpressionText(viewModel.state)
        EraseIcon(viewModel::handleEraseIconClick)
        CalculatorDivider()
        AllCalculatorButtons(
            buttonsGrid = viewModel.getButtons(),
            viewModel::handleButtonClick
        )
    }
}

@Composable
fun AppTitleText(modifier: Modifier = Modifier) {
    Row {
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            style = TitleTextStyle,
            text = stringResource(id = R.string.appName)
        )
    }
}

@Composable
fun ExpressionText(
    calculatorUiState: CalculatorUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(
            top = ExpressionTextTopPadding,
            bottom = ExpressionTextBottomPadding
        )
    ) {
        when (calculatorUiState) {
            is CalculatorUiState.Initial -> {
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    style = ExpressionTextStyle,
                    text = calculatorUiState.initialExpression
                )
            }
            is CalculatorUiState.Input -> {
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    style = ExpressionTextStyle,
                    text = calculatorUiState.expression
                )
            }
            is CalculatorUiState.Error -> {
                Text(
                    color = MaterialTheme.colorScheme.error,
                    style = ExpressionTextStyle,
                    text = calculatorUiState.errorMessage
                )
            }
        }
    }
}

@Composable
fun EraseIcon(
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(end = EraseIconEndPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = onIconClick) {
            Icon(
                painter = painterResource(id = R.drawable.erase),
                modifier = modifier
                    .width(EraseIconWidth)
                    .height(EraseIconHeight),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun CalculatorDivider(modifier: Modifier = Modifier) {
    Divider(
        color = MaterialTheme.colorScheme.outline
    )
}

@Composable
fun AllCalculatorButtons(
    buttonsGrid: List<List<CalculatorButton>>,
    onClick: (CalculatorButton) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        for (row in buttonsGrid) {
            CalculatorButtonRow(
                buttonsRow = row,
                onClick = onClick
            )
            Spacer(modifier = modifier.height(SpacerHeight))
        }
    }
}

@Composable
fun CalculatorButtonRow(
    buttonsRow: List<CalculatorButton>,
    onClick: (CalculatorButton) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        if (buttonsRow.size == 3) {
            CalculatorButton(
                button = buttonsRow[0],
                onClick = onClick,
                modifier = modifier
                    .weight(LargeCalculatorButtonWeight)
                    .aspectRatio(LargeCalculatorButtonWeight)
            )
        } else {
            CalculatorButton(
                button = buttonsRow[0],
                onClick = onClick,
                modifier = modifier
                    .weight(CalculatorButtonWeight)
                    .aspectRatio(CalculatorButtonWeight)
            )

            Spacer(modifier = modifier.weight(SpacerWeight))

            CalculatorButton(
                button = buttonsRow[1],
                onClick = onClick,
                modifier = modifier
                    .weight(CalculatorButtonWeight)
                    .aspectRatio(CalculatorButtonWeight)
            )
        }

        Spacer(modifier = modifier.weight(SpacerWeight))

        CalculatorButton(
            button = buttonsRow[buttonsRow.lastIndex - 1],
            onClick = onClick,
            modifier = modifier
                .weight(CalculatorButtonWeight)
                .aspectRatio(CalculatorButtonWeight)
        )

        Spacer(modifier = modifier.weight(SpacerWeight))

        AccentedCalculatorButton(
            button = buttonsRow[buttonsRow.lastIndex],
            onClick = onClick,
            modifier = modifier
                .weight(CalculatorButtonWeight)
                .aspectRatio(CalculatorButtonWeight)
        )
    }
}

@Composable
fun CalculatorButton(
    button: CalculatorButton,
    onClick: (CalculatorButton) -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.fillMaxSize(),
        onClick = { onClick(button) },
        shape = CalculatorButtonShape,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        contentPadding = PaddingValues(ButtonContentPadding)
    ) {
        Text(
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = ButtonTextStyle,
            text = button.value
        )
    }
}

@Composable
fun AccentedCalculatorButton(
    button: CalculatorButton,
    onClick: (CalculatorButton) -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.fillMaxSize(),
        onClick = { onClick(button) },
        shape = CalculatorButtonShape,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
        contentPadding = PaddingValues(ButtonContentPadding)
    ) {
        Text(
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = ButtonTextStyle,
            text = button.value
        )
    }
}