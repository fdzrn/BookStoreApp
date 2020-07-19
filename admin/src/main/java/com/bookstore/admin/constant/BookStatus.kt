package com.bookstore.admin.constant

import java.util.*

enum class BookStatus{
    FOR_SALE,OUT_OF_STOCK,HIDE;

    fun toFormattedString() =
        super.toString().replace("_", " ").toUpperCase(Locale.getDefault()).trim()
}