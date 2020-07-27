package com.bookstore.client.model.formatted.transaction

import com.bookstore.client.constant.RetrofitStatus
import com.bookstore.client.model.response.transaction.Transaction

data class TransactionResponse (
    val status: RetrofitStatus = RetrofitStatus.UNKNOWN,
//    val transaction: List<Transaction> = emptyList()
    val transaction: List<Transaction>? = null
)