package com.mitf.serverdrivenui.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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

    init {
        Log.d("CallInit","Init")
        screenDto.children?.get(0)?.children?.forEach {
            Log.d("datanyaChecksPosition", it.viewtype.toString())
        }
    }
    @Composable
    fun compose() {
        Log.d("datanyaError", screenDto.children?.get(0)?.children?.size.toString())
        Log.d("datanyaChecks", isRow.toString())
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(
                state = scrollState,
            )
        ) {
            val fields = widget.map { it.getHoist() }
//            Column {
//                Text(text = "tester1")
//                Text(text = "tester2")
//                Text(text = "tester3")
//                Text(text = "tester4")
//                Text(text = "tester5")
//            }
//            Row {
//                Column(modifier = Modifier
//                    .weight(1F, true)
//                    .fillMaxWidth()) {
//                    Text(
//                        modifier = Modifier
//                            .padding(top = 16.dp, bottom = 8.dp)
//                            .fillMaxWidth(),
//                        text = "tester",
//                        style = CaptionRegular
//                    )
//                    OutlinedTextField(
//                        modifier = Modifier
//                            .background(
//                                color = Black200,
//                                shape = RoundedCornerShape(size = 8.dp)
//                            )
//                            .fillMaxWidth(),
//                        value = "",
//                        onValueChange = {},
//                        shape = RoundedCornerShape(size = 8.dp),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            backgroundColor = Black200,
//                            cursorColor = Blue600,
//                            focusedBorderColor = Blue600,
//                            errorBorderColor = Danger500,
//                            errorCursorColor = Danger500,
//                            unfocusedBorderColor = Black300,
//                            disabledTextColor = Black300,
//                            disabledLabelColor = Black300,
//                            disabledBorderColor = Black300,
//                            disabledPlaceholderColor = Black300
//                        ),
//                        placeholder = {
//                            Text(
//                                text = "tester",
//                                style = CaptionRegular,
//                                color = Black600
//                            )
//                        },
//                        keyboardOptions = KeyboardOptions(
//                            imeAction = ImeAction.Next,
//                            keyboardType = KeyboardType.Text
//                        )
//                    )
//                }
//                Column {
//                    Text(
//                        modifier = Modifier
//                            .padding(top = 16.dp, bottom = 8.dp)
//                            .wrapContentWidth(),
//                        text = "tester",
//                        style = CaptionRegular
//                    )
//                    OutlinedTextField(
//                        modifier = Modifier
//                            .background(
//                                color = Black200,
//                                shape = RoundedCornerShape(size = 8.dp)
//                            )
//                            .wrapContentWidth(),
//                        value = "",
//                        onValueChange = {},
//                        shape = RoundedCornerShape(size = 8.dp),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            backgroundColor = Black200,
//                            cursorColor = Blue600,
//                            focusedBorderColor = Blue600,
//                            errorBorderColor = Danger500,
//                            errorCursorColor = Danger500,
//                            unfocusedBorderColor = Black300,
//                            disabledTextColor = Black300,
//                            disabledLabelColor = Black300,
//                            disabledBorderColor = Black300,
//                            disabledPlaceholderColor = Black300
//                        ),
//                        placeholder = {
//                            Text(
//                                text = "tester",
//                                style = CaptionRegular,
//                                color = Black600
//                            )
//                        },
//                        keyboardOptions = KeyboardOptions(
//                            imeAction = ImeAction.Next,
//                            keyboardType = KeyboardType.Text
//                        )
//                    )
//                }
//            }
            if (isRow){
                Row(modifier = Modifier.weight(1F, true)) {
                    widget.zip(fields).map {
                        it.first.compose(it.second)
                    }
                }
            }
            else{
                Column{
                    widget.zip(fields).map {
                        it.first.compose(it.second)
                    }
                }
            }
//            Row(modifier = Modifier.wrapContentWidth()) {
//                val fields = widget.map { it.getHoist() }
//                widget.zip(fields).map {
//                    it.first.compose(it.second)
//                }
//            }
//            Log.d("datanyaCheckings", (check?.get(0)).toString())
//            Column {
//                val fields = widget.map { it.getHoist() }
//                widget.zip(fields).map {
//                    it.first.compose(it.second)
//                }
//            }
//            if (check?.get(0) == true){
//                Log.d("datanyaWidget", "masukIf")
//                Column {
//                    val fields = widget.map { it.getHoist() }
//                    widget.zip(fields).map {
//                        it.first.compose(it.second)
//                    }
//                }
//            }
//            else{
//                Log.d("datanyaWidget", "masukElse")
//                Row(modifier = Modifier.weight(1F, true)) {
//                    val fields = widget.map { it.getHoist() }
//                    widget.zip(fields).map {
//                        it.first.compose(it.second)
//                    }
//                }
//            }
        }
    }

//    @Composable
//    fun compose() {
//        val scrollState = rememberScrollState()
//        Column(
//            modifier = Modifier.verticalScroll(
//                state = scrollState,
//            )
//        ) {
//            val fields = widget.map { it.getHoist() }
//            widget.zip(fields).map {
//                it.first.compose(it.second)
//            }
//        }
//    }
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
        ViewType.TEXT_FIELD_SEPARATE -> TextFieldSeparateWidget(this)
        ViewType.TEXT_FIELD_SELECTOR -> TextFieldSelectorWidget(this)
        ViewType.TEXT_FIELD_CURRENCY -> TextFieldCurrencyWidget(this)
        ViewType.TITLE -> TitleWidget(this)
        ViewType.TEXT -> TextWidget(this)
        ViewType.FORM -> FormWidget(this)
        ViewType.CHECKBOX -> CheckBoxWidget(this)
        ViewType.LOGIN_FORM -> LoginFormWidget(this)
        ViewType.RADIO -> RadioWidget(this)
        else -> EmptyWidget()
    }
}