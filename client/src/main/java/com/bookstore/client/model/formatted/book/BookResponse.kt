package com.bookstore.client.model.formatted.book

import com.bookstore.client.constant.RetrofitStatus
import com.bookstore.client.model.response.book.Book

data class BookResponse(
    val status: RetrofitStatus = RetrofitStatus.UNKNOWN,
    val list: List<Book>? = null
)