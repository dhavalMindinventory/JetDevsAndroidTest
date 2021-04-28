package com.mi.imaginatoprac.common.extension

fun String?.checkNotEmpty(): Boolean {
    return this != null && isNotEmpty() && isNotBlank()
}