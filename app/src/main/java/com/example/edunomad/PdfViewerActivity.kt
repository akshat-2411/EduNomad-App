package com.example.edunomad

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class PdfViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)

        val pdfUrl = intent.getStringExtra("pdfUrl")
        val webView: WebView = findViewById(R.id.webView)

        // Enable JavaScript
        webView.settings.javaScriptEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.settings.domStorageEnabled = true

        // Ensure the URL loads inside the WebView (not external browser)
        webView.webViewClient = WebViewClient()

        // Google Docs Viewer URL
        val googleDocsViewerUrl = "https://docs.google.com/gview?embedded=true&url=$pdfUrl"

        // Load PDF in WebView
        googleDocsViewerUrl?.let {
            webView.loadUrl(it)
        }
    }
}
