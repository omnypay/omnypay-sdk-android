package net.omnypay.sdk.allsdkdemo.instantbuy;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kaushald on 09/02/17.
 */

public class WebAppInterface {

    Context mContext;

    public WebAppInterface(Context context) {
        this.mContext = context;
    }

    @JavascriptInterface
    public void buy(String buyObj) {
        SharedPreferences.Editor prefsEditor = mContext.getSharedPreferences("instantbuy",
                Context.MODE_PRIVATE).edit();
        prefsEditor.putString("instantbuyitemdata", buyObj);
        prefsEditor.apply();
    }

    @JavascriptInterface
    public void buyNow(String sku, String name, long price, long qty, String shipping_address,
                       String payment_instrument) {
        try {
            JSONObject buyObj = new JSONObject();
            buyObj.put("sku", sku);
            buyObj.put("name", name);
            buyObj.put("price", price);
            buyObj.put("qty", qty);
            buyObj.put("shipping_address", shipping_address);
            buyObj.put("payment_instrument", payment_instrument);
            SharedPreferences.Editor prefsEditor = mContext.getSharedPreferences("instantbuy",
                    Context.MODE_PRIVATE).edit();
            prefsEditor.putString("instantbuyitemdata", buyObj.toString());
            prefsEditor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
