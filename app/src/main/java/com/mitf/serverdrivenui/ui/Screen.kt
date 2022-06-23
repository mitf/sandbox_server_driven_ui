package com.mitf.serverdrivenui.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.mitf.serverdrivenui.dto.*
import com.mitf.serverdrivenui.ui.widget.*

class Screen(
//    val screenDto: ScreenDto,
    private val screenDto: ScreenDtoNew
) {
    //    val dataMap = screenDto.data[]
    var widget = screenDto.ui_components?.map {
        it.getComposableWidget(screenDto.data ?: mutableMapOf())
    } ?: listOf()

//    var widget = screenDto.children?.map {
//        it.getComposableWidget()
//    } ?: listOf()

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
//            screenDto.data?.forEach { (key, value) ->
//                Log.d("datanyaNew", "Key: $key Value: $value")
//            }
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

//fun WidgetDto.getComposableWidget(): ComposableWidget {
//    return when (this.viewtype) {
//        ViewType.TEXT_FIELD -> TextFieldWidget(this)
//        ViewType.TEXT_FIELD_SELECTOR -> TextFieldSelectorWidget(this)
//        ViewType.TEXT_FIELD_CURRENCY -> TextFieldCurrencyWidget(this)
//        ViewType.TITLE -> TitleWidget(this)
//        ViewType.TEXT -> TextWidget(this)
//        ViewType.FORM -> FormWidget(this)
//        ViewType.CHECKBOX -> CheckBoxWidget(this)
//        ViewType.LOGIN_FORM -> LoginFormWidget(this)
//        ViewType.RADIO -> RadioWidget(this)
//        ViewType.TEXT_ROW -> TextRowWidget(this)
//        ViewType.TEXT_ROW_FIELD -> RowFieldWidget(this)
//        else -> EmptyWidget()
//    }
//}

fun UiComponents.getComposableWidget(data: MutableMap<String, Any>): ComposableWidget {
    return when (this.type) {
        Type.HEADER -> FormWidget(this, data)
        Type.TEXT_BOX -> TextFieldWidget(this, data)
        Type.TEXT_BOX_MULTILINE -> TextFieldMultilineWidget(this, data)
        Type.TEXT_BOX_SELECTOR -> TextFieldSelectorWidget(this, data)
        else -> EmptyWidget()
    }
}