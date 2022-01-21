package com.mitf.serverdrivenui.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mitf.serverdrivenui.BackEndService
import com.mitf.serverdrivenui.ScreenJson
import com.mitf.serverdrivenui.ServiceLocator
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.getComposableWidget

class FormWidget(
    private val widgetDto: WidgetDto
) : ComposableWidget {
    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        val childElements = widgetDto.children?.map { it.getComposableWidget() } ?: listOf()
        val children = childElements.map { Pair(it, it.getHoist()) }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            children.map {
                it.first.compose(it.second)
            }
            val json = ScreenJson.current
            Button(onClick = {
                val parameters = children.flatMap {
                    it.second.entries.map { maps ->
                        Pair(
                            maps.key,
                            maps.value.value
                        )
                    }
                }.toMap()
                val newPage = ServiceLocator.resolve(BackEndService::class.java)
                    .getPage(widgetDto.data ?: "", parameters)
                json.held.value = newPage
            },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(widgetDto.label ?: "")
            }
        }
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf()
    }
}