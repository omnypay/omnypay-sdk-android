package net.omnypay.sdk.exampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class OmnyPayAuthScanActivity extends AppCompatActivity implements View.OnClickListener{

    private Button scanButton;
    private TextView scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omnypay_auth);
        scanButton = (Button) findViewById(R.id.scanButton);
        scanResult = (TextView) findViewById(R.id.scanResult);
    }

    @Override
    public void onClick(View view) {

    }
}
