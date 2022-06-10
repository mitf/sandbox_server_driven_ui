package com.mitf.serverdrivenui.ui.widget

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.WidgetViewModel
import com.mitf.serverdrivenui.ui.theme.CaptionRegular

class RadioWidget(private val widgetDto: WidgetDto) : ComposableWidget {
    private val fieldName = widgetDto.data ?: "value"

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        val (selectedOption, optionSelected) = remember {
            mutableStateOf(widgetDto.options?.find { it.value.contains(widgetDto.default ?: "") })
        }

        Column {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                text = widgetDto.label ?: "",
                style = CaptionRegular
            )
            Row(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                widgetDto.options?.forEach { item ->
                    Row(
                        modifier = Modifier
                            .padding(end = 43.dp)
                            .selectable(
                                selected = item == selectedOption,
                                onClick = {
                                    optionSelected(item)
                                    hoist[fieldName]?.value = item.value
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = item == selectedOption,
                            onClick = {
                                optionSelected(item)
                                hoist[fieldName]?.value = item.value
                            },
                            modifier = Modifier.padding(end = 7.dp)
                        )
                        Text(text = item.key, style = CaptionRegular)
                    }
                }
            }
        }
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf(Pair(fieldName, mutableStateOf(widgetDto.default ?: "")))
    }
}