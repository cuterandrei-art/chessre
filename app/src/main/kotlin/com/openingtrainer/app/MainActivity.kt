package com.openingtrainer.app

import android.os.Build
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)

        // Configure WebView
        configureWebView(webView)

        // Load the bundled HTML file from assets
        webView.loadUrl("file:///android_asset/www/index.html")
    }

    private fun configureWebView(webView: WebView) {
        with(webView.settings) {
            // Enable JavaScript
            javaScriptEnabled = true

            // Enable DOM storage
            domStorageEnabled = true

            // Enable local storage
            databaseEnabled = true

            // Set user agent
            userAgentString = userAgentString?.let {
                it.replace(Regex("Android \\d+"), "Android ${Build.VERSION.RELEASE}")
            }

            // Allow mixed content (if needed for online resources)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }

            // Optimize for offline-first PWA
            cacheMode = android.webkit.WebSettings.LOAD_DEFAULT
        }

        // Enable dark mode support if available
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            WebSettingsCompat.setForceDark(webView.settings, WebSettingsCompat.FORCE_DARK_AUTO)
        }

        // Set WebView clients
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }

        webView.webChromeClient = WebChromeClient()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
