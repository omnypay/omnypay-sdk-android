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

package net.omnypay.sdk.exampleapp.adapters;

/**
 * CardAdapter class is an adapter class for Cards to be displayed.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.omnypay.sdk.core.model.PaymentInstrumentOffers;
import net.omnypay.sdk.core.model.ProvisionCardParam;
import net.omnypay.sdk.exampleapp.R;

import java.util.ArrayList;
import java.util.List;

public class PaymentCardAdapter extends BaseAdapter implements View.OnClickListener {


    private static LayoutInflater inflater = null;
    private List<PaymentInstrumentOffers> paymentInstrumentOffersList;

    public PaymentCardAdapter(Context context, List<PaymentInstrumentOffers> paymentInstrumentOffersList) {
        this.paymentInstrumentOffersList = paymentInstrumentOffersList;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setResourceList(ArrayList<PaymentInstrumentOffers> resourceList) {
        this.paymentInstrumentOffersList = resourceList;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getCount() {
        return paymentInstrumentOffersList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View cardItemView = view;
        CardViewHolder holder;
        if (view == null) {

            cardItemView = inflater.inflate(R.layout.card_layout, null);
            holder = new CardViewHolder();
            holder.cardNumber = (TextView) cardItemView.findViewById(R.id.card_number);
            holder.cardType = (TextView) cardItemView.findViewById(R.id.card_type);
            cardItemView.setTag(holder);

        } else {
            holder = (CardViewHolder) cardItemView.getTag();
        }
        PaymentInstrumentOffers paymentInstrumentOffers = paymentInstrumentOffersList.get(i);
        holder.cardNumber.setText(paymentInstrumentOffers.getCardNumber());
        PaymentInstrumentOffers.CardTypeEnum cardType = paymentInstrumentOffers.getCardType();
        if (cardType != null) {
            holder.cardType.setText(cardType.toString());
        }
        return cardItemView;
    }

    private static class CardViewHolder {
        TextView cardNumber;
        TextView cardType;
    }
}
