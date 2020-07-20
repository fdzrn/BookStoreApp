package com.bookstore.admin.model.formated.book

import com.bookstore.admin.constant.RetrofitStatus
import com.bookstore.admin.model.response.book.BookModel

data class AddBookResponse (
    val status: RetrofitStatus = RetrofitStatus.UNKNOWN,
    val addedBook: BookModel? = null
)