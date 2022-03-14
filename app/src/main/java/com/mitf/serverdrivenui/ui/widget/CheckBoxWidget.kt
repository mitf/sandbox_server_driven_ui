package com.mitf.serverdrivenui.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.getComposableWidget
import com.mitf.serverdrivenui.ui.theme.CaptionRegular

class CheckBoxWidget(private val widgetDto: WidgetDto) : ComposableWidget {
    private val fieldName = widgetDto.data ?: "value"

    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        Column {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                text = widgetDto.label ?: "",
                style = CaptionRegular
            )
        }
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf(Pair(fieldName, mutableStateOf(widgetDto.default ?: "")))
    }

    @Composable
    private fun itemCheckbox(label: String, isChecked: (Boolean) -> Unit) {
        val checked = remember { mutableStateOf(false) }
        Row {
            Checkbox(
                checked = checked.value,
                onCheckedChange = {
                    checked.value = it
                    isChecked(it)
                }
            )
            Text(label)
        }
    }
}