package com.bookstore.client.ui.main.fragment.book

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bookstore.R.layout.fragment_book
import com.bookstore.client.ui.main.MainViewModel
import com.bookstore.client.ui.main.fragment.book.adapter.BookAdapter
import com.bookstore.client.ui.main.fragment.book.adapter.BookItemListener
import com.bookstore.client.ui.search.SearchBookActivity
import com.bookstore.client.utils.ViewHelper.hide
import com.bookstore.client.utils.ViewHelper.show
import com.bookstore.client.constant.RetrofitStatus
import com.bookstore.client.model.response.book.Book
import com.bookstore.client.ui.book.DetailBookActivity
import kotlinx.android.synthetic.main.fragment_book.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class BookFragment: Fragment(), BookItemListener {
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val bookViewModel: BookViewModel by viewModel()
    private val bookAdapter by lazy {
        BookAdapter(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(fragment_book, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookViewModel.bookResponse.observe(viewLifecycleOwner, Observer {
            swipe_refresh_layout.isRefreshing = false
            loading.hide()
            when(it.status) {
                RetrofitStatus.SUCCESS -> it.list?.let { list ->
                    placeholder_empty.hide()
                    recyclerview.show()
                    bookAdapter.setData(list)
                }
                RetrofitStatus.UNAUTHORIZED -> mainViewModel.logout(requireActivity())
                else -> {
                    recyclerview.hide()
                    placeholder_empty.show()
                }
            }
        })
        button_search.setOnClickListener{
            startActivity(Intent(requireContext(), SearchBookActivity::class.java))
        }
        swipe_refresh_layout.setOnRefreshListener {
            bookViewModel.getBook()
        }
        recyclerview.apply {
            adapter = bookAdapter
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }
    }

    override fun onItemClick(book: Book) {
        val intent = Intent(requireContext(), DetailBookActivity::class.java)
        startActivity(intent.putExtra(DetailBookActivity.DATA, book))
    }

    override fun onResume() {
        super.onResume()
        bookViewModel.getBook()
    }
}