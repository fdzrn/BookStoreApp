package com.bookstore.admin.model.formated.transaction

import com.bookstore.admin.constant.RetrofitStatus

data class PurchaseCountResponse(
    val status: RetrofitStatus = RetrofitStatus.UNKNOWN,
    val transactionCount: Int = 0
)