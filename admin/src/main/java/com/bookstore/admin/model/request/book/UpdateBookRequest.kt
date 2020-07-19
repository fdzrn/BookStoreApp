package com.bookstore.admin.model.request.book

import com.bookstore.admin.constant.BookStatus
import org.threeten.bp.LocalDateTime


data class UpdateBookRequest(
    val authorName: String,
    val bookCategoryId: Int,
    val bookStatus: String = BookStatus.FOR_SALE.toString(),
    val id: Int,
    val isbn: String = "null",
    val price: Int,
    val publicationDate: String = LocalDateTime.now().toString(),
    val synopsis: String,
    val title: String
)