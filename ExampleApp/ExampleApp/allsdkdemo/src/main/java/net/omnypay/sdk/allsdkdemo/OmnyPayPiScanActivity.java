package net.omnypay.sdk.allsdkdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
    private TextView cardNumberValue, cardCvvValue, cardExpiryDateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omnypay_pi);
        scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(this);
        cardNumberValue = (TextView) findViewById(R.id.cardNumberValue);
        cardCvvValue = (TextView) findViewById(R.id.cardCvvValue);
        cardExpiryDateValue = (TextView) findViewById(R.id.cardExpiryDateValue);
    }

    @Override
    public void onClick(View view) {
        startPIScan();
    }

    private void startPIScan() {
        OmnyPayPIScanner.getInstance().start(OmnyPayPiScanActivity.this, new
                PaymentInstrumentScanCallback() {
                    @Override
                    public void onPaymentInstrumentScan(CardInformation cardInformation) {
                        if (cardInformation != null) {
                            cardNumberValue.setText(String.valueOf(cardInformation.getCardNumber()));
                            cardCvvValue.setText(String.valueOf(cardInformation.getCardCvvCode()));
                            cardExpiryDateValue.setText(String.valueOf(cardInformation.getCardExpiryDate()));
                        }

                    }
                }, true);
    }


}
