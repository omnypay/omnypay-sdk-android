package net.omnypay.sdk.allsdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by MikiP on 03-01-2017.
 */

public class InitializeOtherSDKSActivity extends AppCompatActivity implements View.OnClickListener {

    private Button initializeOmnypayPIButton;
    private Button initializeOmnypayDLButton;
    private Button initializeOmnypayAUTHButton;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_sdks);
        initializeVariables();
    }

    private void initializeVariables() {
        initializeOmnypayPIButton = (Button) findViewById(R.id.initializeOmnypayPIButton);
        initializeOmnypayDLButton = (Button) findViewById(R.id.initializeOmnypayDLButton);
        initializeOmnypayAUTHButton = (Button) findViewById(R.id.initializeOmnypayAUTHButton);
        initializeOmnypayPIButton.setOnClickListener(this);
        initializeOmnypayDLButton.setOnClickListener(this);
        initializeOmnypayAUTHButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.initializeOmnypayPIButton:
                Intent intent = new Intent(this, OmnyPayPiScanActivity.class);
                startActivity(intent);
                break;

            case R.id.initializeOmnypayDLButton:
                intent = new Intent(this, OmnyPayDlScanActivity.class);
                startActivity(intent);
                break;

            case R.id.initializeOmnypayAUTHButton:
                intent = new Intent(this, OmnyPayAuthScanActivity.class);
                startActivity(intent);
                break;
        }

    }
}
