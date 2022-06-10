package com.mitf.serverdrivenui.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.dto.ScreenDto
import com.mitf.serverdrivenui.dto.ViewType
import com.mitf.serverdrivenui.ui.widget.*

class Screen(screenDto: ScreenDto) {
    var widget = screenDto.children?.map {
        it.getComposableWidget()
    } ?: listOf()

    @Composable
    fun compose() {
        Column(modifier = Modifier.wrapContentHeight()) {
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
    override fun compose(hoist: Map<String, MutableState<String>>) {}

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf()
    }
}

fun WidgetDto.getComposableWidget(): ComposableWidget {
    return when(this.viewtype) {
        ViewType.TEXT_FIELD -> TextFieldWidget(this)
        ViewType.TITLE -> TitleWidget(this)
        ViewType.TEXT -> TextWidget(this)
        ViewType.FORM -> FormWidget(this)
        ViewType.CHECKBOX -> CheckBoxWidget(this)
        ViewType.LOGIN_FORM -> LoginFormWidget(this)
        ViewType.TEXT_FIELD_SELECTOR -> TextFieldSelectorWidget(this)
        ViewType.RADIO -> RadioWidget(this)
        else -> EmptyWidget()
    }
}