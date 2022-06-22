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
import com.mitf.serverdrivenui.dto.UiComponents
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.theme.*
import com.mitf.serverdrivenui.utils.findString
import com.mitf.serverdrivenui.utils.findSubStringAfter
import com.mitf.serverdrivenui.utils.toIntOrZero

class TextFieldMultilineWidget(
//    private val widgetDto: WidgetDto
    private val uiComponent: UiComponents
) : ComposableWidget {
    //    private val fieldName = widgetDto.data ?: "value"
    private val fieldName = ""

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        var isValid by remember { mutableStateOf(true) }
        var errorText by remember { mutableStateOf("") }
//        val required by remember {
//            mutableStateOf(widgetDto.validation.findString("required"))
//        }
//        val isSingleLine by remember {
//            mutableStateOf(
//                widgetDto.classType?.contains("singleline") ?: false
//            )
//        }
//        val isDigit by remember {
//            mutableStateOf(
//                widgetDto.classType?.contains("number") ?: false
//            )
//        }
//        val isChar by remember {
//            mutableStateOf(
//                widgetDto.classType?.contains("number") ?: false
//            )
//        }
//        val minLength by remember { mutableStateOf(widgetDto.validation.findSubStringAfter("min")) }
//        val maxLength by remember { mutableStateOf(widgetDto.validation.findSubStringAfter("max")) }
//
//        val density = LocalDensity.current
//        var passwordVisible by remember {
//            mutableStateOf(
//                widgetDto.classType?.contains("password") ?: false
//            )
//        }
//        val isEnable by remember {
//            mutableStateOf(
//                widgetDto.classType?.contains("disable") ?: true
//            )
//        }
//
//        var values by remember {
//            mutableStateOf(hoist[fieldName]?.value ?: "")
//        }
        var values by remember {
            mutableStateOf("")
        }
        val required by remember {
            mutableStateOf(
                uiComponent.validation.findString("required")
            )
        }
        val isSingleLine by remember {
            mutableStateOf(
                uiComponent.attributes?.contains("singleline") ?: false
            )
        }
        val isDigit by remember {
            mutableStateOf(
                uiComponent.attributes?.contains("number") ?: false
            )
        }
        val isChar by remember {
            mutableStateOf(
                uiComponent.attributes?.contains("text") ?: false
            )
        }
        val minLength by remember {
            mutableStateOf(
                uiComponent.validation.findSubStringAfter("min")
            )
        }
        val maxLength by remember {
            mutableStateOf(
                uiComponent.validation.findSubStringAfter("max")
            )
        }
        val isEnable by remember {
            mutableStateOf(
                uiComponent.attributes?.contains("disable") ?: false
            )
        }
        val hint by remember {
            mutableStateOf(
                uiComponent.attributes.findSubStringAfter("place_holder")
            )
        }

        Row {
            Column(modifier = Modifier.weight(1F, true)) {
                Text(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .wrapContentWidth(),
                    text = uiComponent.label ?: "",
                    style = CaptionRegular
                )
                OutlinedTextField(
                    modifier = Modifier
                        .background(
                            color = if (!isEnable) White else Black200,
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .fillMaxWidth()
                        .requiredHeight(150.dp),
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
                    value = values,
                    singleLine = isSingleLine,
                    onValueChange = { data: String ->
                        isValid = when {
                            (data.isEmpty() && required.contains("required")) -> {
//                                errorText = "${widgetDto.label} tidak boleh kosong"
                                errorText = "${uiComponent.label} tidak boleh kosong"
                                false
                            }
                            data.length < minLength.toIntOrZero() -> {
                                errorText =
//                                    "Mohon masukan ${widgetDto.label} sesuai format (Minimal $minLength Character)"
                                    "Mohon masukan ${uiComponent.label} sesuai format (Minimal $minLength Character)"
                                false
                            }
                            else -> true
                        }
                        when {
                            isDigit -> {
                                if (data.length <= maxLength.toIntOrZero())
                                    values = data.filter { it.isDigit() }
                            }
                            isChar -> {
                                if (data.length <= maxLength.toIntOrZero())
                                    values = data.filter { it.isLetter() }
                            }
                            else -> {
                                if (
                                    !data.contains(Regex("[^A-Za-z0-9 ,.]"))
                                    && data.length <= maxLength.toIntOrZero()
                                ) values = data
                            }
                        }

                    },
                    placeholder = {
                        Text(
                            text = hint,
                            style = CaptionRegular,
                            color = Black600
                        )
                    },
//                    trailingIcon = {
//                        if (widgetDto.classType?.contains("password") == true) {
//                            IconToggleButton(
//                                enabled = !isEnable,
//                                checked = passwordVisible,
//                                onCheckedChange = { passwordVisible = it }) {
//                                Icon(
//                                    imageVector = if (passwordVisible) Icons.Default.Lock else Icons.Outlined.Lock,
//                                    contentDescription = null
//                                )
//                            }
//                        }
//                    },
//                    visualTransformation = if (passwordVisible) {
//                        PasswordVisualTransformation()
//                    } else {
//                        VisualTransformation.None
//                    },
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = if (isSingleLine || widgetDto.classType?.contains("password") == true) {
//                            ImeAction.Next
//                        } else {
//                            ImeAction.Default
//                        },
//                        capitalization = if (widgetDto.classType?.contains("password") == false) {
//                            KeyboardCapitalization.Characters
//                        } else {
//                            KeyboardCapitalization.None
//                        },
//                        keyboardType = if (isDigit) KeyboardType.Number else KeyboardType.Text
//                    )
                    keyboardOptions = KeyboardOptions(
                        imeAction = if (isSingleLine) {
                            ImeAction.Next
                        } else {
                            ImeAction.Default
                        },
                        keyboardType = if (isDigit) KeyboardType.Number else KeyboardType.Text
                    )
                )
            }
        }
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf()
//        return mapOf(Pair(fieldName, mutableStateOf("")))
    }
}