package com.bookstore.client.view.purchase_history.adapter

interface Filterable {
    fun filterByName(invoiceNumber: String?)
}