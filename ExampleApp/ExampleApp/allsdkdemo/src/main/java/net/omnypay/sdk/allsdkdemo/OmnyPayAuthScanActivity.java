package net.omnypay.sdk.allsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.omnypay.omnypayauth.OmnyPayAuth;
import net.omnypay.omnypayauth.Authentication;



public class OmnyPayAuthScanActivity extends AppCompatActivity implements View.OnClickListener{

    private Button authenticateButton;
    private TextView authenticationResult;
    private String password="1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omnypay_auth);
        authenticateButton = (Button) findViewById(R.id.authenticateButton);
        authenticateButton.setOnClickListener(this);
        authenticationResult = (TextView) findViewById(R.id.authenticationResult);
    }

    @Override
    public void onClick(View view) {
        OmnyPayAuth.getInstance().start(OmnyPayAuthScanActivity.this,
                password, "Title", new Authentication() {
                    @Override
                    public void authenticationComplete(boolean isAuthenticatedSuccessfully) {
                        if(isAuthenticatedSuccessfully){
                            // show success dialog
                            authenticationResult.setText("authenticated successfully");
                        } else {
                            // Authentication failed, use some other authentication method
                            authenticationResult.setText("authentication failed");
                        }
                    }
                });

    }
}
