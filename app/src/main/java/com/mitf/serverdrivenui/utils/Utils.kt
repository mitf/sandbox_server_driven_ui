package com.mitf.serverdrivenui.utils

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
