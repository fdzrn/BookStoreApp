package com.bookstore.client.model.formatted.user

import com.bookstore.client.constant.RetrofitStatus

data class SignInResponse(
    val status: RetrofitStatus = RetrofitStatus.UNKNOWN
)