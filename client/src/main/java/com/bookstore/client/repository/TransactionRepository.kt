package com.bookstore.client.repository

import com.bookstore.client.dao.remote.RemoteTransactionDAO
import com.bookstore.client.utils.SessionHelper
import com.bookstore.client.utils.SessionHelper.asBearer
import com.bookstore.client.model.request.transaction.CheckoutRequest
import com.bookstore.client.model.request.transaction.PaymentRequest
import com.bookstore.client.model.response.transaction.Transaction
import com.bookstore.client.model.response.transaction.TransactionDetail

class TransactionRepository(private val userRepository: UserRepository,private val transactionDAO: RemoteTransactionDAO) {

    suspend fun performCheckout(checkoutRequest: CheckoutRequest) : Transaction =
        userRepository.checkSession().let {
            if (it != null) return transactionDAO.performCheckout(it.asBearer(), checkoutRequest)
            else throw SessionHelper.unauthorizaedException
        }

    suspend fun performPayment(paymentRequest: PaymentRequest) : Transaction =
        userRepository.checkSession().let {
            if (it != null) return transactionDAO.performPayment(it.asBearer(), paymentRequest)
            else throw SessionHelper.unauthorizaedException
        }

    suspend fun getPurchaseHistory() : List<Transaction> = userRepository.checkSession().let {
        if(it != null) return transactionDAO.getCheckoutHistory(it.asBearer())
        else throw SessionHelper.unauthorizaedException
    }
}