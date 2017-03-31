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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.omnypay.sdk.core.model.Basket;
import net.omnypay.sdk.core.model.BasketPaymentConfirmation;
import net.omnypay.sdk.core.model.BasketReceipt;
import net.omnypay.sdk.core.model.ProductOffer;
import net.omnypay.sdk.core.model.ReconciledTotal;
import net.omnypay.sdk.core.model.SkuOffer;
import net.omnypay.sdk.exampleapp.adapters.BasketAdapter;
import net.omnypay.sdk.exampleapp.utils.Constants;
import net.omnypay.sdk.wrapper.OmnyPayAPI;
import net.omnypay.sdk.wrapper.OmnyPayCallback;
import net.omnypay.sdk.wrapper.OmnyPayError;

import java.util.List;

import static android.view.View.INVISIBLE;

/**
 * This class fetches and displays all the line items or products along with the product offers.
 */
public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    private Button payButton;
    private View emptyView;
    private RecyclerView basketRecyclerView;
    private BasketAdapter basketCartAdapter;
    private String paymentInstrumentId;
    private ProgressDialog progressDialog;

    private OmnyPayAPI omnyPayApi = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initializeVariables();
    }

    private void initializeVariables() {
        payButton = (Button) findViewById(R.id.pay_button);
        emptyView = (View) findViewById(R.id.empty_view);
        basketRecyclerView = (RecyclerView) findViewById(R.id.basket_recycler_view);
        basketCartAdapter = new BasketAdapter(null, null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        basketRecyclerView.setLayoutManager(linearLayoutManager);
        basketRecyclerView.setAdapter(basketCartAdapter);
        payButton.setOnClickListener(this);
        paymentInstrumentId = getIntent().getExtras().getString(Constants.PAYMENT_INSTRUMENT_ID_KEY);
        progressDialog=new ProgressDialog(this);

        omnyPayApi = new OmnyPayAPI(CartActivity.this);
    }

    /**
     * displays the updated basket in UI
     *
     * @param basket basket to be displayed
     */
    private void displayUpdatedBasket(final Basket basket) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setDiscountedCentsForProduct(basket.getOffers(), basket.getReconciledTotal());
                basketCartAdapter.setResourceList(basket.getItems(), basket.getOffers());
                if (basket.getItems().size() == 0) {
                    basketRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    payButton.setEnabled(false);
                } else {
                    basketRecyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);

                }
            }
        });
    }

    /**
     * Sets the corresponding discounted cents for every product offers
     *
     * @param skuOffers           List of product offers received from OmnyPay service
     * @param reconciledTotals List of reconciled total received from OmnyPay service
     */
    private void setDiscountedCentsForProduct(List<SkuOffer> skuOffers, List<ReconciledTotal>
            reconciledTotals) {

        for (SkuOffer skuOffer : skuOffers) {
            for (ReconciledTotal reconciledTotal : reconciledTotals)
                if (skuOffer.getSku().equals(reconciledTotal.getSku())) {
                    skuOffer.setDiscountCents(reconciledTotal.getDiscountCents());
                }

        }
    }

    /**
     * Broadcast receiver for listening to OmnyPay service updates
     */
    BroadcastReceiver basketUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                // Payment is completed. Display Payment Receipt now.
                case OmnyPayAPI.ACTION_BASKET_RECEIPT_UPDATE:
                    final BasketReceipt basketReceipt = (BasketReceipt) intent.getSerializableExtra(OmnyPayAPI.BROADCAST_DATA);
                    setDiscountedCentsForProduct(basketReceipt.getOffers(), basketReceipt
                            .getReconciledTotal());
                    progressDialog.cancel();
                    Intent receiptClass = new Intent(CartActivity.this, ReceiptActivity.class);
                    receiptClass.putExtra(Constants.TRANSACTION_SUMMARY, basketReceipt);
                    startActivity(receiptClass);
                    break;

                //Payment is cancelled by Point Of Sale. Navigating back to  Home Screen.
                case OmnyPayAPI.ACTION_TRANSACTION_CANCELLED:
                    Intent homeIntent = new Intent(CartActivity.this, InitializeActivity.class);
                    startActivity(homeIntent);
                    break;

                //Basket is updated. Updating UI.
                case OmnyPayAPI.ACTION_UPDATE_BASKET:
                    final Basket updatedBasket = (Basket) intent.getSerializableExtra(OmnyPayAPI.BROADCAST_DATA);
                    if (updatedBasket.getState().equals(Basket.StateEnum.COMPLETE_SCAN)) {
                        payButton.setEnabled(true);
                    } else {
                        displayUpdatedBasket(updatedBasket);
                    }
                    break;

                //Offers received. Save it or Display it as per choice.
                case OmnyPayAPI.ACTION_POST_PURCHASE_OFFER:
                    break;
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        /**
         * Registering local broadcast listener to listen to basket updates.
         */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(OmnyPayAPI.ACTION_BASKET_RECEIPT_UPDATE);
        intentFilter.addAction(OmnyPayAPI.ACTION_TRANSACTION_CANCELLED);
        intentFilter.addAction(OmnyPayAPI.ACTION_UPDATE_BASKET);
        intentFilter.addAction(OmnyPayAPI.ACTION_POST_PURCHASE_OFFER);

        /**
         * Local Broadcast initiated and registered.
         */
        LocalBroadcastManager.getInstance(CartActivity.this).registerReceiver
                (basketUpdateReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(CartActivity.this).unregisterReceiver(basketUpdateReceiver);
    }

    @Override
    public void onClick(View view) {
        progressDialog.setMessage("Initiating Payment");
        progressDialog.show();

        /**
         * OmnyPaySDK.startPayment is used for initiating the Payment. The payment is done using
         * selected payment instrument. In this sample app,  if no payment instrument is
         * selected, then payment is done using the first payment instrument added.
         */
        omnyPayApi.startPayment(paymentInstrumentId, new OmnyPayCallback<BasketPaymentConfirmation>() {
            @Override
            public void onResult(BasketPaymentConfirmation basketPaymentConfirmation) {
                // Payment initiation successful, waiting for payment completion by Point of Sale
                // terminal.
                // Show meaningful UI to user from here
            }

            @Override
            public void onFailure(OmnyPayError omnyPayError) {
                showToast(omnyPayError.getMessage());
            }
        });

    }

    /**
     * The displayToast method is used for displaying Toast messages in the app for every erroneous call.
     *
     * @param omnypayError Error thrown from OmnyPaySDK
     */
    private void showToast(final String omnypayError) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();
                Toast.makeText(CartActivity.this, omnypayError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
