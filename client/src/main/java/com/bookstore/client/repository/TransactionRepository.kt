package com.bookstore.client.repository

import com.bookstore.client.dao.remote.RemoteTransactionDAO
import com.bookstore.client.utils.SessionHelper
import com.bookstore.client.utils.SessionHelper.asBearer
import com.bookstore.client.model.request.transaction.CheckoutRequest
import com.bookstore.model.request.transaction.PaymentRequest
import com.bookstore.model.response.transaction.Transaction

class TransactionRepository(private val userRepository: UserRepository,private val transactionDAO: RemoteTransactionDAO) {

    suspend fun performCheckout(checkoutRequest: CheckoutRequest) : Transaction =
        userRepository.checkSession().let {
            if (it != null) return transactionDAO.perfomeCheckout(it.asBearer(), checkoutRequest)
            else throw SessionHelper.unauthorizaedException
        }

    suspend fun performPayment(paymentRequest: PaymentRequest) : Transaction =
        userRepository.checkSession().let {
            if (it != null) return transactionDAO.performPayment(it.asBearer(), paymentRequest)
            else throw SessionHelper.unauthorizaedException
        }
}