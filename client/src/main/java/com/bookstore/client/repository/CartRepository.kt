package com.bookstore.client.repository

import com.bookstore.client.dao.remote.RemoteCartDAO
import com.bookstore.client.utils.SessionHelper
import com.bookstore.client.utils.SessionHelper.asBearer
import com.bookstore.client.model.request.cart.CartRequest
import com.bookstore.client.model.response.cart.Cart
import okhttp3.ResponseBody
import retrofit2.Response

class CartRepository(private val userRepository: UserRepository, private val cartDAO: RemoteCartDAO) {

    suspend fun getCart(): Cart = userRepository.checkSession().let {
        if (it != null) return cartDAO.getCart(it.asBearer())
        else throw SessionHelper.unauthorizaedException
    }

    suspend fun addBookToCart(cartRequest: CartRequest) : Response<ResponseBody> =
        userRepository.checkSession().let {
            if (it != null) return cartDAO.addBookToCart(it.asBearer(), cartRequest)
            else throw SessionHelper.unauthorizaedException
        }

    suspend fun removeBookFromCart(detailId: Int) : Response<ResponseBody> =
        userRepository.checkSession().let {
            if (it != null) return cartDAO.removeBookFromCart(it.asBearer(), detailId)
            else throw SessionHelper.unauthorizaedException
        }
}