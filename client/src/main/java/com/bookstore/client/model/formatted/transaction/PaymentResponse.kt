package com.bookstore.client.model.formatted.transaction

import com.bookstore.client.constant.RetrofitStatus
import com.bookstore.model.response.transaction.Transaction

data class PaymentResponse(
    val status: RetrofitStatus = RetrofitStatus.UNKNOWN,
    val transaction: Transaction? = null
)