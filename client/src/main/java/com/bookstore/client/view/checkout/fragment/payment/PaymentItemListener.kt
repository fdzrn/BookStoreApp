package com.bookstore.client.view.checkout.fragment.payment

import com.bookstore.model.response.transaction.TransactionDetail

interface PaymentItemListener {
    fun onItemClick(transactionDetail: TransactionDetail)
}