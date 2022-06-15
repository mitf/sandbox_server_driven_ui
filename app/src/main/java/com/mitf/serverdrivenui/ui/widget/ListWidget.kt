package com.mitf.serverdrivenui.ui.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.WidgetViewModel

class ListWidget(private val widgetDto: WidgetDto) : ComposableWidget {
    private val fieldName = widgetDto.data ?: "value"

    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {

    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf()
    }

    @Composable
    private fun itemLeadsManagement() {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item(10) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()) {
                    Column {
                        Text(text = "test")
                    }
                }
            }
        }
    }
}