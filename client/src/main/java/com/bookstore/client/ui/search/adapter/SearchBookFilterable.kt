package com.bookstore.client.ui.search.adapter

import com.bookstore.client.constant.BookType

interface SearchBookFilterable {
    fun performFilterByName(bookName:String?, bookTypes: List<BookType>)
    fun performFilterByType(bookTypes: List<BookType>)
}