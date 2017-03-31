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

package net.omnypay.sdk.allsdkdemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import net.omnypay.sdk.core.model.PaymentInstrumentInfo;
import net.omnypay.sdk.core.model.ProvisionCardParam;
import net.omnypay.sdk.wrapper.OmnyPayAPI;
import net.omnypay.sdk.wrapper.OmnyPayCallback;
import net.omnypay.sdk.wrapper.OmnyPayError;

/**
 * The AddCardActivity class has a template where user is provided with an option to add the card
 * manually using 'add' button.
 */

public class AddCardActivity extends AppCompatActivity implements View.OnClickListener
        , AdapterView.OnItemSelectedListener {

    private EditText cardNumber;
    private EditText cardAlias;
    private Spinner cardType;
    private EditText cardIssuer;
    private EditText cardExpiryDate;
    private EditText cardHolderZip;
    private EditText cardHolderName;
    private Button okButton;
    private int selection = 0;
    private ProgressDialog progressDialog;

    private OmnyPayAPI omnyPayAPI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        initializeVariables();
    }

    private void initializeVariables() {
        cardNumber = (EditText) findViewById(R.id.card_number_edit);
        cardAlias = (EditText) findViewById(R.id.card_alias_edit);
        cardType = (Spinner) findViewById(R.id.card_type_spinner);
        ArrayAdapter<CharSequence> cardTypeAdapter = ArrayAdapter.createFromResource(this, R.array.card_type_array, android.R.layout.simple_spinner_item);
        cardTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardType.setAdapter(cardTypeAdapter);
        cardType.setSelection(selection);
        cardIssuer = (EditText) findViewById(R.id.card_issuer);
        cardExpiryDate = (EditText) findViewById(R.id.card_expiry_date);
        cardHolderZip = (EditText) findViewById(R.id.card_holder_zip);
        cardHolderName = (EditText) findViewById(R.id.card_holder_name);
        okButton = (Button) findViewById(R.id.add_card);
        okButton.setOnClickListener(this);
        cardType.setOnItemSelectedListener(this);
        progressDialog = new ProgressDialog(this);

        omnyPayAPI = new OmnyPayAPI(AddCardActivity.this);
    }

    private void validations() {
        if (cardNumber.getText().toString().length() == 0)
            cardNumber.setError("Card Number is required!");
        if (cardAlias.getText().toString().length() == 0)
            cardAlias.setError("Card Alias is required!");
        if (cardIssuer.getText().toString().length() == 0)
            cardIssuer.setError("Card Issuer is required!");
        if (cardExpiryDate.getText().toString().length() == 0)
            cardExpiryDate.setError("Card Expiry Date is required!");
        if (cardHolderZip.getText().toString().length() == 0)
            cardHolderZip.setError("Card Holder Zip is required!");
        if (cardHolderName.getText().toString().length() == 0)
            cardHolderName.setError("Card Holder Name is required!");
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selection = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.add_card:
                progressDialog.setMessage("Adding Payment Instrument");
                progressDialog.show();
                ProvisionCardParam provisionCardParam = new ProvisionCardParam();
                provisionCardParam.setCardNumber(cardNumber.getText().toString());
                provisionCardParam.setCardAlias(cardAlias.getText().toString());
                provisionCardParam.setCardType(getCardType(selection));
                provisionCardParam.setCardIssuer(cardIssuer.getText().toString());
                provisionCardParam.setCardExpiryDate(cardExpiryDate.getText().toString());
                provisionCardParam.setCardPin("");
                provisionCardParam.setCardHolderZip(cardHolderZip.getText().toString());
                provisionCardParam.setCardHolderName(cardHolderName.getText().toString());
                validations();
                /**
                 * OmnyPaySDK has a method provisionPaymentInstrument which can be used for provisioning of cards.
                 * Cards can be of the following types: credit, debit, charge-card, gift-card. Depending on the vault
                 * configuration requested during merchant onboarding, OmnyPay SDK will connect to its own vault or
                 * a third party vault including that of a retailer.
                 */
                omnyPayAPI.provisionPaymentInstrument(provisionCardParam, new
                        OmnyPayCallback<PaymentInstrumentInfo>() {
                    @Override
                    public void onResult(PaymentInstrumentInfo paymentInstrumentInfo) {
                        progressDialog.cancel();
                        finish();
                    }

                    @Override
                    public void onFailure(OmnyPayError omnyPayError) {
                        showToast(omnyPayError.getMessage());
                    }
                });
                break;

        }

    }

    /**
     * Returns card type selected
     */
    private ProvisionCardParam.CardTypeEnum getCardType(int selection) {
        if (selection == 0) {
            return ProvisionCardParam.CardTypeEnum.DEBIT;
        } else if (selection == 1) {
            return ProvisionCardParam.CardTypeEnum.CREDIT;
        } else {
            return ProvisionCardParam.CardTypeEnum.CHARGE_CARD;
        }
    }


    /**
     * The displayToast method is used for displaying Toast messages in the app for every erroneous call.
     */
    private void showToast(final String omnypayError) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();
                Toast.makeText(AddCardActivity.this, omnypayError, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
