package com.bookstore.client.model.formatted.transaction

import com.bookstore.client.constant.RetrofitStatus
import com.bookstore.client.model.response.transaction.Transaction

data class CheckoutResponse(
    val status: RetrofitStatus = RetrofitStatus.UNKNOWN,
    val transaction: Transaction? = null
)