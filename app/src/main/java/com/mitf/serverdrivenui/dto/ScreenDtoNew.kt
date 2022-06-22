package com.mitf.serverdrivenui.dto

import androidx.compose.runtime.MutableState
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.HashSet

@JsonClass(generateAdapter = true)
data class ScreenDtoNew(
//    val data: Data? = null,
    val data: Map<String, Any>? = null,
    val errors: Any? = null,
    val message: String? = null,
    val statusCode: Int? = null,
    val statusDesc: String? = null,
    val ui_components: List<UiComponents>? = null,
    val uiName: String? = null,
    val uiVersion: String? = null
)

@JsonClass(generateAdapter = true)
data class Data(
    val address: String? = null,
    val avatar: String? = null,
    val branchCode: String? = null,
    val email: String? = null,
    val employeeId: String? = null,
    val employeeName: String? = null,
    val firstName: String? = null,
    val id: Int? = null,
    val isActivition: Boolean? = null,
    val lastName: String? = null
)

@JsonClass(generateAdapter = true)
data class UiComponents(
    val attributes: List<String>? = null,
    val label: String? = null,
    val method: String? = null,
    val type: Type? = null,
    val ui_components: List<UiComponents>? = null,
    val url: String? = null,
    val options: List<Any>? = null,
    val slug: String? = null,
    val validation: List<String>? = null
)

//enum class Type(val type:String) {
//    TEXT("text"),
//    TEXT_ROW("row_widget"),
//    TEXT_ROW_FIELD("row_text_box"),
//    TITLE("text"),
//    TEXT_FIELD("text_box"),
//    TEXT_FIELD_SELECTOR("text_box_selector"),
//    TEXT_FIELD_CURRENCY("text_box_currency"),
//    CHECKBOX("check_box"),
//    FORM("form"),
//    RADIO("radio")
//}
enum class Type {
    HEADER,
    TEXT_BOX,
    TEXT_BOX_MULTILINE,
    TEXT_BOX_SELECTOR
}

