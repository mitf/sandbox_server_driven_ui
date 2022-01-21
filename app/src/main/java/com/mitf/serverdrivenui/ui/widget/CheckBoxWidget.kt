package com.mitf.serverdrivenui.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget

class CheckBoxWidget(private val widgetDto: WidgetDto) : ComposableWidget {
    private val fieldName = widgetDto.data ?: "value"

    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        Row {
            Checkbox(
                checked = hoist[fieldName]?.value.equals("true", true),
                onCheckedChange = { hoist[fieldName]?.value = it.toString() })
            Text(widgetDto.label ?: "")
        }
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf(Pair(fieldName, mutableStateOf(widgetDto.default ?: "")))
    }
}