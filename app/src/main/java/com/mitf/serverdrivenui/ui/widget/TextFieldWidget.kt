package com.mitf.serverdrivenui.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget

class TextFieldWidget(private val widgetDto: WidgetDto) : ComposableWidget {
    private val fieldName = widgetDto.data ?: "value"

    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        TextField(
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
            value = hoist[fieldName]?.value ?: "",
            onValueChange = { hoist[fieldName]?.value = it },
            label = { Text(widgetDto.label ?: "")})
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf(Pair(fieldName, mutableStateOf(widgetDto.default ?: "")))
    }
}