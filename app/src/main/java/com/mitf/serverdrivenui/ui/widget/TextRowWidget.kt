package com.mitf.serverdrivenui.ui.widget

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mitf.serverdrivenui.dto.WidgetDto
import com.mitf.serverdrivenui.ui.ComposableWidget
import com.mitf.serverdrivenui.ui.getComposableWidget

class TextRowWidget {

}
//class TextRowWidget(
//    val widgetDto: WidgetDto
//) : ComposableWidget {
//
//    @Composable
//    override fun compose(hoist: Map<String, MutableState<String>>) {
//        val childElements = widgetDto.children?.map { it.getComposableWidget() } ?: listOf()
//        val children = childElements.map { Pair(it, it.getHoist()) }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//        ) {
//            children.map {
//                it.first.compose(it.second)
//            }
//        }
//    }
//
//    override fun getHoist(): Map<String, MutableState<String>> {
//        return mapOf()
//    }
//}