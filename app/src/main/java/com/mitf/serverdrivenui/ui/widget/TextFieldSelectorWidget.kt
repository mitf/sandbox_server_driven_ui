package com.mitf.serverdrivenui.ui.widget

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mitf.serverdrivenui.dto.OptionModel
import com.mitf.serverdrivenui.dto.UiComponents
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.theme.*
import com.mitf.serverdrivenui.utils.*

class TextFieldSelectorWidget(
//    private val widgetDto: WidgetDto
    private val uiComponent: UiComponents,
    private val dataMap: MutableMap<String, Any>

) : ComposableWidget {
    private val fieldName = uiComponent.slug ?: "value"

    companion object {
        val isClicked = mutableStateOf(false)
        val selectorLabel = mutableStateOf("")
        val itemSelected = mutableStateOf("")
        val fieldSelector = mutableStateOf("")
        val widgetId = mutableStateOf("")
        val optionItem = mutableStateListOf<OptionModel>()
    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        Log.d("checkDataId", widgetId.value)
        var isValid by remember { mutableStateOf(true) }
        var errorText by remember { mutableStateOf("") }
        val required by remember {
            mutableStateOf(uiComponent.validation.findString(ValidatorType.REQUIRED.type))
        }
        optionItem.clear()
        optionItem.addAll(uiComponent.options ?: listOf())
        var value by remember {
            mutableStateOf("")
        }
//        var value by remember {
//            mutableStateOf(hoist[fieldName]?.value ?: "")
//        }
        val hint by remember {
            mutableStateOf(
                uiComponent.attributes.findSubStringAfter("place_holder")
            )
        }
        val isEnable by remember {
            mutableStateOf(
                uiComponent.attributes?.contains("disable") ?: false
            )
        }

        if (fieldName == fieldSelector.value) {
            value = itemSelected.value
            hoist[fieldName]?.value = itemSelected.value
        }

        val density = LocalDensity.current

        Column(
            modifier = Modifier
                .layoutId(fieldName)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            if (!isEnable){
                                widgetId.value = fieldName
                                fieldSelector.value = fieldName
                                selectorLabel.value = uiComponent.label ?: ""
                                isClicked.value = true
                            }
                        },
                        onTap = {
                            if (!isEnable){
                                widgetId.value = fieldName
                                fieldSelector.value = fieldName
                                selectorLabel.value = uiComponent.label ?: ""
                                isClicked.value = true
                            }
                        }
                    )
                }
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .fillMaxWidth(),
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
                enabled = false,
                value = value,
                onValueChange = { data: String ->
                    value = data
                    isValid = when {
                        (data.isEmpty() && required.contains("required")) -> {
                            errorText = "${uiComponent.label} tidak boleh kosong"
                            false
                        }
                        else -> true
                    }
                },
                placeholder = {
                    Text(
                        text = hint,
                        style = CaptionRegular,
                        color = Black600
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
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

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf(mapToPair(fieldName, dataMap[fieldName]))
//        return mapOf(getValueString(fieldName, uiComponent))
//        return mapOf(Pair(fieldName, mutableStateOf(getValueString(widgetDto))))
    }
}