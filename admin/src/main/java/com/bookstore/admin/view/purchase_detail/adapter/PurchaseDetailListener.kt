package com.bookstore.admin.view.purchase_detail.adapter

import com.bookstore.admin.model.response.transaction.TransactionDetail

interface PurchaseDetailListener {
    fun onItemClick(detailTransaction: TransactionDetail)
}