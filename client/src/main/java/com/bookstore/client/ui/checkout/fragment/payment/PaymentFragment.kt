package com.bookstore.client.ui.checkout.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookstore.R
import com.bookstore.client.ui.checkout.CheckoutViewModel
import com.bookstore.client.ui.checkout.fragment.payment.adapter.PaymentAdapter
import com.bookstore.model.response.transaction.Transaction
import com.bookstore.model.response.transaction.TransactionDetail
import org.koin.android.viewmodel.ext.android.sharedViewModel

class PaymentFragment: Fragment(), PaymentItemListener {

    private val checkoutViewModel: CheckoutViewModel by sharedViewModel()
    private lateinit var transaction: Transaction
    private val paymentAdapter by lazy {
        PaymentAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_payment, container, false)
    override fun onItemClick(transactionDetail: TransactionDetail) {
        TODO("Not yet implemented")
    }
}