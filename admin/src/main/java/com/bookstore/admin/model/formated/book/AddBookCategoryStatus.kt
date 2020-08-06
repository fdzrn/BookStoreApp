package com.bookstore.admin.model.formated.book

import com.bookstore.admin.constant.RetrofitStatus
import com.bookstore.admin.model.response.book.BookCategory

data class AddBookCategoryResponse(
    val status : RetrofitStatus = RetrofitStatus.UNKNOWN,
    val bookCategory: BookCategory? = null
)