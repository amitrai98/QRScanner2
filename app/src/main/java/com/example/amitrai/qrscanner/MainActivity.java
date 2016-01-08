package com.example.amitrai.qrscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private Boolean exit = false;

    private ProgressBar progressBar = null;
    private LinearLayout layout_buttons = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        layout_buttons = (LinearLayout) findViewById(R.id.layout_buttons);
        progressBar.setVisibility(View.GONE);
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
     * opens the mobile site viewing activity
     */
    public void openSite(View v){
//        layout_buttons.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);
        startActivity(new Intent(this, WebActivity.class));
    }

    /**
     * opens the qr activity
     */
    public void openQr(View v){
        layout_buttons.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        startActivity(new Intent(this, ScannerActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        layout_buttons.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {

        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }
}
