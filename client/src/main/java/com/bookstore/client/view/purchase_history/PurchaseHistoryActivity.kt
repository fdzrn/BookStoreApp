package com.bookstore.client.view.purchase_history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookstore.R
import com.bookstore.client.constant.RetrofitStatus
import com.bookstore.client.utils.ViewHelper.hide
import com.bookstore.client.utils.ViewHelper.hideKeyBoard
import com.bookstore.client.utils.ViewHelper.show
import com.bookstore.client.utils.ViewHelper.showKeyBoard
import com.bookstore.client.view.main.MainViewModel
import com.bookstore.client.view.purchase_history.adapter.ItemListener
import com.bookstore.client.view.purchase_history.adapter.PurchaseHistoryAdapter
import kotlinx.android.synthetic.main.activity_purchase_history.*
import org.koin.android.viewmodel.ext.android.viewModel

class PurchaseHistoryActivity : AppCompatActivity(),ItemListener {

    private val mainViewModel: MainViewModel by viewModel()
    private val purchaseHistoryViewModel: PurchaseHistoryViewModel by viewModel()
    private val purchaseHistoryAdapter by lazy { PurchaseHistoryAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_history)
        purchaseHistoryViewModel.purchaseHistory.observe(this, Observer {
            swipe_refresh_layout.isRefreshing = false
            loading.hide()
            when(it.status) {
                RetrofitStatus.SUCCESS -> it.transaction?.let { list ->
                    button_search.isEnabled = true
                    placeholder_empty_history.hide()
                    recyclerview.show()
                    purchaseHistoryAdapter.setData(list)
                }
                RetrofitStatus.UNAUTHORIZED -> mainViewModel.logout(this)
                else -> {
                    button_search.isEnabled = false
                    placeholder_empty_history.show()
                    recyclerview.hide()
                }
            }
        })
        button_back.setOnClickListener {
            super.onBackPressed()
        }
        button_search.setOnClickListener {
            showSearchBar()
        }
        button_clear_search.setOnClickListener {
            hideSearchBar()
            purchaseHistoryAdapter.filterByName(null)
        }
        input_search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                purchaseHistoryAdapter.filterByName(input_search.text.toString())
            }
            true
        }
        swipe_refresh_layout.setOnRefreshListener {
            purchaseHistoryViewModel.callPurchaseHistory()
        }
        recyclerview.apply {
            adapter = purchaseHistoryAdapter
            layoutManager = LinearLayoutManager(this@PurchaseHistoryActivity)
            setHasFixedSize(true)
        }
        button_search.isEnabled = false
        hideSearchBar()
    }

    override fun onResume() {
        super.onResume()
        if (input_search.text.toString().isEmpty()) purchaseHistoryViewModel.callPurchaseHistory()
    }

    override fun onItemSearch(empty: Boolean) {
        if (empty && !placeholder_empty_history.isShown) placeholder_empty_search.show()
        else placeholder_empty_search.hide()
    }

    private fun showSearchBar() {
        text_title_activity_purchase_history.hide()
        button_search.hide()
        input_search.show()
        button_clear_search.show()
        input_search.showKeyBoard()
    }

    private fun hideSearchBar() {
        placeholder_empty_history.hide()
        input_search.hide()
        button_clear_search.hide()
        text_title_activity_purchase_history.show()
        button_search.show()
        input_search.apply {
            text.clear()
            hideKeyBoard()
        }
    }
}