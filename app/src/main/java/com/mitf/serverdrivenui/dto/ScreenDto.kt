package com.mitf.serverdrivenui.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScreenDto (
    val children: List<WidgetDto>? = null
)

@JsonClass(generateAdapter = true)
data class WidgetDto (
    val children: List<WidgetDto>? = null,
    val label: String? = null,
    val sublabel: String? = null,
    val viewtype: ViewType? = null,
    val default: String? = null,
    val data: String? = null
)

enum class ViewType {
    TEXT,
    TITLE,
    TEXT_FIELD,
    CHECKBOX,
    FORM,
    LOGIN_FORM,
    MAINTENANCE
}
