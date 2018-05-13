package com.example.kevpreneur.smartshopper;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {
    IntentIntegrator integrator = new IntentIntegrator(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scan);
        scannerInstance();
    }

    protected void scannerInstance(){
        integrator.setPrompt("Scan product barcode to shop");
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(CaptureActivityPortrait.class);
        integrator.initiateScan();
    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                showDialogFragment(result.getContents());
//                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showDialogFragment(String barcode) {
        FragmentManager fm = getFragmentManager();
        Bundle args = new Bundle();
        ProductDetails dialogFragment = new ProductDetails();
        args.putString("barcode", barcode);
        dialogFragment.setArguments(args);

        dialogFragment.show(fm, "Item Details Fragment");
    }
}
