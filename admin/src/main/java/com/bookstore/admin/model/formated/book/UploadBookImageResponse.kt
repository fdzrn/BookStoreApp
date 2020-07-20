package com.bookstore.admin.model.formated.book

import com.bookstore.admin.constant.RetrofitStatus

data class UploadBookImageResponse(
    val status: RetrofitStatus = RetrofitStatus.UNKNOWN
)