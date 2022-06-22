package com.mitf.serverdrivenui.ui.widget

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.theme.*
import com.mitf.serverdrivenui.utils.findString
import com.mitf.serverdrivenui.utils.findSubStringAfter
import com.mitf.serverdrivenui.utils.getValueString
import com.mitf.serverdrivenui.utils.toIntOrZero
import java.text.NumberFormat
import java.util.*

class TextFieldCurrencyWidget(
    private val widgetDto: WidgetDto
): ComposableWidget {
    private val fieldName = widgetDto.data ?: "value"

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        var isValid by remember { mutableStateOf(true) }
        var errorText by remember { mutableStateOf("") }
        val required by remember {
            mutableStateOf(widgetDto.validation.findString("required"))
        }
        val isSingleLine by remember {
            mutableStateOf(
                widgetDto.classType?.contains("singleline") ?: false
            )
        }
        val isDigit by remember {
            mutableStateOf(
                widgetDto.classType?.contains("number") ?: false
            )
        }
        val maxLength by remember { mutableStateOf(widgetDto.validation.findSubStringAfter("max")) }

        val density = LocalDensity.current
        val isEnable by remember {
            mutableStateOf(
                widgetDto.classType?.contains("disable") ?: true
            )
        }

        var values by remember {
            mutableStateOf(hoist[fieldName]?.value ?: "")
        }

        Column {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                text = widgetDto.label ?: "",
                style = CaptionRegular
            )
            OutlinedTextField(
                modifier = Modifier
                    .background(
                        color = if (!isEnable) White else Black200,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .fillMaxWidth(),
                shape = RoundedCornerShape(size = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = if (!isEnable) White else Black200,
                    cursorColor = Blue600,
                    focusedBorderColor = Blue600,
                    errorBorderColor = Danger500,
                    errorCursorColor = Danger500,
                    unfocusedBorderColor = Black300,
                    disabledTextColor = Black300,
                    disabledLabelColor = Black300,
                    disabledBorderColor = Black300,
                    disabledPlaceholderColor = Black300
                ),
                visualTransformation = CurrencyTransformation(),
                isError = !isValid,
                enabled = !isEnable,
                value = values,
                singleLine = isSingleLine,
                onValueChange = { data: String ->
                    if (data.length <= maxLength.toIntOrZero()) values = data
                },
                placeholder = {
                    Text(
                        text = widgetDto.placeholder ?: "",
                        style = CaptionRegular,
                        color = Black600
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf(getValueString(fieldName, widgetDto))
    }

    class CurrencyTransformation: VisualTransformation{
        override fun filter(text: AnnotatedString): TransformedText {
            val transformation = formatCurrency(text)
            return TransformedText(
                text = AnnotatedString(transformation),
                offsetMapping = object : OffsetMapping{
                    override fun originalToTransformed(offset: Int): Int = transformation.length
                    override fun transformedToOriginal(offset: Int): Int = text.length
                }
            )
        }

        private fun formatCurrency(s: CharSequence): String {
            val cleanString = s.replace(Regex("[^0-9]"), "")
            val parsed = cleanString.toBigDecimalOrNull() ?: 0.toBigDecimal()
            return NumberFormat.getCurrencyInstance(Locale("id", "id")).format((parsed))
                .removeSuffix(",00")
        }
    }
}