package com.atharvakale.facerecognition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.atharvakale.facerecognition.databinding.ActivityMainBinding;

public class webview extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Set WebViewClient to handle page navigation and load URLs within the WebView
        webView.setWebViewClient(new MyWebViewClient());

        // Load a web page into the WebView
//        webView.loadUrl("https://github.com/aldysetiaa");
        // Tambahkan interface untuk berkomunikasi dengan JavaScript
        webView.addJavascriptInterface(new WebAppInterface(), "AndroidFunction");

        // Load HTML ke dalam WebView
        webView.loadUrl("file:///android_asset/getLoc.html");
    }


    // Interface untuk berkomunikasi dari JavaScript ke Java
    public class WebAppInterface {
        @JavascriptInterface
        public void sendCoordinates(float x, float y) {
            // Gunakan koordinat yang diterima dari JavaScript
            // Lakukan apa pun yang Anda butuhkan dengan koordinat tersebut
            // Misalnya, tampilkan dalam log
            Log.d("Coordinate", "X: " + x + ", Y: " + y);
        }
    }
    // Override the back button behavior to navigate within the WebView's history
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
