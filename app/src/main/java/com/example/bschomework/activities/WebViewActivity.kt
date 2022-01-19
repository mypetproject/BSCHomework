package com.example.bschomework.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.bschomework.R
import com.example.bschomework.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityWebViewBinding>(this, R.layout.activity_web_view)
            .run {
                toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }

                webview.run {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    loadUrl(URL)
                }
            }
    }

    companion object {
        private const val URL = "https://en.wikipedia.org/wiki/Main_Page"
    }
}