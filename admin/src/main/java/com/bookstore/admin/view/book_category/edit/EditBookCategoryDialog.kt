package com.bookstore.admin.view.book_category.edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bookstore.admin.R
import com.bookstore.admin.constant.RetrofitStatus
import com.bookstore.admin.model.request.book.UpdateBookCategoryRequest
import com.bookstore.admin.model.response.book.BookCategory
import com.bookstore.admin.utils.ViewHelper.hide
import com.bookstore.admin.utils.ViewHelper.show
import com.bookstore.admin.view.main.MainViewModel
import com.bookstore.admin.view.main.fragment.book_category.adapter.BookCategoryItemListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_edit_book_category.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class EditBookCategoryDialog : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "EditBookCategoryDialog"

        fun createInstance(bookCategory: BookCategory, bookCategoryItemListener: BookCategoryItemListener? = null) : EditBookCategoryDialog =
            EditBookCategoryDialog().apply {
                this.bookCategory = bookCategory
                this.bookCategoryItemListener = bookCategoryItemListener
            }
    }

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val editBookCategoryDialogViewModel: EditBookCategoryDialogViewModel by viewModel()
    private lateinit var bookCategory: BookCategory
    private var bookCategoryItemListener: BookCategoryItemListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_edit_book_category, container, false)

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (this::bookCategory.isInitialized) {
            editBookCategoryDialogViewModel.updateBookCategoryResponse.observe(viewLifecycleOwner, Observer { response ->
                hideLoading()
                when(response.status) {
                    RetrofitStatus.SUCCESS -> {
                        response.bookCategory?.let {
                            bookCategoryItemListener?.onItemUpdate(it)
                        }
                        this.dismiss()
                        Toast.makeText(requireContext(),R.string.edit_book_category_success_msg,Toast.LENGTH_SHORT).show()
                        // tambah function swipe_refresh
                    }
                    RetrofitStatus.UNAUTHORIZED -> mainViewModel.logout(requireActivity())
                    else -> Toast.makeText(requireContext(),R.string.edit_book_category_error_msg,Toast.LENGTH_SHORT).show()
                }
            })
            editBookCategoryDialogViewModel.deleteBookCategoryResponse.observe(viewLifecycleOwner, Observer {response ->
                when(response.status) {
                    RetrofitStatus.SUCCESS -> {
                        response.bookCategory?.let {
                            bookCategoryItemListener?.onItemDelete(it)
                        }
                        this.dismiss()
                        Toast.makeText(requireContext(),R.string.delete_book_category_success_msg, Toast.LENGTH_SHORT).show()
                        // tambah juga swipe_refresh
                    }
                    RetrofitStatus.UNAUTHORIZED -> mainViewModel.logout(requireActivity())
                    RetrofitStatus.CONSTRAINT_DETECTED -> {
                        hideLoading()
                        Toast.makeText(requireContext(),R.string.cant_delete_book_category_msg,Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        hideLoading()
                        Toast.makeText(requireContext(),R.string.delete_book_category_error_msg,Toast.LENGTH_SHORT).show()
                    }
                }
            })
            input_book_category_name.editText?.setText(bookCategory.name)
            input_book_category_code.editText?.setText(bookCategory.code)
            button_cancel.setOnClickListener {
                this.dismiss()
            }
            button_save.setOnClickListener{
                val name = input_book_category_name.editText?.text.toString().trim().capitalize()
                val code = input_book_category_code.editText?.text.toString().toUpperCase()
                input_book_category_name.error = if (name.isEmpty()) "Please input a Book category name" else null
                input_book_category_code.error = if (code.isEmpty()) "Please input a book category code" else null
                if (name.isNotEmpty() && code.isNotEmpty()) {
                    showLoading()
                    val updateBookCategoryRequest = UpdateBookCategoryRequest (
                        id = bookCategory.id,
                        name = name,
                        code = code
                    )
                    editBookCategoryDialogViewModel.updateBookCategory(updateBookCategoryRequest)
                }
            }
            button_delete.setOnClickListener {
                showLoading()
                editBookCategoryDialogViewModel.deleteBookCategory(bookCategory.id)
            }
        } else throw IllegalArgumentException("BookCategory have'nt been set on EditBookCategoryDialog")
    }

    private fun showLoading() {
        button_delete.isEnabled = false
        button_cancel.isEnabled = false
        button_save.isEnabled = false
        loading.show()
    }

    private fun hideLoading() {
        button_delete.isEnabled = true
        button_cancel.isEnabled = true
        button_save.isEnabled = true
        loading.hide()
    }

}