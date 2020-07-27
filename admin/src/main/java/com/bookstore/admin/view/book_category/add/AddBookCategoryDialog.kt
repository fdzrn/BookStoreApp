package com.bookstore.admin.view.book_category.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bookstore.admin.R
import com.bookstore.admin.constant.RetrofitStatus
import com.bookstore.admin.model.request.book.AddBookCategoryRequest
import com.bookstore.admin.utils.ViewHelper.hide
import com.bookstore.admin.view.main.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_add_book_category.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AddBookCategoryDialog: BottomSheetDialogFragment() {
    companion object {
        const val TAG = "AddApplicationDialog"
    }

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val addCategoryDialog: AddBookCategoryDialogViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_add_book_category,container,false)

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCategoryDialog.addBookCategoryResponse.observe(viewLifecycleOwner, Observer { response ->
            when(response.status) {
                RetrofitStatus.SUCCESS -> {
                    this.dismiss()
                    Toast.makeText(requireContext(),"Successfully Add New Category Book",Toast.LENGTH_SHORT).show()
                }
                RetrofitStatus.UNAUTHORIZED -> mainViewModel.logout(requireActivity())
                else -> {
                    hideLoading()
                    Snackbar.make(parent_layout,"An error occurred while adding the category,please try again",Snackbar.LENGTH_LONG).show()
                }
            }
        })
        button_save.setOnClickListener {
            val bookName = input_book_category_name.editText?.text.toString().trim().capitalize()
            val bookCode = input_book_category_code.editText?.text.toString().trim().toUpperCase()
            input_book_category_name.error = if (bookName.isEmpty()) "Please enter the Name of the Book" else null
            input_book_category_code.error = if (bookCode.isEmpty()) "Please enter the Code of the Book" else null
            if (bookName.isNotEmpty() && bookCode.isNotEmpty()) {
                showLoading()
                val addBookCategoryRequest = AddBookCategoryRequest(name = bookName,code = bookCode)
                addCategoryDialog.addBookCategory(addBookCategoryRequest)
            }
        }
        button_cancel.setOnClickListener {
            this.dismiss()
        }
    }
    private fun showLoading() {
        button_cancel.isEnabled = false
        button_save.isEnabled = false
    }
    private fun hideLoading() {
        loading.hide()
        button_save.isEnabled = true
        button_cancel.isEnabled = true
    }
}