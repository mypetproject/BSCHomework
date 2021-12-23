package com.example.bschomework.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.bschomework.R
import com.example.bschomework.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        DataBindingUtil.setContentView<ActivityAboutBinding>(this, R.layout.activity_about).run {
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }
}