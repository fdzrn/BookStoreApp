package com.bookstore.admin.model.formated.transaction

import com.bookstore.admin.constant.RetrofitStatus
import com.bookstore.admin.model.response.transaction.Transaction

data class PurchaseListResponse(
    val status: RetrofitStatus = RetrofitStatus.UNKNOWN,
    val transactions: List<Transaction>? = null
)