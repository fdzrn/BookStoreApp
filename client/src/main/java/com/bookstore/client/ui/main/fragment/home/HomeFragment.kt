package com.bookstore.client.ui.main.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookstore.R
import com.bookstore.client.ui.search.SearchBookActivity
import com.bookstore.client.constant.BookType
import com.bookstore.client.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_bar.setOnClickListener { startActivity(Intent(requireContext(), SearchBookActivity::class.java)) }
        button_settings.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }
        button_category_fiction.setOnClickListener{
            openSearchActivityByType(BookType.FICTION)
        }
        button_category_art.setOnClickListener {
            openSearchActivityByType(BookType.ART)
        }
        button_category_history.setOnClickListener {
            openSearchActivityByType(BookType.HISTORY)
        }
        button_category_horror.setOnClickListener {
            openSearchActivityByType(BookType.HORROR)
        }
        button_category_non_fiction.setOnClickListener {
            openSearchActivityByType(BookType.NON_FICTION)
        }
        button_category_psychology.setOnClickListener {
            openSearchActivityByType(BookType.PSYCHOLOGY)
        }
        button_category_romance.setOnClickListener {
            openSearchActivityByType(BookType.ROMANCE)
        }
        button_category_science.setOnClickListener {
            openSearchActivityByType(BookType.SCIENCE)
        }
    }

    private fun openSearchActivityByType(filter : BookType) {
        Intent(requireContext(),SearchBookActivity:: class.java).run {
            this.putParcelableArrayListExtra(SearchBookActivity.FILTER_BY_TYPE, arrayListOf(filter))
            startActivity(this)
        }
    }
}