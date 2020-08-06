package com.bookstore.admin.view.main.fragment.purchase_history.adapter

interface PurchaseHistoryFilterable {
    fun filterByName(invoiceNumber: String?)
}