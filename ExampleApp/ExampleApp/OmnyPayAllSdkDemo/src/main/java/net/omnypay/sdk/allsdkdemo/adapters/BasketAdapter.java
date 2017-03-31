
package net.omnypay.sdk.allsdkdemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.omnypay.sdk.allsdkdemo.R;
import net.omnypay.sdk.core.model.BasketLineItem;
import net.omnypay.sdk.core.model.Item;
import net.omnypay.sdk.core.model.ProductOffer;
import net.omnypay.sdk.core.model.SkuOffer;

import java.util.ArrayList;
import java.util.List;

/**
 * BasketCartAdapter class is an adapter class for Basket Item and it includes both item view and
 * SKU Product views.
 */

public class BasketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int SKU_OFFER = 1;
    private final int BASKET_ITEM = 2;
    private ArrayList<Object> basketItems = new ArrayList<Object>();


    public BasketAdapter(List<BasketLineItem> items, List<SkuOffer> skuOffers ) {
        if (items != null) {
            buildBasketRecyclerViewItems(items, skuOffers);
        }
    }

    private void buildBasketRecyclerViewItems(List<BasketLineItem> items, List<SkuOffer> skuOffers) {
        basketItems.clear();
        for (BasketLineItem item : items) {
            basketItems.add(item);
            if (skuOffers != null) {
                for (SkuOffer skuOffer : skuOffers) {
                    if (item.getSku().equals(skuOffer.getSku())) {
                        basketItems.add(skuOffer);
                    }

                }

            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case SKU_OFFER:
                View v2 = inflater.inflate(R.layout.product_offer_layout, parent, false);
                viewHolder = new SkuOfferViewHolder(v2);
                break;

            default:
                View v1 = inflater.inflate(R.layout.basket_item_layout, parent, false);
                viewHolder = new BasketItemViewHolder(v1);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case BASKET_ITEM:
                BasketItemViewHolder basketItemViewHolder = (BasketItemViewHolder) holder;
                BasketLineItem item = (BasketLineItem) basketItems.get(position);
                if (item != null) {
                    basketItemViewHolder.itemName.setText(item.getName());
                    basketItemViewHolder.itemQuantity.setText("" + item.getQty());
                    basketItemViewHolder.itemPrice.setText("" + item.getPrice());
                }

                break;


            case SKU_OFFER:
                SkuOfferViewHolder skuOfferViewHolder = (SkuOfferViewHolder) holder;
                SkuOffer skuOffer = (SkuOffer) basketItems.get(position);
                if (skuOffer != null) {
                    skuOfferViewHolder.offerName.setText(skuOffer.getTitle());
                    skuOfferViewHolder.offerPrice.setText("" + skuOffer.getDiscountCents());
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return basketItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (basketItems.get(position) instanceof BasketLineItem) {
            return BASKET_ITEM;
        } else {
            return SKU_OFFER;
        }

    }

    public void setResourceList(List<BasketLineItem> items, List<SkuOffer> skuOffers) {
        if (items != null) {
            buildBasketRecyclerViewItems(items, skuOffers);
            notifyDataSetChanged();
        }
    }

    private class SkuOfferViewHolder extends RecyclerView.ViewHolder {

        private TextView offerName, offerPrice;

        public SkuOfferViewHolder(View v) {
            super(v);
            offerName = (TextView) v.findViewById(R.id.offer_name);
            offerPrice = (TextView) v.findViewById(R.id.offer_value);
        }
    }


    private class BasketItemViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName, itemQuantity, itemPrice;

        public BasketItemViewHolder(View v) {
            super(v);
            itemName = (TextView) v.findViewById(R.id.item_name);
            itemQuantity = (TextView) v.findViewById(R.id.item_quantity);
            itemPrice = (TextView) v.findViewById(R.id.item_price);
        }
    }
}
