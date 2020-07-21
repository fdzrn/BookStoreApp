package com.bookstore.admin.repository

import com.bookstore.admin.dao.remote.RemoteCartDAO
import com.bookstore.admin.model.request.cart.CartRequest
import com.bookstore.admin.model.response.cart.Cart
import com.bookstore.admin.utils.SessionHelper
import com.bookstore.admin.utils.SessionHelper.asBearer
import okhttp3.ResponseBody
import retrofit2.Response

class CartRepository(
    private val userRepository: UserRepository,
    private val remoteCartDAO: RemoteCartDAO
) {
    suspend fun getCart(): Cart = userRepository.checkAccessToken().let {
        if (it != null) return remoteCartDAO.getCart(it.asBearer())
        else throw SessionHelper.unauthorizedException
    }
    suspend fun addBookToCart(cartRequest: CartRequest) : Response<ResponseBody> = userRepository.checkAccessToken().let {
        if (it != null) return remoteCartDAO.addBookToCart(it.asBearer(), cartRequest)
        else throw SessionHelper.unauthorizedException
    }
    suspend fun removeBookFromCart(detailId: Int): Response<ResponseBody> = userRepository.checkAccessToken().let {
        if (it != null) return remoteCartDAO.removeBookFromCart(it.asBearer(), detailId)
        else throw SessionHelper.unauthorizedException
    }
}