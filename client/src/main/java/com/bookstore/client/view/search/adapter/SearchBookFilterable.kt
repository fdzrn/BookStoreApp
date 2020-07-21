package com.bookstore.client.view.search.adapter

import com.bookstore.client.constant.BookType

interface SearchBookFilterable {
    fun performFilterByName(bookName:String?, bookTypes: List<BookType>)
    fun performFilterByType(bookTypes: List<BookType>)
}