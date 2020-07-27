package com.bookstore.admin.view.book_category.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bookstore.admin.R
import com.bookstore.admin.model.response.book.BookCategory
import com.bookstore.admin.view.main.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class EditBookCategoryDialog : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "EditBookCategoryDialog"
    }

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val editBookCategoryDialogViewModel: EditBookCategoryDialogViewModel by viewModel()
    private lateinit var bookCategory: BookCategory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_edit_book_category, container, false)
}