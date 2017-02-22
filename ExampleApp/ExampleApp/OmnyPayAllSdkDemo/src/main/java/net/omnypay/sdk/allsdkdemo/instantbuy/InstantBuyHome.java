package net.omnypay.sdk.allsdkdemo.instantbuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.omnypay.scan.OmnyPayScan;
import net.omnypay.scan.ScannedResultCallback;
import net.omnypay.sdk.allsdkdemo.R;

import java.io.IOException;

public class InstantBuyHome extends AppCompatActivity implements ScannedResultCallback {

    TextView tvScanResult;
    Button btnOpenWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_buy_home);

        tvScanResult = (TextView) findViewById(R.id.scan_result);
        btnOpenWeb = (Button) findViewById(R.id.btn_launch_webview);
        btnOpenWeb.setEnabled(false);

    }

    public void launchScanner(View view) {
        try {
            OmnyPayScan.getInstance().start(this, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScanResult(String s) {
        tvScanResult.setText(s);
        btnOpenWeb.setEnabled(true);
    }

    public void launchWebActivity(View view) {
        Intent htmlIntent = new Intent(this, InstantBuyWebActivity.class);
        htmlIntent.putExtra("scan_result", tvScanResult.getText().toString());
        startActivity(htmlIntent);
    }
}
