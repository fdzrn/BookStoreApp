package com.bookstore.admin.view.main.fragment.purchase_history.adapter

import com.bookstore.admin.model.response.transaction.Transaction

interface PurchaseHistoryItemListener {
    fun onItemClick(transaction: Transaction)
    fun onItemDraw(transaction: List<Transaction>)
    fun onItemSearch(empty: Boolean)
}