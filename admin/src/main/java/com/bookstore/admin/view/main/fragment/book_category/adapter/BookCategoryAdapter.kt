package com.bookstore.admin.view.main.fragment.book_category.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookstore.admin.R
import com.bookstore.admin.model.response.book.BookCategory
import kotlinx.android.synthetic.main.item_list_category_book.view.*
import java.util.*


class BookCategoryAdapter(private val bookCategoryItemListener: BookCategoryItemListener) :
    RecyclerView.Adapter<BookCategoryAdapter.ViewHolder>(), BookCategoryFilterable {

    private val originalBookCategory = mutableListOf<BookCategory>()
    private var bookCategories = listOf<BookCategory>()


    fun setData(bookCategoryList: List<BookCategory>) {
        this.originalBookCategory.clear()
        this.originalBookCategory.addAll(bookCategoryList)
        this.bookCategories = bookCategoryList
        notifyDataSetChanged()
        bookCategoryItemListener.onItemDraw(originalBookCategory)
    }

    fun getData() = originalBookCategory


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_category_book, parent, false)
        )

    override fun getItemCount(): Int = bookCategories.size

    override fun onBindViewHolder(holder:ViewHolder, position: Int) =
        holder.bind(bookCategories[position])

    override fun filterByName(bookCategoryName: String?) {
        bookCategories = originalBookCategory
        if (!bookCategoryName.isNullOrEmpty()) bookCategories = originalBookCategory.filter {
            it.name.trim().toLowerCase(Locale.getDefault())
                .contains(bookCategoryName.trim().toLowerCase(Locale.getDefault()))
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("DefaultLocale")
        fun bind(bookCategory: BookCategory) {
            itemView.text_name.text = bookCategory.name.trim().capitalize()
            itemView.card_item_list_category_book.setOnClickListener {
                bookCategoryItemListener.onItemClick(bookCategory)
            }
        }
    }
}