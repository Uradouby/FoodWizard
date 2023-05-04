package com.example.foodwizard.Price

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.foodwizard.databinding.ActivityWebviewBinding

class WebviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.webViewClient = WebViewClient()
        val url = intent.getStringExtra("url")
        if (url != null) {
            binding.webView.loadUrl(url)
        }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)
    }
}