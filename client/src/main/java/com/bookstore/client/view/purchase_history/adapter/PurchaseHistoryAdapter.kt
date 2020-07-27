package com.bookstore.client.view.purchase_history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookstore.R
import com.bookstore.client.model.response.transaction.Transaction
import kotlinx.android.synthetic.main.item_list_purchase_history.view.*
import java.util.*

class PurchaseHistoryAdapter(private val itemListener: ItemListener) :
    RecyclerView.Adapter<PurchaseHistoryAdapter.ViewHolder>(), Filterable {

    private val originalPurchase = mutableListOf<Transaction>()
    private var modificationPurchase = listOf<Transaction>()

    fun setData(purchase: List<Transaction>) {
        this.originalPurchase.clear()
        this.originalPurchase.addAll(purchase)
        this.modificationPurchase = purchase
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): PurchaseHistoryAdapter.ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_purchase_history, parent, false)
    )

    override fun getItemCount(): Int = modificationPurchase.size

    override fun onBindViewHolder(holder: PurchaseHistoryAdapter.ViewHolder, position: Int) =
        holder.bind(modificationPurchase[position])

    override fun filterByName(invoiceNumber: String?) {
        modificationPurchase = originalPurchase
        if (!invoiceNumber.isNullOrEmpty()) modificationPurchase = originalPurchase.filter {
            it.invoiceNumber.trim().toLowerCase(Locale.getDefault())
                .contains(invoiceNumber.trim().toLowerCase(Locale.getDefault()))
        }
        itemListener.onItemSearch(modificationPurchase.isEmpty())
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(transaction: Transaction) {
            itemView.text_invoice_number.text = transaction.invoiceNumber.trim()
            itemView.text_invoice_date.text = transaction.createdTime.trim()
            itemView.card
        }
    }
}