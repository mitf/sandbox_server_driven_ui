package com.mitf.serverdrivenui.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.WidgetViewModel

class TextWidget(private val widgetDto: WidgetDto) : ComposableWidget {
    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        Text(
            widgetDto.label ?: "",
            modifier = Modifier.fillMaxWidth(), 
            textAlign = TextAlign.Start
        )
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf()
    }
}