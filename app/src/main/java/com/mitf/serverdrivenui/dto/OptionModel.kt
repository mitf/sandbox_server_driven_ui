package com.mitf.serverdrivenui.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OptionModel(
    val id: String,
    val value: String
)