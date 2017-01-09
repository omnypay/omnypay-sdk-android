package net.omnypay.sdk.allsdkdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

public class OmnyPayDlScanActivity extends AppCompatActivity implements View.OnClickListener{

    private Button scanButton;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private static Snackbar snackbar;
    private TextView firstNameValue, lastNameValue, postalCodeValue;
    private  String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA,};

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ) {
            requestCameraPermissions(scanButton);
        } else {
            //TODO some method
            startDLScan();
        }

    }

    private void startDLScan() {
        OmnyPayDLScanner.getInstance().start(
                OmnyPayDlScanActivity.this, new DriversLicenseScanCallback() {
                    @Override
                    public void onDriversLicenseScan(IdentityData identityData) {
                        firstNameValue.setText(String.valueOf(identityData.getFirstName()));
                        lastNameValue.setText(String.valueOf(identityData.getLastName()));
                        postalCodeValue.setText(String.valueOf(identityData.getPostCode()));
                    }
                });
    }


    private  void requestCameraPermissions(View randomView) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            snackbar = Snackbar.make(randomView, R.string.permission_camera_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.decide, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat
                                    .requestPermissions(OmnyPayDlScanActivity.this,
                                            PERMISSIONS_CAMERA,
                                            MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    });

            snackbar.show();
        } else {
            ActivityCompat.requestPermissions(OmnyPayDlScanActivity.this, PERMISSIONS_CAMERA,
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    

    @Override
    public  void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {


            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


//                snackbar = Snackbar.make(scanButton, R.string.permission_granted, Snackbar.LENGTH_SHORT);
//                snackbar.show();
                startDLScan();

            } else {
                snackbar = Snackbar.make(scanButton, R.string.permission_not_granted, Snackbar.LENGTH_SHORT);
                snackbar.show();

            }
        }

    }

}
