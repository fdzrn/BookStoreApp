package com.bookstore.admin.model.formated.user

import com.bookstore.admin.constant.RetrofitStatus

data class SignInResponse(
    val status: RetrofitStatus = RetrofitStatus.UNKNOWN
)