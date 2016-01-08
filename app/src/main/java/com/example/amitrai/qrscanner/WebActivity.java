package com.example.amitrai.qrscanner;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by amit.rai on 9/4/2015.
 */
public class WebActivity extends ActionBarActivity {

    public static final String DATA = null;
    private String url = null;
    public static final String TAG = WebActivity.class.getName();

    private WebView webview = null;
    private ProgressBar progressbar = null;
    private String SPEECH_URL = "http://speechtrans.com/closed-captions/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        if (getIntent().hasExtra(DATA)) {
            url = getIntent().getExtras().getString(DATA);
        }
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * initializing view elemtnts.
     */
    private void init() {
        webview = (WebView) findViewById(R.id.webview);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        progressbar.setVisibility(View.VISIBLE);


        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);

//        String ua = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
//        settings.setUserAgentString(ua);


        if (url != null) {
            List<String > urls = extractLinks(url);
            if (urls.size()<1) {
                String customHtml = "<html><body>" + url + "</body></html>";
                webview.loadData(customHtml, "text/html", "UTF-8");
                Log.e(TAG, customHtml);
            } else {
                SPEECH_URL = urls.get(0);
                Log.e(TAG, SPEECH_URL);
                if(!SPEECH_URL.contains("http")){
                    SPEECH_URL = "http:\\"+SPEECH_URL;
                }
                webview.loadUrl(SPEECH_URL);
            }

        } else {

            Log.e(TAG, SPEECH_URL);
            webview.loadUrl(SPEECH_URL);
        }


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                WebActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressbar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    public static List<String> extractLinks(String text) {
        List<String> links = new ArrayList<String>();
        Matcher m = Patterns.WEB_URL.matcher(text);
        while (m.find()) {
            String url = m.group();
            Log.e(TAG, "URL extracted: " + url);
            links.add(url);
        }

        return links;
    }
}
