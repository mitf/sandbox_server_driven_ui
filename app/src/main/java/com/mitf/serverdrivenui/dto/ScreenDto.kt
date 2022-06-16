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
    val validation: List<String>? = null,
    val sublabel: String? = null,
    val viewtype: ViewType? = null,
    val default: String? = null,
    val data: String? = null,
    val isEnable: Boolean? = true,
    val classType: List<String>? = null,
    val options: List<ItemOptions>? = null,
    val placeholder: String? = null
)

@JsonClass(generateAdapter = true)
data class ItemOptions(
    val key: String,
    val value: String
)

enum class ViewType {
    TEXT,
    TEXT_ROW,
    TEXT_ROW_FIELD,
    TITLE,
    TEXT_FIELD,
    TEXT_FIELD_SELECTOR,
    TEXT_FIELD_CURRENCY,
    TEXT_FIELD_SEPARATE,
    CHECKBOX,
    FORM,
    LOGIN_FORM,
    RADIO,
    MAINTENANCE
}
