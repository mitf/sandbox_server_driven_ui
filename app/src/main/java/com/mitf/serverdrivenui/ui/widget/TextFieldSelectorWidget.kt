package com.mitf.serverdrivenui.ui.widget

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mitf.serverdrivenui.R
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.theme.*
import com.mitf.serverdrivenui.utils.ValidatorType
import com.mitf.serverdrivenui.utils.findString
import com.mitf.serverdrivenui.utils.findSubStringAfter
import kotlinx.coroutines.launch

class TextFieldSelectorWidget(private val widgetDto: WidgetDto) : ComposableWidget {
    private val fieldName = widgetDto.data ?: "value"

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        var isValid by remember { mutableStateOf(true) }
        var errorText by remember { mutableStateOf("") }
        val required by remember {
            mutableStateOf(widgetDto.validation.findString(ValidatorType.REQUIRED.type))
        }
        val minLength by remember {
            mutableStateOf(
                widgetDto.validation.findSubStringAfter(
                    ValidatorType.MIN.type
                )
            )
        }
        val maxLength by remember {
            mutableStateOf(
                widgetDto.validation.findSubStringAfter(
                    ValidatorType.MAX.type
                )
            )
        }

        val density = LocalDensity.current
        var passwordVisible by remember {
            mutableStateOf(
                widgetDto.classType?.contains("password") ?: false
            )
        }

        val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

        ModalBottomSheetLayout(
            sheetContent = {
                LazyColumn {
                    items(50) {
                        ListItem(
                            text = { Text("Item $it") },
                            icon = {
                                Icon(
                                    Icons.Default.ThumbUp,
                                    contentDescription = "Localized description"
                                )
                            }
                        )
                    }
                }
            }, sheetState = state
        ) {
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
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = { scope.launch { state.show() } },
                                onTap = { scope.launch { state.show() } },
                            )
                        }
                        .background(
                            color = if (widgetDto.isEnable != false) White else Black200,
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(size = 8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = if (widgetDto.isEnable != false) White else Black200,
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
                    value = hoist[fieldName]?.value ?: "",
                    onValueChange = { data: String ->
                        if (data.length <= maxLength.toInt()) {
                            hoist[fieldName]?.value = data
                        }
                        isValid = when {
                            (data.isEmpty() && required.contains("required")) -> {
                                errorText = "${widgetDto.label} tidak boleh kosong"
                                false
                            }
                            data.length < minLength.toInt() -> {
                                errorText =
                                    "Mohon masukan ${widgetDto.label} sesuai format (Minimal $minLength Character)"
                                false
                            }
                            else -> true
                        }
                    },
                    placeholder = { Text(text = "hint", style = CaptionRegular, color = Black600) },
                    trailingIcon = {
                        if (widgetDto.classType?.contains("password") == true) {
                            IconToggleButton(
                                checked = passwordVisible,
                                onCheckedChange = { passwordVisible = it }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Outlined.Add else Icons.Outlined.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    visualTransformation = if (passwordVisible) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
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
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf(Pair(fieldName, mutableStateOf(widgetDto.default ?: "")))
    }
}