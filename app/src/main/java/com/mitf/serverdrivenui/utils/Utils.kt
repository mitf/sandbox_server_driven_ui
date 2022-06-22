package com.mitf.serverdrivenui.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.mitf.serverdrivenui.dto.WidgetDto
import java.text.NumberFormat
import java.util.*

fun List<String>?.findString(find: String) = if (!this.isNullOrEmpty()) {
    this.find { it.contains(find) }.toString()
} else {
    ""
}

fun List<String>?.findSubStringAfter(find: String) = if (!this.isNullOrEmpty()) {
    this.findString(find).substringAfter(":")
} else {
    ""
}

fun Any?.toIntOrZero(): Int =
    (this ?: "").toString().replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0

fun Any?.toLongOrZero(): Long =
    (this ?: "").toString().replace(Regex("[^0-9]"), "").toLongOrNull() ?: 0

// in Extension
fun getValueString(fieldId: String, widgetDto: WidgetDto): Pair<String, MutableState<String>> =
    Pair(fieldId, mutableStateOf(widgetDto.default ?: ""))

fun EditText.setCurrencyInputType() = this.addTextChangedListener(object : TextWatcher {
    var lastText = ""
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    override fun afterTextChanged(s: Editable?) {
        if (s.toString() != lastText) {
            removeTextChangedListener(this)
            val cleanString = s.toString().replace(Regex("[^0-9]"), "")
            val parsed = cleanString.toBigDecimalOrNull() ?: 0.toBigDecimal()
            val formatted = NumberFormat.getCurrencyInstance(Locale("id", "id")).format((parsed))
                .removeSuffix(",00")
            lastText = formatted
            setText(formatted)
            setSelection(formatted.length)
            addTextChangedListener(this)
        }
    }
})
