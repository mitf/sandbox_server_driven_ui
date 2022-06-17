package com.mitf.serverdrivenui.ui.widget

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.theme.*
import com.mitf.serverdrivenui.utils.findString
import com.mitf.serverdrivenui.utils.findSubStringAfter
import com.mitf.serverdrivenui.utils.toIntOrZero

class RowFieldWidget(
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
        val maxWidth by remember {
            mutableStateOf(
                widgetDto.classType.findSubStringAfter("width").toIntOrZero()
            )
        }
        val minLength by remember { mutableStateOf(widgetDto.validation.findSubStringAfter("min")) }
        val maxLength by remember { mutableStateOf(widgetDto.validation.findSubStringAfter("max")) }

        val isEnable by remember {
            mutableStateOf(
                widgetDto.classType?.contains("disable") ?: true
            )
        }

        var values by remember {
            mutableStateOf(hoist[fieldName]?.value ?: "")
        }

        Column(
            modifier = Modifier
                .widthIn(20.dp, maxWidth.dp)
                .padding(5.dp, 0.dp)
        ) {
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
                isError = !isValid,
                enabled = !isEnable,
                value = if (isDigit) values.filter { it.isDigit() } else values.filter { it.isLetter() } ,
                singleLine = isSingleLine,
                onValueChange = { data: String ->
                    if (data.length <= maxLength.toIntOrZero()) values = data
                    isValid = when {
                        (data.isEmpty() && required.contains("required")) -> {
                            errorText = "${widgetDto.label} tidak boleh kosong"
                            false
                        }
                        data.length < minLength.toIntOrZero() -> {
                            errorText =
                                "Mohon masukan ${widgetDto.label} sesuai format (Minimal $minLength Character)"
                            false
                        }
                        else -> true
                    }
                },
                placeholder = {
                    Text(
                        text = widgetDto.placeholder ?: "",
                        style = CaptionRegular,
                        color = Black600
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = if (isSingleLine || widgetDto.classType?.contains("password") == true) {
                        ImeAction.Next
                    } else {
                        ImeAction.Default
                    },
                    capitalization = if (widgetDto.classType?.contains("password") == false) {
                        KeyboardCapitalization.Characters
                    }
                    else {
                        KeyboardCapitalization.None
                    },
                    autoCorrect = false,
                    keyboardType = if (isDigit) KeyboardType.Number else KeyboardType.Text
                )
            )
        }
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf()
    }
}