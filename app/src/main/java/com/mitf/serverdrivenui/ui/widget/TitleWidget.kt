package com.mitf.serverdrivenui.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget

class TitleWidget(private val widgetDto: WidgetDto) : ComposableWidget {

    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        Text(
            widgetDto.label ?: "",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 30.sp
        )
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf()
    }
}