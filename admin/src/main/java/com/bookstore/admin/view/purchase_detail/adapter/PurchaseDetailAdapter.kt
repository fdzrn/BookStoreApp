package com.bookstore.admin.view.purchase_detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookstore.admin.R
import com.bookstore.admin.model.response.transaction.TransactionDetail
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.item_list_book.view.*

class PurchaseDetailAdapter(
    private val transactionDetails: List<TransactionDetail>,
    private val purchaseDetailListener: PurchaseDetailListener
) : RecyclerView.Adapter<PurchaseDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_book, parent, false))

    override fun getItemCount(): Int = transactionDetails.size

    override fun onBindViewHolder(holder: PurchaseDetailAdapter.ViewHolder, position: Int) = holder.bind(transactionDetails[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("DefaultLocale")
        fun bind(transactionDetail: TransactionDetail) {
            Glide.with(itemView.context)
                .load(transactionDetail.bookModel.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .placeholder(R.color.colorShimmer)
                .error(R.color.colorShimmer)
                .into(itemView.image_cover)
            itemView.text_name.text = transactionDetail.bookModel.title.trim().capitalize()
            itemView.text_category.text = transactionDetail.bookModel.bookCategory.name.trim().capitalize()
            itemView.card_item_list_book.setOnClickListener { purchaseDetailListener.onItemClick(transactionDetail) }
        }
    }
}