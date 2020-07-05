package com.bookstore.client.repository

import com.bookstore.client.dao.remote.RemoteBookDAO
import com.bookstore.client.utils.SessionHelper
import com.bookstore.client.utils.SessionHelper.asBearer
import com.bookstore.model.request.book.FavouriteBookRequest
import com.bookstore.client.model.response.book.Book
import com.bookstore.model.response.book.FavouriteBook
import okhttp3.ResponseBody
import retrofit2.Response

class BookRepository(private val userRepository: UserRepository, private val bookDAO: RemoteBookDAO) {
    suspend fun getBook() : List<Book> = userRepository.checkSession().let {
        if (it != null) return bookDAO.getBook(it.asBearer())
        else throw SessionHelper.unauthorizaedException
    }

    suspend fun getFavouriteBook() : FavouriteBook = userRepository.checkSession().let {
        if (it!=null) return bookDAO.getFavouriteBook(it.asBearer())
        else throw SessionHelper.unauthorizaedException
    }

    suspend fun addBookToFavourite(favouriteBookRequest: FavouriteBookRequest) : Response<ResponseBody> =
        userRepository.checkSession().let {
            if (it != null) return bookDAO.addBookToFavourite(it.asBearer(), favouriteBookRequest)
            else throw SessionHelper.unauthorizaedException
        }

    suspend fun removeBookFromFavourite(detailId: Int) : Response<ResponseBody> =
        userRepository.checkSession().let {
            if (it != null) return bookDAO.removeBookFromFavourite(it.asBearer(), detailId)
            else throw SessionHelper.unauthorizaedException
        }

}