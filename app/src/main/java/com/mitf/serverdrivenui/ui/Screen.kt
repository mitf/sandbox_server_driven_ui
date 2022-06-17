package com.mitf.serverdrivenui.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.mitf.serverdrivenui.dto.ScreenDto
import com.mitf.serverdrivenui.dto.ViewType
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.widget.*

class Screen(val screenDto: ScreenDto) {
    private var isRow = false
    var widget = screenDto.children?.map {
        it.getComposableWidget()
    } ?: listOf()
//    var widget = screenDto.children?.map {
//        it.getComposableWidget()
//    } ?: listOf()

//    @Composable
//    fun compose() {
//        Log.d("datanyaError", screenDto.children?.get(0)?.children?.size.toString())
//        Log.d("datanyaChecks", isRow.toString())
//        val scrollState = rememberScrollState()
//        Column(
//            modifier = Modifier.verticalScroll(
//                state = scrollState,
//            )
//        ) {
////            Column {
////                Text(text = "tester1")
////                Text(text = "tester2")
////                Text(text = "tester3")
////                Text(text = "tester4")
////                Text(text = "tester5")
////            }
////            Row {
////                Column(modifier = Modifier
////                    .weight(1F, true)
////                    .fillMaxWidth()) {
////                    Text(
////                        modifier = Modifier
////                            .padding(top = 16.dp, bottom = 8.dp)
////                            .fillMaxWidth(),
////                        text = "tester",
////                        style = CaptionRegular
////                    )
////                    OutlinedTextField(
////                        modifier = Modifier
////                            .background(
////                                color = Black200,
////                                shape = RoundedCornerShape(size = 8.dp)
////                            )
////                            .fillMaxWidth(),
////                        value = "",
////                        onValueChange = {},
////                        shape = RoundedCornerShape(size = 8.dp),
////                        colors = TextFieldDefaults.outlinedTextFieldColors(
////                            backgroundColor = Black200,
////                            cursorColor = Blue600,
////                            focusedBorderColor = Blue600,
////                            errorBorderColor = Danger500,
////                            errorCursorColor = Danger500,
////                            unfocusedBorderColor = Black300,
////                            disabledTextColor = Black300,
////                            disabledLabelColor = Black300,
////                            disabledBorderColor = Black300,
////                            disabledPlaceholderColor = Black300
////                        ),
////                        placeholder = {
////                            Text(
////                                text = "tester",
////                                style = CaptionRegular,
////                                color = Black600
////                            )
////                        },
////                        keyboardOptions = KeyboardOptions(
////                            imeAction = ImeAction.Next,
////                            keyboardType = KeyboardType.Text
////                        )
////                    )
////                }
////                Column {
////                    Text(
////                        modifier = Modifier
////                            .padding(top = 16.dp, bottom = 8.dp)
////                            .wrapContentWidth(),
////                        text = "tester",
////                        style = CaptionRegular
////                    )
////                    OutlinedTextField(
////                        modifier = Modifier
////                            .background(
////                                color = Black200,
////                                shape = RoundedCornerShape(size = 8.dp)
////                            )
////                            .wrapContentWidth(),
////                        value = "",
////                        onValueChange = {},
////                        shape = RoundedCornerShape(size = 8.dp),
////                        colors = TextFieldDefaults.outlinedTextFieldColors(
////                            backgroundColor = Black200,
////                            cursorColor = Blue600,
////                            focusedBorderColor = Blue600,
////                            errorBorderColor = Danger500,
////                            errorCursorColor = Danger500,
////                            unfocusedBorderColor = Black300,
////                            disabledTextColor = Black300,
////                            disabledLabelColor = Black300,
////                            disabledBorderColor = Black300,
////                            disabledPlaceholderColor = Black300
////                        ),
////                        placeholder = {
////                            Text(
////                                text = "tester",
////                                style = CaptionRegular,
////                                color = Black600
////                            )
////                        },
////                        keyboardOptions = KeyboardOptions(
////                            imeAction = ImeAction.Next,
////                            keyboardType = KeyboardType.Text
////                        )
////                    )
////                }
////            }
//        }
//    }

    @Composable
    fun compose() {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(
                state = scrollState,
            )
        ) {
            val fields = widget.map { it.getHoist() }
            widget.zip(fields).map {
                it.first.compose(it.second)
            }
        }
    }
}

interface ComposableWidget {
    @Composable
    fun compose(hoist: Map<String, MutableState<String>>)

    fun getHoist(): Map<String, MutableState<String>>
}

class EmptyWidget : ComposableWidget {

    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf()
    }
}

fun WidgetDto.getComposableWidget(): ComposableWidget {
    return when (this.viewtype) {
        ViewType.TEXT_FIELD -> TextFieldWidget(this)
        ViewType.TEXT_FIELD_SELECTOR -> TextFieldSelectorWidget(this)
        ViewType.TEXT_FIELD_CURRENCY -> TextFieldCurrencyWidget(this)
        ViewType.TITLE -> TitleWidget(this)
        ViewType.TEXT -> TextWidget(this)
        ViewType.FORM -> FormWidget(this)
        ViewType.CHECKBOX -> CheckBoxWidget(this)
        ViewType.LOGIN_FORM -> LoginFormWidget(this)
        ViewType.RADIO -> RadioWidget(this)
        ViewType.TEXT_ROW -> TextRowWidget(this)
        ViewType.TEXT_ROW_FIELD -> RowFieldWidget(this)
        else -> EmptyWidget()
    }
}