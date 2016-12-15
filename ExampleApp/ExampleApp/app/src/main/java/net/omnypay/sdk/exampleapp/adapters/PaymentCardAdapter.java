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
