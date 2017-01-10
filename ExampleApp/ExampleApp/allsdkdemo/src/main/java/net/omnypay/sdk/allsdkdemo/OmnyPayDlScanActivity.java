package net.omnypay.sdk.allsdkdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.omnypay.omnypaydlscan.DriversLicenseScanCallback;
import net.omnypay.omnypaydlscan.OmnyPayDLScanner;
import net.omnypay.omnypaydlscan.model.IdentityData;

/**
 * Created by MikiP on 03-01-2017.
 */

public class OmnyPayDlScanActivity extends AppCompatActivity implements View.OnClickListener {

    private Button scanButton;
    private TextView firstNameValue, lastNameValue, postalCodeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omnypay_dl);
        scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(this);
        firstNameValue = (TextView) findViewById(R.id.firstNameValue);
        lastNameValue = (TextView) findViewById(R.id.lastNameValue);
        postalCodeValue = (TextView) findViewById(R.id.postalCodeValue);
    }

    @Override
    public void onClick(View view) {
        startDLScan();
    }

    private void startDLScan() {
        OmnyPayDLScanner.getInstance().start(
                OmnyPayDlScanActivity.this, new DriversLicenseScanCallback() {
                    @Override
                    public void onDriversLicenseScan(IdentityData identityData) {
                        if (identityData != null) {
                            firstNameValue.setText(String.valueOf(identityData.getFirstName()));
                            lastNameValue.setText(String.valueOf(identityData.getLastName()));
                            postalCodeValue.setText(String.valueOf(identityData.getPostCode()));
                        }

                    }
                }, true);
    }

}
