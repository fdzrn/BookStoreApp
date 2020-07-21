package com.bookstore.client.view.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.bookstore.R
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initializeSettingPreview()
        button_back.setOnClickListener {
            super.onBackPressed()
        }
        row_setting_language.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    private fun initializeSettingPreview() {
        row_setting_language_value.text = Locale.getDefault().displayLanguage
    }
}