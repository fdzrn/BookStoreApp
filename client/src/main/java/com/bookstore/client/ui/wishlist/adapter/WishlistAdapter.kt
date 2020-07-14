package com.bookstore.client.ui.wishlist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookstore.R
import com.bookstore.client.model.response.book.Book
import com.bookstore.client.constant.BookStatus
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.item_list_book.view.*
import kotlinx.android.synthetic.main.item_list_book.view.image_cover

class WishlistAdapter(private val wishlistItemListener: WishlistItemListener): RecyclerView.Adapter<WishlistAdapter.ViewHolder>(), WishlistFilterable {
    private val originalBooks = mutableListOf<Book>()
    private var books = listOf<Book>()

    fun setData(books: List<Book>) {
        this.originalBooks.clear()
        this.originalBooks.addAll(books.filter { it.bookStatus == BookStatus.FOR_SELL.toString() })
        this.books = originalBooks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistAdapter.ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_book,parent, false))


    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: WishlistAdapter.ViewHolder, position: Int) = holder.bind(books[position])


    @SuppressLint("DefaultLocale")
    override fun performFilterByName(bookName: String?) {
        books = originalBooks
        if (!bookName.isNullOrEmpty()) books = originalBooks.filter {
            it.title.trim().toLowerCase().contains(bookName.trim().toLowerCase())
        }
        wishlistItemListener.onItemSearch(books.isEmpty())
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
                wishlistItemListener.onItemClick(book)
            }
        }
    }
}