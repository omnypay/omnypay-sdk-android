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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.omnypay.sdk.core.model.BasketReceipt;
import net.omnypay.sdk.core.model.Item;
import net.omnypay.sdk.core.model.ReconciledTotal;
import net.omnypay.sdk.core.model.TotalSummary;
import net.omnypay.sdk.allsdkdemo.adapters.BasketCartAdapter;
import net.omnypay.sdk.allsdkdemo.utils.Constants;

import java.util.List;

/**
 * The ReceiptActivity class fetches the payment receipt for the current transaction.
 */
public class ReceiptActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView subtotal, taxes, discount, totalPaid;
    private RecyclerView receiptItemsRecyclerView;
    private BasketCartAdapter receiptCartAdapter;
    private BasketReceipt basketReceipt;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        initializeVariables();

    }

    private void initializeVariables() {
        subtotal = (TextView) findViewById(R.id.subtotalVal);
        taxes = (TextView) findViewById(R.id.taxesVal);
        discount = (TextView) findViewById(R.id.discountVal);
        totalPaid = (TextView) findViewById(R.id.totalAmountVal);
        receiptItemsRecyclerView = (RecyclerView) findViewById(R.id.receipt_items_recycler_view);
        homeButton = (Button) findViewById(R.id.home_button);


        basketReceipt = (BasketReceipt) getIntent().getSerializableExtra(Constants.TRANSACTION_SUMMARY);
        TotalSummary totalSummary = basketReceipt.getReceipt().getTotalSummary().get(0);

        setTotalPriceForItems(basketReceipt.getItems(), basketReceipt.getReconciledTotal());

        receiptCartAdapter = new BasketCartAdapter(basketReceipt.getItems(), basketReceipt.getOffers());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReceiptActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        receiptItemsRecyclerView.setLayoutManager(linearLayoutManager);
        receiptItemsRecyclerView.setAdapter(receiptCartAdapter);

        subtotal.setText("" + totalSummary.getTotal());
        taxes.setText("" + totalSummary.getTax());
        discount.setText("" + totalSummary.getDiscountedTotal());
        totalPaid.setText("" + totalSummary.getPaymentTotal());

        homeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ReceiptActivity.this, InitializeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    /**
     * setTotalPriceForItems sets the total price which is Quantity*Individual Price for items.
     *
     * @param items            List of items received from OmnyPay service
     * @param reconciledTotals List of reconciled total received from OmnyPay service
     */
    private void setTotalPriceForItems(List<Item> items, List<ReconciledTotal> reconciledTotals) {

        for (Item item : items) {
            for (ReconciledTotal reconciledTotal : reconciledTotals)
                if (item.getSku().equals(reconciledTotal.getSku())) {
                    item.setPrice(reconciledTotal.getTotal());
                }

        }
    }
}
