package net.omnypay.sdk.allsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import net.omnypay.omnypaypiscan.OmnyPayPIScanner;
import net.omnypay.omnypaypiscan.PaymentInstrumentScanCallback;
import net.omnypay.omnypaypiscan.model.CardInformation;


/**
 * Created by MikiP on 03-01-2017.
 */

public class OmnyPayPiScanActivity extends AppCompatActivity implements View.OnClickListener {
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
        OmnyPayPIScanner.getInstance().start(OmnyPayPiScanActivity.this, new
                PaymentInstrumentScanCallback() {
                    @Override
                    public void onPaymentInstrumentScan(CardInformation cardInformation) {
                        scanResult.setText(cardInformation.toString());
                    }
                });

    }

}
