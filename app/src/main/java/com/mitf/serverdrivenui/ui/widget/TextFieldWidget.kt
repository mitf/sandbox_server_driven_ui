package com.mitf.serverdrivenui.ui.widget

import android.util.Log
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
import com.mitf.serverdrivenui.utils.mapToPair
import com.mitf.serverdrivenui.utils.toIntOrZero

/*
   TODO :
        1. Implement TextFieldRowWidget like RT/RW and Number Field Done
        2. Searching Added category field like number only field Done
        3. Searching Added category field like character only field Done
        4. Added currency field DONE
*/

/*
    TODO :
        1. Implement BaseActivity for SDUI
        2. Implement endpath for navigation
        3. Extension untuk cek tipe data
*/
class TextFieldWidget(
//    private val widgetDto: WidgetDto
    private val uiComponent: UiComponents,
    val dataMap: MutableMap<String, Any>
) : ComposableWidget {
    //    private val fieldName = widgetDto.data ?: "value"
    private val fieldName = uiComponent.slug ?: ""
    private var valueData = ""

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        var isValid by remember { mutableStateOf(true) }
        var errorText by remember { mutableStateOf("") }
        Log.d("datanyaValidation", uiComponent.validation.toString())
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
//            mutableStateOf("")
//        }
        Log.d("datanyaHoist", hoist[fieldName]?.value.toString())
        Log.d("datanyaHoistFieldName", dataMap[fieldName].toString())
        var values by remember {
            mutableStateOf(
                if (hoist[fieldName]?.value?.isEmpty() == true) {
                    dataMap[fieldName].toString()
                }
                else {
                    hoist[fieldName]?.value
                }
            )
        }
//        var values by remember {
//            mutableStateOf(
//                data[fieldName].toString()
//            )
//        }
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
//                    text = widgetDto.label ?: "",
                    text = uiComponent.label ?: "",
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
                    value = values.toString(),
                    singleLine = isSingleLine,
                    onValueChange = { data: String ->
                        values = data
                        valueData = data
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
                                Log.d("datanyaMasuk", "IsDigit")
                                if (data.length <= maxLength.toIntOrZero()){
                                    values = data.filter { it.isDigit() }
                                    valueData = data.filter { it.isDigit() }
                                }
                            }
                            isChar -> {
                                Log.d("datanyaMasuk", "IsChar")
                                if (data.length <= maxLength.toIntOrZero()){
                                    values = data.filter { it.isLetter() }
                                    valueData = data.filter { it.isLetter() }
                                }
                            }
                            else -> {
                                Log.d("datanyaMasukElseData", data.length.toString())
                                Log.d("datanyaMasukElseParent", maxLength.toIntOrZero().toString())
                                if (!data.contains(Regex("[^A-Za-z0-9 ,.]"))
                                    && data.length <= maxLength.toIntOrZero()
                                ){
                                    values = data
                                    valueData = data
                                }
                            }
                        }
                        hoist[fieldName]?.value = data
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
        return mapOf(mapToPair(fieldName, dataMap[fieldName]))
    }

}