package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.calculator.data.ButtonsSource
import com.example.calculator.data.CalculatorButton
import com.example.calculator.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                Calculator()
            }
        }
    }
}

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Background)
            .padding(horizontal = CalculatorHorizontalPadding),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        AppTitleText()
        ExpressionText()
        EraseIcon()
        CalculatorDivider()
        AllCalculatorButtons(buttonsGrid = ButtonsSource.buttons)
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
fun ExpressionText(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(
            top = ExpressionTextTopPadding,
            bottom = ExpressionTextBottomPadding
        )
    ) {
        Text(
            style = ExpressionTextStyle,
            text = "Google" // TODO replace
        )
    }
}

@Composable
fun EraseIcon(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(end = EraseIconEndPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = { /*TODO*/ }) {
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
    modifier: Modifier = Modifier,
    buttonsGrid: List<List<CalculatorButton>>
) {
    Column {
        for (row in buttonsGrid) {
            CalculatorButtonRow(buttonsRow = row)
            Spacer(modifier = modifier.height(SpacerHeight))
        }
    }
}

@Composable
fun CalculatorButtonRow(
    modifier: Modifier = Modifier,
    buttonsRow: List<CalculatorButton>
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        if (buttonsRow.size == 3) {
            CalculatorButton(
                modifier = modifier
                    .weight(LargeCalculatorButtonWeight)
                    .aspectRatio(LargeCalculatorButtonWeight),
                text = buttonsRow[0].value
            )

            Spacer(modifier = modifier.weight(SpacerWeight))

            CalculatorButton(
                modifier = modifier
                    .weight(CalculatorButtonWeight)
                    .aspectRatio(CalculatorButtonWeight),
                text = buttonsRow[1].value
            )

            Spacer(modifier = modifier.weight(SpacerWeight))

            AccentedCalculatorButton(
                modifier = modifier
                    .weight(CalculatorButtonWeight)
                    .aspectRatio(CalculatorButtonWeight),
                text = buttonsRow[2].value
            )
        } else {
            CalculatorButton(
                modifier = modifier
                    .weight(CalculatorButtonWeight)
                    .aspectRatio(CalculatorButtonWeight),
                text = buttonsRow[0].value
            )

            Spacer(modifier = modifier.weight(SpacerWeight))

            CalculatorButton(
                modifier = modifier
                    .weight(CalculatorButtonWeight)
                    .aspectRatio(CalculatorButtonWeight),
                text = buttonsRow[1].value
            )

            Spacer(modifier = modifier.weight(SpacerWeight))

            CalculatorButton(
                modifier = modifier
                    .weight(CalculatorButtonWeight)
                    .aspectRatio(CalculatorButtonWeight),
                text = buttonsRow[2].value
            )

            Spacer(modifier = modifier.weight(SpacerWeight))

            AccentedCalculatorButton(
                modifier = modifier
                    .weight(CalculatorButtonWeight)
                    .aspectRatio(CalculatorButtonWeight),
                text = buttonsRow[3].value
            )
        }
    }
}

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    text: String
) {
    Button(
        modifier = modifier.fillMaxSize(),
        onClick = { /*TODO*/ },
        shape = CalculatorButtonShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = SecondaryContainer)
    ) {
        Text(
            style = CalculatorButtonTextStyle,
            text = text
        )
    }
}

@Composable
fun AccentedCalculatorButton(
    text: String,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.fillMaxSize(),
        onClick = { /*TODO*/ },
        shape = CalculatorButtonShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = TertiaryContainer)
    ) {
        Text(
            style = AccentedButtonTextStyle,
            text = text
        )
    }
}