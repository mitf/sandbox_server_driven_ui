package com.mitf.serverdrivenui.ui.widget

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mitf.serverdrivenui.ScreenJson
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
            modifier = Modifier
                .padding(24.dp)
                .wrapContentHeight(),
        ) {
            children.map {
                it.first.compose(it.second)
            }
            Button(
                onClick = {
                    val parameters = children.flatMap {
                        it.second.entries.map { maps ->
                            Pair(
                                maps.key,
                                maps.value.value
                            )
                        }
                    }.toMap()
                    Log.e("param", "$parameters")
//                val newPage = ServiceLocator.resolve(BackEndService::class.java)
//                    .getPage(widgetDto.data ?: "", parameters)
//                json.held.value = newPage
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(widgetDto.label ?: "")
            }
        }
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf()
    }
}