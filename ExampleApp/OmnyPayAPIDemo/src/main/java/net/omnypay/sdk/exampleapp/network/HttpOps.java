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

package net.omnypay.sdk.exampleapp.network;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import net.omnypay.sdk.exampleapp.utils.Constants;

import java.io.IOException;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * HttpOps is the class for making a http call directly to OmnyPay platform
 */
public class HttpOps {

    private static OkHttpClient client = new OkHttpClient();

    public static void doPost(final String url, final Object postBody, final Listener callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, gson.toJson(postBody));
                String timestamp = "" + ((new Date().getTime()) / 1000);
                String localVarPath = "/api/identity/authentication".replaceAll("\\{format\\}", "json");
                String signature = generateSignature("POST", timestamp, Constants.API_KEY, Constants.API_SECRET,
                        localVarPath, Constants.CORRELATION_ID, postBody);
                Request request = new Request.Builder().header("X-api-key", Constants.API_KEY)
                        .header("X-correlation-id", Constants.CORRELATION_ID).header("X-timestamp", timestamp)
                        .header("X-signature", signature).url(url).post(body).build();
                try {
                    Response response = client.newCall(request).execute();
                    callback.onResult(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void doGet(final String merchantPOSId, final Listener callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String localVarPath = ("/api/management/pos-terminal/merchant/" +
                        "{merchant-id}/merchant-pos-id/{merchant-pos-id}").replaceAll("\\{format\\}",
                        "json").replaceAll("\\{" + "merchant-id" + "\\}", Constants.MERCHANT_ID.toString())
                        .replaceAll("\\{" + "merchant-pos-id" + "\\}", (merchantPOSId.toString()));

                String getPOSIdUrl = "https://api.dev-000.omnyway.net/api/management/pos-terminal/" +
                        "merchant/{merchant-id}/merchant-pos-id/{merchant-pos-id}";
                getPOSIdUrl = getPOSIdUrl.replaceAll("\\{format\\}",
                        "json").replaceAll("\\{" + "merchant-id" + "\\}", Constants.MERCHANT_ID.toString())
                        .replaceAll("\\{" + "merchant-pos-id" + "\\}", (merchantPOSId.toString()));

                String timestamp = "" + ((new Date().getTime()) / 1000);
                String signature = generateSignature("GET", timestamp, Constants.API_KEY, Constants.API_SECRET,
                        localVarPath, Constants.CORRELATION_ID, null);
                Request request = new Request.Builder().header("X-api-key", Constants.API_KEY)
                        .header("X-correlation-id", Constants.CORRELATION_ID).header("X-timestamp", timestamp)
                        .header("X-signature", signature).url(getPOSIdUrl).build();
                try {
                    Response response = client.newCall(request).execute();
                    callback.onResult(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Generates signature to be passed as headers in request call.
     *
     * @param requestMethod
     * @param timestamp
     * @param apiKey
     * @param apiSecret
     * @param urlPath
     * @param correlationId
     * @param payload
     * @return
     */
    private static String generateSignature(String requestMethod, String timestamp, String apiKey,
                                            String apiSecret, String urlPath, String correlationId, Object payload) {
        String MerchantApiSecret = apiSecret;
        try {
            String stringToHash;
            Log.d("Request", "originalBody: " + payload);
            Gson gson = new Gson();
            String requestBody = gson.toJson(payload);
            Log.d("Request", "requestBody: " + requestBody);
            if (requestBody.equals("null")) {
                stringToHash = apiKey + timestamp + correlationId + requestMethod.toUpperCase() + urlPath;
            } else {
                stringToHash = apiKey + timestamp + correlationId + requestMethod.toUpperCase() + urlPath
                        + requestBody;
            }

            Log.d("Request", "varArray: " + stringToHash);
            String hash = generateHmacSHA256Signature(stringToHash, MerchantApiSecret);
            Log.d("Request", "hash: " + hash);
            return hash;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generates HMAC256 signature.
     *
     * @param message
     * @param apiSecret
     * @return
     */
    private static String generateHmacSHA256Signature(String message, String apiSecret) {
        String hash = null;
        try {
            final Mac mac = Mac.getInstance(Constants.HMAC_TYPE);
            final SecretKeySpec secretKey = new SecretKeySpec(apiSecret.getBytes(), Constants.HMAC_TYPE);
            mac.init(secretKey);
            hash = byteArrayToHexString(mac.doFinal(message.getBytes()));
        } catch (Exception e) {
            Log.d("", "Exception: " + e.toString());
        }
        return hash;
    }

    /**
     * Generates Hex String for the string passed.
     *
     * @param array
     * @return
     */
    public static String byteArrayToHexString(byte[] array) {
        StringBuffer hexString = new StringBuffer();
        for (byte b : array) {
            int intVal = b & 0xff;
            if (intVal < 0x10)
                hexString.append("0");
            hexString.append(Integer.toHexString(intVal));
        }
        return hexString.toString();
    }

}
