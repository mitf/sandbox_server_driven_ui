package com.mitf.serverdrivenui.ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget

class ListWidget(private val widgetDto: WidgetDto) : ComposableWidget {
    private val fieldName = widgetDto.data ?: "value"

    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {

    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf()
    }

}