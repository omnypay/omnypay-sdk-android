package net.omnypay.sdk.exampleapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import net.omnypay.sdk.core.model.AuthenticatedSession;
import net.omnypay.sdk.exampleapp.model.Authentication;
import net.omnypay.sdk.exampleapp.model.AuthenticationRequestParam;
import net.omnypay.sdk.exampleapp.network.HttpOps;
import net.omnypay.sdk.exampleapp.network.Listener;
import net.omnypay.sdk.exampleapp.utils.Constants;
import net.omnypay.sdk.wrapper.OmnyPayAPI;
import net.omnypay.sdk.wrapper.OmnyPayCallback;
import net.omnypay.sdk.wrapper.OmnyPayError;

/**
 * This class initializes example app with all the prerequisites for e.g. Merchant Id and User account
 * required for the app.
 */
public class InitializeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button initializeButton;
    private Button proceedButton;
    private Authentication authentication;
    private Gson gson;
    private ProgressDialog progressDialog;

    /**
     * Set merchantId, username and password before proceeding any further
     */
    private String merchantId = null ;
    private String username = null ;
    private String password = null;
    private String accountAuthenticationUrl = "http://pantheon.demo0.omnypay" +
            ".net:8080/identity/authentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize);
        initializeVariables();

    }

    private void initializeVariables() {
        gson = new Gson();
        progressDialog = new ProgressDialog(this);
        initializeButton = (Button) findViewById(R.id.initializeButton);
        proceedButton = (Button) findViewById(R.id.proceedButton);
        proceedButton.setOnClickListener(this);
        initializeButton.setOnClickListener(this);
    }

    /**
     * The initializeSDK method initializes merchantId. If merchantId exists in OmnyPay system,
     * then user is created and authenticated. If not, error is returned back. Replace
     * merchantId with the your merchant_id.
     */
    private void initializeSDK() {
        progressDialog.setMessage("Initializing SDK ");
        progressDialog.show();
        OmnyPayAPI.initialize(InitializeActivity.this, merchantId, null, new
                OmnyPayCallback<Void>() {
                    @Override
                    public void onResult(Void aVoid) {
                        authenticateShopper();
                    }

                    @Override
                    public void onFailure(final OmnyPayError omnyPayError) {
                        if (merchantId == null ) {
                            showToast("Merchant ID is not set correctly");

                        } else {
                            showToast(omnyPayError.getMessage());
                        }
                    }
                });
    }


    /**
     * Use your identity service to authenticate the shopper. This method shows how to use OmnyPay's
     * identity service for shopper authentication.
     * <p></p>
     * After authentication pass on the auth-token and shopper id to OmnyPay service.
     */
    private void authenticateShopper() {
        final AuthenticationRequestParam authenticationRequestParam = new
                AuthenticationRequestParam();
        authenticationRequestParam.setUsername(username);
        authenticationRequestParam.setPassword(password);
        authenticationRequestParam.setMerchantId(merchantId);
        HttpOps.doPost(accountAuthenticationUrl, gson.toJson
                (authenticationRequestParam), new Listener() {
            @Override
            public void onResult(String response) {

                authentication = gson.fromJson(response, Authentication.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.cancel();
                        proceedButton.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onError(OmnyPayError omnyPayError) {

                if (authenticationRequestParam.getMerchantId() == null || authenticationRequestParam
                        .getUsername() == null || authenticationRequestParam.getPassword() == null) {
                    showToast("Authentication Parameters are not set correctly");

                } else {
                    showToast(omnyPayError.getMessage());
                }

            }
        });
    }

    /**
     * This method shows how a user is authenticated with current session by passing your
     * user/shopper id and auth token. authenticateShopper is the method defined is OmnyPayAPI
     * which is responsible for authenticating a shopper with OmnyPay service.
     */
    private void authenticateShopperWithCurrentSession() {
        progressDialog.setMessage(" Authenticating Shopper ");
        progressDialog.show();
        OmnyPayAPI.authenticateShopper(authentication.getMerchantShopperId(), authentication
                .getMerchantAuthToken(), new OmnyPayCallback<AuthenticatedSession>() {
            @Override
            public void onResult(AuthenticatedSession authenticatedSession) {
                progressDialog.cancel();
                moveToAddCardActivity();
            }

            @Override
            public void onFailure(OmnyPayError omnypayError) {
                showToast(omnypayError.getMessage());
            }
        });
    }

    /**
     * moveToAddCardActivity is the method used for moving to next activity which displays all the
     * cards configured with the user.
     */
    private void moveToAddCardActivity() {
        Intent intent = new Intent(this, DisplayCardsActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.initializeButton:
                initializeSDK();
                break;

            case R.id.proceedButton:
                authenticateShopperWithCurrentSession();
                break;


        }
    }

    /**
     * The displayToast method is used for displaying Toast messages in the app for every
     * erroneous call.
     *
     * @param omnypayError
     */

    private void showToast(final String omnypayError) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();
                Toast.makeText(InitializeActivity.this, omnypayError, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
