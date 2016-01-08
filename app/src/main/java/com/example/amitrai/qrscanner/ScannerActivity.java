package com.example.amitrai.qrscanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by amit.rai on 9/4/2015.
 */
public class ScannerActivity extends ActionBarActivity implements ZBarScannerView.ResultHandler{

    private ZBarScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        String data = rawResult.getContents();
        if(data == null){
            mScannerView.startCamera();
            return;
        }

//        Toast.makeText(this, "Contents = " + rawResult.getContents() +
//                ", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();


        if(data!= null && !data.isEmpty()){
            Intent i = new Intent(ScannerActivity.this, WebActivity.class);
            i.putExtra(WebActivity.DATA,data);
            startActivity(i);
            finish();
        }
    }
}
