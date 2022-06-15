package com.mitf.serverdrivenui.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.WidgetViewModel

class ImageWidget(private val widgetDto: WidgetDto) : ComposableWidget {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun compose(hoist: Map<String, MutableState<String>>) {
        LazyVerticalGrid(cells = GridCells.Fixed(2)) {

        }
    }

    override fun getHoist(): Map<String, MutableState<String>> {
        return mapOf()
    }
}