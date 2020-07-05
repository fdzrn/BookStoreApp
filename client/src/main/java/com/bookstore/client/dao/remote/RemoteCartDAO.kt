package com.bookstore.client.dao.remote

import com.bookstore.client.config.AppConfig
import com.bookstore.client.model.request.cart.CartRequest
import com.bookstore.client.model.response.cart.Cart
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RemoteCartDAO {
    @GET("/api/rest/cart/findByUserId/${AppConfig.OAUTH_DEFAULT_CUSTOMER_ID}")
    suspend fun getCart(@Header("Authorization") authorization: String): Cart

    @POST("/api/rest/cart/saveOrUpdate")
    suspend fun addBookToCart(
        @Header("Authorization") authorization: String,
        @Body cartRequest: CartRequest
    ): Response<ResponseBody>

    @DELETE("/api/rest/cart/deleteByCartDetailId/{detaild_id}")
    suspend fun removeBookFromCart(
        @Header("Authorization") authorization: String,
        @Path("detail_id") detailId: Int
    ): Response<ResponseBody>
}