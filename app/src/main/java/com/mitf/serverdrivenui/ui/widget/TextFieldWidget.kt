package com.mitf.serverdrivenui.ui.widget

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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

/*
   TODO :
        1. Implement TextFieldRowWidget like RT/RW and Number Field
        2. Searching Added some category field like number only field Done
        3.
*  */

/*
    TODO :
        1. Implement BaseActivity for
        2. Implement endpath for navigation
* */

class TextFieldWidget(private val widgetDto: WidgetDto) : ComposableWidget {
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
        val minLength by remember { mutableStateOf(widgetDto.validation.findSubStringAfter("min")) }
        val maxLength by remember { mutableStateOf(widgetDto.validation.findSubStringAfter("max")) }

        val density = LocalDensity.current
        var passwordVisible by remember {
            mutableStateOf(
                widgetDto.classType?.contains("password") ?: false
            )
        }
        val isEnable by remember {
            mutableStateOf(
                widgetDto.classType?.contains("disable") ?: true
            )
        }

        var values by remember {
            mutableStateOf(hoist[fieldName]?.value ?: "")
        }

        Row {
            Column(modifier = Modifier.weight(1F, true)) {
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
                    value = if (isDigit) values.filter { it.isDigit() } else values,
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
                    trailingIcon = {
                        if (widgetDto.classType?.contains("password") == true) {
                            IconToggleButton(
                                enabled = !isEnable,
                                checked = passwordVisible,
                                onCheckedChange = { passwordVisible = it }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Lock else Icons.Outlined.Lock,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    visualTransformation = if (passwordVisible) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
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
                        keyboardType = if (isDigit) KeyboardType.Number else KeyboardType.Text
                    )
                )
                AnimatedVisibility(
                    visible = !isValid,
                    enter = slideInVertically(initialOffsetY = { with(density) { -40.dp.roundToPx() } })
                            + expandVertically(expandFrom = Alignment.Top)
                            + fadeIn(initialAlpha = 0.3f),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        text = errorText,
                        color = Danger500,
                        style = TinyMedium
                    )
                }
            }
        }
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf(Pair(fieldName, mutableStateOf(widgetDto.default ?: "")))
    }
}