package com.bookstore.admin.view.main.fragment.book_category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bookstore.admin.R
import com.bookstore.admin.constant.RetrofitStatus
import com.bookstore.admin.model.response.book.BookCategory
import com.bookstore.admin.utils.ViewHelper.hide
import com.bookstore.admin.utils.ViewHelper.hideKeyboard
import com.bookstore.admin.utils.ViewHelper.show
import com.bookstore.admin.view.book_category.add.AddBookCategoryDialog
import com.bookstore.admin.view.book_category.edit.EditBookCategoryDialog
import com.bookstore.admin.view.main.MainViewModel
import com.bookstore.admin.view.main.fragment.book_category.adapter.BookCategoryAdapter
import com.bookstore.admin.view.main.fragment.book_category.adapter.BookCategoryItemListener
import kotlinx.android.synthetic.main.fragment_book_category.swipe_refresh_layout
import kotlinx.android.synthetic.main.fragment_book_category.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class BookCategoryFragment : Fragment(), BookCategoryItemListener {
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val bookCategoryViewModel: BookCategoryViewModel by viewModel()
    private val bookCategoryAdapter by lazy {
        BookCategoryAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_book_category, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookCategoryViewModel.bookCategoryResponse.observe(viewLifecycleOwner, Observer {
            swipe_refresh_layout.isRefreshing = false
            loading.hide()
            when(it.status) {
                RetrofitStatus.SUCCESS -> it.list?.let { list ->
                    button_search.isEnabled = true
                    placeholder_empty.hide()
                    recyclerview.show()
                    bookCategoryAdapter.setData(list)
                }
                RetrofitStatus.UNAUTHORIZED -> mainViewModel.logout(requireActivity())
                else -> {
                    button_search.isEnabled = false
                    placeholder_empty.show()
                    recyclerview.hide()
                    layout_book_category_count.hide()
                }
            }
        })
        button_search.setOnClickListener {
            showSearchBar()
        }
        button_add.setOnClickListener {
            AddBookCategoryDialog()
                .show(requireActivity().supportFragmentManager, AddBookCategoryDialog.TAG)
        }
        button_clear_search.setOnClickListener {
            hideSearchBar()
            bookCategoryAdapter.filterByName(null)
        }
        input_search.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                bookCategoryAdapter.filterByName(input_search.text.toString())
                input_search.hideKeyboard()
            }
            true
        }
        swipe_refresh_layout.setOnClickListener{
            bookCategoryViewModel.getBookCategory()
        }
        recyclerview.apply {
            adapter = bookCategoryAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
            setHasFixedSize(true)
        }
        button_search.isEnabled = false
        hideSearchBar()
    }

    override fun onResume() {
        super.onResume()
        bookCategoryViewModel.getBookCategory()
    }

    override fun onItemSearch(empty: Boolean) {
        if (empty && !placeholder_empty.isShown) placeholder_empty.show()
        else placeholder_empty.hide()
    }

    override fun onItemDraw(bookCategory: List<BookCategory>) {
       when {
           input_search.isShown -> layout_book_category_count.hide()
           bookCategory.isEmpty() -> layout_book_category_count.hide()
           else -> {
               text_book_category_count.text = bookCategory.count().toString()
               layout_book_category_count.show()
           }
       }
    }

    override fun onItemClick(bookCategories: BookCategory) {
        EditBookCategoryDialog
    }

    private fun hideSearchBar() {
        placeholder_empty.hide()
        input_search.hide()
        button_clear_search.hide()
        text_title_fragment_book_category.show()
        button_search.show()
        button_add.show()
        input_search.apply {
            text.clear()
            hideKeyboard()
        }
        onItemDraw(bookCategoryAdapter.getData())

    }

    private fun showSearchBar() {
        text_title_fragment_book_category.hide()
        button_search.hide()
        button_add.hide()
        input_search.show()
        button_clear_search.show()
        input_search.show()
        onItemDraw(bookCategoryAdapter.getData())
    }
}