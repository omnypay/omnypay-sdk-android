package net.omnypay.sdk.exampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.omnypay.omnypaydlscan.DriversLicenseScanCallback;
import net.omnypay.omnypaydlscan.OmnyPayDLScanner;
import net.omnypay.omnypaydlscan.model.IdentityData;

/**
 * Created by MikiP on 03-01-2017.
 */

public class OmnyPayDlScanActivity extends AppCompatActivity implements View.OnClickListener{

    private Button scanButton;
    private TextView scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omnypay_dl);
        scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(this);
        scanResult = (TextView) findViewById(R.id.scanResult);
    }

    @Override
    public void onClick(View view) {

        OmnyPayDLScanner.getInstance().start(
                OmnyPayDlScanActivity.this, new DriversLicenseScanCallback() {
                    @Override
                    public void onDriversLicenseScan(IdentityData identityData) {
                        scanResult.setText(identityData.toString());
                    }
                });
    }

}
