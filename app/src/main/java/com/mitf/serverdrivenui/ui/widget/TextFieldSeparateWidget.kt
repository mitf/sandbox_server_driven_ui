package com.mitf.serverdrivenui.ui.widget

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.theme.*
import com.mitf.serverdrivenui.utils.findString
import com.mitf.serverdrivenui.utils.findSubStringAfter
import com.mitf.serverdrivenui.utils.getValueString

class TextFieldSeparateWidget(
    val widgetDto: WidgetDto
) : ComposableWidget {

    private val fieldName = widgetDto.data ?: "value"

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        val isValid by remember { mutableStateOf(true) }
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
        val isEnable by remember {
            mutableStateOf(
                widgetDto.classType?.contains("disable") ?: true
            )
        }
        val values by remember {
            mutableStateOf(hoist[fieldName]?.value ?: "")
        }

        Column {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .wrapContentWidth(),
                text = widgetDto.label ?: "",
                style = CaptionRegular
            )
            OutlinedTextField(
                modifier = Modifier
                    .background(
                        color = if (!isEnable) White else Black200,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .wrapContentWidth(),
                value = if (isDigit) values.filter { it.isDigit() } else values,
                onValueChange = {},
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
                singleLine = isSingleLine,
                isError = !isValid,
                enabled = !isEnable,
                placeholder = {
                    Text(
                        text = widgetDto.placeholder ?: "",
                        style = CaptionRegular,
                        color = Black600
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = if (isDigit) KeyboardType.Number else KeyboardType.Text
                )
            )
        }
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf(getValueString(fieldName, widgetDto))
    }
}