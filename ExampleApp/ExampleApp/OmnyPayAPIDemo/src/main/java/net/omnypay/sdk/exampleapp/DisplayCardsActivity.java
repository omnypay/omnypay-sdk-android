/**
 * Copyright 2016 OmnyPay Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.omnypay.sdk.exampleapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import net.omnypay.scan.OmnyPayScan;
import net.omnypay.scan.ScannedResultCallback;
import net.omnypay.sdk.core.model.Basket;
import net.omnypay.sdk.core.model.MerchantPoS;
import net.omnypay.sdk.core.model.PaymentInstrumentOffers;
import net.omnypay.sdk.exampleapp.adapters.PaymentCardAdapter;
import net.omnypay.sdk.exampleapp.utils.Constants;
import net.omnypay.sdk.wrapper.OmnyPayAPI;
import net.omnypay.sdk.wrapper.OmnyPayCallback;
import net.omnypay.sdk.wrapper.OmnyPayError;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * This class has a template where all the cards are displayed if user has any, or
 * user is provided with an option to add the card manually.
 */
public class DisplayCardsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addButton;
    private Button proceedButton;
    private ListView cardListView;
    private View emptyView;
    private String paymentInstrumentId;
    private PaymentCardAdapter cardAdapter;
    private ProgressDialog progressDialog;
    private ArrayList<PaymentInstrumentOffers> paymentInstrumentArrayList;

    private OmnyPayAPI omnyPayApi = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_instrument);
        initializeVariables();
    }

    private void initializeVariables() {
        addButton = (Button) findViewById(R.id.add_button);
        proceedButton = (Button) findViewById(R.id.proceedButton);
        emptyView = findViewById(R.id.empty_view);
        paymentInstrumentArrayList = new ArrayList<>();
        cardListView = (ListView) findViewById(R.id.card_list_view);
        cardAdapter = new PaymentCardAdapter(DisplayCardsActivity.this, paymentInstrumentArrayList);
        cardListView.setAdapter(cardAdapter);
        cardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                proceedButton.setEnabled(true);
                paymentInstrumentId = paymentInstrumentArrayList.get(position)
                        .getPaymentInstrumentId();
            }
        });
        addButton.setOnClickListener(this);
        proceedButton.setOnClickListener(this);
        proceedButton.setEnabled(false);
        progressDialog = new ProgressDialog(this);

        omnyPayApi = new OmnyPayAPI(DisplayCardsActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        proceedButton.setEnabled(false);
        getAllPaymentInstruments();

    }

    /**
     * OmnyPaySDK has a method getPaymentInstrument which can be used to get all the payment
     * instruments (credit / debit cards) which  has been added for a user account.
     * getAllPaymentInstruments methods retrieves all the cards configured. In this sample,
     * the 1st card is selected by default and payment is processed.
     * <p>
     * Payment instrument can be selected by selecting other available payment instruments.
     */
    private void getAllPaymentInstruments() {
        omnyPayApi.getPaymentInstrument(new OmnyPayCallback<List<PaymentInstrumentOffers>>() {
            @Override
            public void onResult(final List<PaymentInstrumentOffers> paymentInstrumentOffers) {
                // Payment instruments received, display it in UI
                if (paymentInstrumentOffers.size() == 0) {
                    showEmptyView();
                } else {
                    showCards(paymentInstrumentOffers);

                }
            }


            private void showEmptyView() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cardListView.setVisibility(View.GONE);
                        emptyView.setVisibility(VISIBLE);


                    }
                });
            }

            private void showCards(final List<PaymentInstrumentOffers> paymentInstrumentOffers) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        emptyView.setVisibility(View.GONE);
                        cardListView.setVisibility(VISIBLE);
                        paymentInstrumentArrayList.clear();
                        paymentInstrumentArrayList.addAll(paymentInstrumentOffers);
                        cardAdapter.setResourceList(paymentInstrumentArrayList);
                        paymentInstrumentId = paymentInstrumentArrayList.get(0).getPaymentInstrumentId();
                    }
                });
            }

            @Override
            public void onFailure(OmnyPayError omnyPayError) {
                showToast(omnyPayError.getMessage());
            }
        });
    }


    /**
     * Every OmnyPay transaction should have a basket object. The basket object is used to store
     * information about the transaction such as association with the retailer’s point of sale,
     * line items or products purchased, associated offers, loyalty points. createBasket method
     * here calls createBasket api and creates a basket. Basket is returned on successful
     * completion.
     */
    private void createBasket() {
        progressDialog.setMessage("Creating Basket");
        omnyPayApi.createBasket(new OmnyPayCallback<Basket>() {
            @Override
            public void onResult(Basket basket) {
                progressDialog.cancel();
                // Basket created successfully, Associating with Point of sale terminal now
                scanQRCode();
            }

            @Override
            public void onFailure(OmnyPayError omnyPayError) {
                showToast(omnyPayError.getMessage());
            }
        });
    }

    /**
     * Scan the QRCode for the Point of Sale using OmnyPayScan SDK.
     */
    private void scanQRCode() {
        try {
            OmnyPayScan.getInstance().start(DisplayCardsActivity.this, new ScannedResultCallback() {
                @Override
                public void onScanResult(String posString) {
                    //Extract the POS id from QR code scan. This is merchant side validation of POS.
                    String merchantPOSID = validatePOSScanAndGetPOSid(posString);

                    // POS Id retrieved, checking in basket now
                    checkIn(merchantPOSID);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method validates the string returned from QRCode Scan and returns the merchant POS ID
     * . This is merchant specific QR logic and validation. A sample implementation is shown here.
     * <p>
     * posString is a combination of 00P, MerchantPOSID, SHA256(00PMerchantPOSID). QRCode is
     * generated as 00P;MerchantPOSID;SHA256(00PMerchantPOSID)
     *
     * @param posString Scan result of POS terminal QR code
     * @return POS id of terminal after validation
     */
    private String validatePOSScanAndGetPOSid(String posString) {
        if (posString == null || posString.trim().length() == 0) {
            return null;
        }
        String returnValue = null;
        StringBuilder stringBuilder = new StringBuilder();
        String[] blocks = posString.split(";");
        if (blocks.length == 3 && blocks[0].equalsIgnoreCase("00P")) {
            stringBuilder.append(blocks[0]).append(blocks[1]);
            String sha256Digest = getSha256(stringBuilder.toString());
            if (sha256Digest.equalsIgnoreCase(blocks[2])) {
                returnValue = blocks[1];
            }
        }
        return returnValue;
    }

    private static String getSha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * OmnyPaySDK has a method checkIn which associates a basket with the POS Terminal.
     */
    private void checkIn(String merchantPOSID) {
        progressDialog.setMessage("Checking in");
        progressDialog.show();
        omnyPayApi.checkIn(merchantPOSID, new OmnyPayCallback<MerchantPoS>() {
            @Override
            public void onResult(MerchantPoS merchantPoS) {
                progressDialog.cancel();
                // Check in successful, displaying current cart now
                Intent intent = new Intent(DisplayCardsActivity.this, CartActivity.class);
                intent.putExtra(Constants.PAYMENT_INSTRUMENT_ID_KEY, paymentInstrumentId);
                startActivity(intent);
            }

            @Override
            public void onFailure(OmnyPayError omnyPayError) {
                showToast(omnyPayError.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.add_button:
                Intent intent = new Intent(this, AddCardActivity.class);
                startActivity(intent);
                break;

            case R.id.proceedButton:
                createBasket();
                break;


        }
    }

    /**
     * The displayToast method is used for displaying Toast messages in the app for every erroneous call.
     *
     * @param omnypayError
     */
    private void showToast(final String omnypayError) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();
                Toast.makeText(DisplayCardsActivity.this, omnypayError, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
