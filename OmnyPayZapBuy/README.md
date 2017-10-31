# [![OmnyWay](https://static1.squarespace.com/static/54ae3170e4b0afa8bbd35870/580a981346c3c414613e5a6f/58d158bd6b8f5bad3a99e941/1490115997842/)](http://www.omnypay.net/)

## Introduction

Zapbuy combines the latest in contextualization, personalization and frictionless checkout technologies to provide a unique user experience, designed to create an uplift in revenue and an improved ROI from merchant’s advertising budgets.Zapbuy works across all mediums — including social media, email, search engine, television and print — allowing consumers to buy products or services directly from ads with a single “ZAP.”
By adding the Zapbuy service within their mobile apps, banks, retailer and social media services can increase the stickiness of their payment and credit products, expand loyalty to their service, and also vastly improve their synergies with brands.

To start using Zapbuy, brands and retailers simply provide a catalog of products to be included in the Zapbuy service and select advertising mediums and campaign dates. Meanwhile, the Zapbuy functionality shows up within the social, banking or retail/brand apps powered by Omnyway’s Zapbuy SDK. The Zapbuy service also integrates directly into popular ad platforms and combines the Zapbuy code with the display ads, allowing shoppers to buy the product advertised with a “ZAP,” by utilizing the securely stored shopper credentials to complete the purchase.

## Merchant Registration
Register your app at http://www.omnypay.net/ You will be assigned a merchant id, merchant Api Key and merchant Api Secret. Please save this for future.

## User Registration
Register your user at http://www.omnypay.net/ You will be assigned a merchant Shopper Id and merchant Auth Token. Please save this for future.

## Android SDK
OmnyPayZapBuy Android SDK enables retailers/brands android apps to integrate OmnyPay's ZapBuy SDK to buy products or services directly from ads with a single “ZAP.”. OmnyPay's ZapBuy SDK routes consumers directly to a purchase page without requiring them to leave the medium they are viewing.

|   **SDK**   | **Description**                                                               | **Version** | **Release Date** |
|:-----------:|-------------------------------------------------------------------------------|:-----------:|:----------------:|
| OmnyPayZapBuy  | Provides access to OmnyPayZapBuy Platform                                      |     0.1.25     |    31-Oct-2017   |

## Version

|Version| Release date| Description                          |
|-------|-------------|--------------------------------------|
|    1.0|  31-Oct-2017| First Release                        |


## Requirements 
- Minimum android version supported is 16 (Android 4.1 Jelly Bean)
- Android studio 2.2 or higher
- Gradle build tools version 2.1.3 or higher
- Latest version of Android support repository installed
- Gradle dependencies
    - compile 'com.android.support:appcompat-v7:25.0.0' //(general)
    - compile 'com.android.support:design:23.0.0' //(general)
    - compile 'com.google.android.gms:play-services-gcm:8.1.0'
    
# Installation
## Gradle dependency
Currently OmnyPayZapBuy does not support Gradle but committed to provide it shortly. Please visit the site again on availability of Gradle dependency. For those developers who have signed up for developer account already, an email notification about availability will be sent. 

## Manually
Until we support Gradle installation, you can integrate OmnyPay into your project manually.

- Download the OmnyPayZapBuy SDK for android. For details see [here](https://github.com/omnypay/omnypay-sdk-android/tree/master/OmnyPayZapBuy).
- Select your application project in the project explorer and click on new module. Select import .AAR/.JAR package. Select the downloaded AAR android library.

## Integrating with core services
There are two main classes of SDK:

- **OmnyPayZapBuy**: A java class that is used to access all OmnyPayZapBuy APIs.
- **ZapBuyCallback**: A callback interface for Zapbuy.

## Steps required
Scan QR Code of a product and save it. There is only a single requirement before displaying product page of Zap Buy and it is:

### Initialize SDK
Initialize the OmnyPayZapBuy SDK using ```startZapBuy(context, qrData, merchantId, merchantShopperId, merchantAuthToken, merchantApiKey, merchantApiSecret, configuration, ZapBuyCallback())``` API by passing your qrCode, merchant Id, merchant shopper Id, merchant auth token, merchant api key and merchant api secret. qrData is the qr Code of the product which is to be purchased. The merchantId uniquely identifies your organization. Any API calls to access or update resources are scoped within the merchant id. The application can detect success through the completion callback.

### Display Product Page

```java
    // merchantId, merchantApiKey and merchantApiSecret received as a part of merchant registration process
   // merchantShopperId and merchantAuthToken received as a part of user registration process
    OmnyPayZapBuy omnyPayZapBuy = new OmnyPayZapBuy(context);
    omnyPayZapBuy.startZapBuy(context, qrData, merchantId, merchantShopperId, merchantAuthToken, merchantApiKey, merchantApiSecret, configuration, new ZapBuyCallback<View>(){
        @Override
        public void onResult(View view) {
            // Initialization complete
            // Set product page.
        }

        @Override
        public void onFailure(ZapBuyError zapBuyError) {
            // Initialization failed check status code from error object
        }
    });
```
Error returned is captured inside onFailure and it shows that any of the parameters passed is incorrect, or QR Code is not valid.
OnResult returns successful view of the product page. It can then be set inside a View or an Activity or as Fragment. Below is the code for setting the view in a Linear Layout inside an activity.

```java
	LinearLayout.LayoutParams webviewParams = new LinearLayout.LayoutParams
                   (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                            .LayoutParams.MATCH_PARENT);
            view.setLayoutParams(webviewParams);
            instantBuyLinearLayout.addView(view);
```

### Display Payment receipt
Displays the payment receipt for the current item purchased.

Prerequisite:
- Product Page is displayed successfully.

Tap on Zap Buy button displayed on Product Page and item is purchased on a single click.

## License
   ```
   Copyright 2016 OmnyPay Inc.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   ```
