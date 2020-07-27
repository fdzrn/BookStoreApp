package com.bookstore.admin.view.main.fragment.book.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookstore.admin.R
import com.bookstore.admin.constant.BookStatus
import com.bookstore.admin.model.response.book.BookModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.item_list_book_admin.view.*
import java.util.*

class BookAdapter(private val bookItemListener: BookItemListener) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>(), BookFilterable {

    private val originalBooks = mutableListOf<BookModel>()
    private var booksAfterFilter = listOf<BookModel>()

    fun setData(books: List<BookModel>) {
        this.originalBooks.clear()
        this.originalBooks.addAll(books.filter { it.bookStatus == BookStatus.FOR_SALE.toString() })
        this.booksAfterFilter = books
        notifyDataSetChanged()
        bookItemListener.onItemDraw(originalBooks)
    }

    fun getData() = originalBooks

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_book_admin, parent,false))

    override fun getItemCount(): Int = booksAfterFilter.size

    override fun onBindViewHolder(holder: BookAdapter.ViewHolder, position: Int) = holder.bind(booksAfterFilter[position])

    override fun filterByName(bookName: String?) {
        booksAfterFilter = originalBooks
        if (!bookName.isNullOrEmpty()) booksAfterFilter = originalBooks.filter {
            it.title.trim().toLowerCase(Locale.getDefault()).contains(bookName.toLowerCase(Locale.getDefault()))
        }
        bookItemListener.onItemSearch(booksAfterFilter.isEmpty())
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("DefaultLocale")
        fun bind(book:BookModel) {
            Glide.with(itemView.context)
                .load(book.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .placeholder(R.color.colorShimmer)
                .error(R.color.colorShimmer)
                .into(itemView.image_cover)
            itemView.text_title_item_list.text = book.title.trim().capitalize()
            itemView.text_category_item_list.text = book.title.trim().capitalize()
            itemView.card_item_list_book.setOnClickListener {
                bookItemListener.onItemClick(book)
            }
        }

    }
}