package com.bookstore.client.view.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookstore.R
import com.bookstore.client.constant.BookType
import com.bookstore.client.model.response.book.Book
import com.bookstore.client.constant.BookStatus
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.activity_book_detail.view.*
import kotlinx.android.synthetic.main.item_list_cart.view.*
import kotlinx.android.synthetic.main.item_list_cart.view.image_cover

class SearchBookAdapter(private val searchBookItemListener: SearchBookItemListener) : RecyclerView.Adapter<SearchBookAdapter.ViewHolder>(), SearchBookFilterable {
    private val originBooks = mutableListOf<Book>()
    private var books = listOf<Book>()

    fun setData(books: List<Book>) {
        this.originBooks.clear()
        this.originBooks.addAll(books.filter { it.bookStatus == BookStatus.FOR_SELL.toString() })
        this.books = originBooks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_list_book, parent, false)
    )

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(books[position])

    @SuppressLint("DefaultLocale")
    override fun performFilterByName(bookName: String?, bookTypes: List<BookType>) {
        books = originBooks
        if (bookTypes.isNotEmpty()) books = originBooks.filter { originBooks ->
            originBooks.bookCategoryId == bookTypes.singleOrNull { bookType ->
                bookType.id == originBooks.bookCategoryId
            }?.id
        }
        if (!bookName.isNullOrEmpty()) books = books.filter {
            it.title.trim().toLowerCase().contains(bookName.trim().toLowerCase())
        }
        books.sortedBy { it.id }
        searchBookItemListener.onItemSearch(books.isEmpty())
        notifyDataSetChanged()
    }

    override fun performFilterByType(bookTypes: List<BookType>) {
        books = originBooks
        if (bookTypes.isNotEmpty()) books = originBooks.filter { originalBooks ->
            originalBooks.bookCategoryId == bookTypes.singleOrNull { bookType -> bookType.id == originalBooks.bookCategoryId }?.id
        }
        books.sortedBy { it.id }
        searchBookItemListener.onItemSearch(books.isEmpty())
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("DefaultLocale")
        fun bind(book:Book) {
            Glide.with(itemView.context)
                .load(book.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .placeholder(R.color.colorShimmer)
                .error(R.color.colorShimmer)
                .into(itemView.image_cover)
            itemView.text_name.text = book.title.trim().capitalize()
            itemView.text_category.text = book.bookCategory.name.trim().capitalize()
            itemView.card.setOnClickListener {
                searchBookItemListener.onItemClick(book)
            }
        }
    }
}
