package net.omnypay.sdk.exampleapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.omnypay.sdk.core.model.Basket;
import net.omnypay.sdk.core.model.Item;
import net.omnypay.sdk.core.model.ProductOffer;
import net.omnypay.sdk.exampleapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * BasketCartAdapter class is an adapter class for Basket Item and it includes both item view and
 * SKU Product views.
 */

public class BasketCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int PRODUCT_OFFER = 1;
    private final int BASKET_ITEM = 2;
    private ArrayList<Object> basketItems = new ArrayList<Object>();


    public BasketCartAdapter(List<Item> items, List<ProductOffer> productOffers) {
        if (items != null) {
            buildBasketRecyclerViewItems(items, productOffers);
        }
    }

    private void buildBasketRecyclerViewItems(List<Item> items, List<ProductOffer> productOffers) {
        basketItems.clear();
        for (Item item : items) {
            basketItems.add(item);
            if (productOffers != null) {
                for (ProductOffer productOffer : productOffers) {
                    if (item.getSku().equals(productOffer.getSku())) {
                        basketItems.add(productOffer);
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
            case PRODUCT_OFFER:
                View v2 = inflater.inflate(R.layout.product_offer_layout, parent, false);
                viewHolder = new ProductOfferViewHolder(v2);
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
                Item item = (Item) basketItems.get(position);
                if (item != null) {
                    basketItemViewHolder.itemName.setText(item.getName());
                    basketItemViewHolder.itemQuantity.setText("" + item.getQty());
                    basketItemViewHolder.itemPrice.setText("" + item.getPrice());
                }

                break;


            case PRODUCT_OFFER:
                ProductOfferViewHolder productOfferViewHolder = (ProductOfferViewHolder) holder;
                ProductOffer productOffer = (ProductOffer) basketItems.get(position);
                if (productOffer != null) {
                    productOfferViewHolder.offerName.setText(productOffer.getTitle());
                    productOfferViewHolder.offerPrice.setText("" + productOffer.getDiscountCents());
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
        if (basketItems.get(position) instanceof Item) {
            return BASKET_ITEM;
        } else {
            return PRODUCT_OFFER;
        }

    }

    public void setResourceList(List<Item> items, List<ProductOffer> productOffers) {
        if (items != null) {
            buildBasketRecyclerViewItems(items, productOffers);
            notifyDataSetChanged();
        }
    }

    private class ProductOfferViewHolder extends RecyclerView.ViewHolder {

        private TextView offerName, offerPrice;

        public ProductOfferViewHolder(View v) {
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
