package com.bookstore.admin.model.formated.book

import com.bookstore.admin.constant.RetrofitStatus

data class BookCategoryCountResponse(
    val status: RetrofitStatus = RetrofitStatus.UNKNOWN,
    val bookCategoryCount: Int = 0
)