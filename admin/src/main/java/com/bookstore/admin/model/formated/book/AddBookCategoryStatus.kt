package com.bookstore.admin.model.formated.book

import com.bookstore.admin.constant.RetrofitStatus

data class AddBookCategoryStatus(
    val status : RetrofitStatus = RetrofitStatus.UNKNOWN
)