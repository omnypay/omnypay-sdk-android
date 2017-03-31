# [![OmnyPay](http://static1.squarespace.com/static/54ae3170e4b0afa8bbd35870/t/580cb7a09f7456d38de76cd7/1477511927583)](http://www.omnypay.net/)

## Introduction

In order to serve the emerging needs of merchants, OmnyPay has developed a SaaS based white label Contextual Commerce platform with which merchants, banks and service providers will be able to offer their consumers, several unique digital commerce services, as part of their own branded mobile app – thus giving the merchants, full control of their consumer relationships, consumer data and consumer experiences when shopping across their multi-channels environments. Equally, working with partner’s bank’s app, merchants can have a “virtual” relationship with non-frequent customers as well.
The OmnyPay SaaS platform offers a cutting edge orchestration layer which allows the rapid integration of its platform to the merchants’ POS, CRM, Loyalty, marketing, inventory, analytics, and payments, while also enabling a unique core set of premium digital services as part of the Merchants’ own branded app for their repeat customers, and their partners app for less frequently visiting customers.

![OmnyPaySaaS](https://github.com/omnypay/omnypay-sdk-android/blob/master/assets/Images/OmnyPaySaaS.png)

Consumers identify with the brands that they interact with the most, and have built inherent loyalty with them. The OmnyPay white label solution leverages and strengthens the ongoing brand equity built by the merchant or bank or service provider throughout the ongoing relationship with their loyal shoppers, and by consistently promoting it across all channels.  Through its mobile SDK and Open APIs, the OmnyPay platform can be quickly integrated with the existing mobile apps, as well as, with their legacy POS and backend systems.

## Registration
Register your app at http://www.omnypay.net/ and save merchantId

## Android SDK
OmnyPay android SDK enables retailer/merchant android apps to integrate OmnyPay's rich checkout experience for a shopper. OmnyPay SDK provides simple functions to perform operations on OmnyPay platform.

## Version

|Version| Release date| Description                          |
|-------|-------------|--------------------------------------|
|    1.0|  08-Dec-2016| First Release                        |


## Requirements 
- Minimum android version supported is 16 (Android 4.1 Jelly Bean)
- Android studio 2.2 or higher
- Gradle build tools version 2.1.3 or higher
- Latest version of Android support repository installed
- Gradle dependencies
    - compile 'com.android.support:appcompat-v7:23.0.0' (general)
    - compile 'com.android.support:design:23.0.0' (general)
    - compile 'com.squareup.picasso:picasso:2.5.2' (required for OmnyPayAPI)
    - compile 'com.squareup.okhttp:okhttp:2.7.5' (required for OmnyPayAPI & OmnyPayIdentity)
    - compile 'com.journeyapps:zxing-android-embedded:3.3.0' (required for OmnyPayScan module)
    - compile 'com.google.android.gms:play-services-vision:8.1.0' (required for OmnyPayScan module)
    - compile 'com.squareup.okhttp:logging-interceptor:2.7.5' (required for OmnyPayAPI & OmnyPayIdentity)
    - compile 'com.google.code.gson:gson:2.6.2' (required for OmnyPayAPI)
    - compile 'joda-time:joda-time:2.9.3' (required for OmnyPayAPI)
    - compile 'com.neovisionaries:nv-websocket-client:1.31' (required for OmnyPayAPI)
    - compile 'com.google.android.gms:play-services-gcm:8.1.0' (required for OmnyPayAPI)
    - Add Jumio dependency in app level build.gradle
```groovy
            repositories {
                maven {
                    url 'http://mobile-sdk.jumio.com'
                }
            }

            ext {
                SDK_VERSION = "2.3.0"
            }

            dependencies {
                ...
                compile 'com.jumio.android:bam:${SDK_VERSION}@aar'
                compile 'com.jumio.android:core:${SDK_VERSION}@aar'
                compile 'com.jumio.android:nv:${SDK_VERSION}@aar'
                compile 'com.jumio.android:nv-ocr:${SDK_VERSION}@aar'
                ...
            }
```

# Installation
## Gradle dependency
Currently OmnyPay does not support Gradle but committed to provide it shortly. Please visit the site again on availability of Gradle dependency. For those developers who have signed up for developer account already, an email notification about availability will be sent. 

## Manually
Until we support Gradle installation, you can integrate OmnyPay into your project manually.

- Download the OmnyPay SDK for android. For details see <a href="https://github.com/omnypay/omnypay-sdk-android">here</a>.
- Select your application project in the project explorer and click on new module. Select import .AAR/.JAR package. Select the downloaded AAR android library.

## Integrating with core services
There are two main classes of SDK:

- **OmnyPayAPI**: A java class that is used to access all OmnyPay APIs.
- **OmnyPayCallback**: A generic callback interface for API responses.

## Minimum steps required
All the APIs provided by the SDK perform specific operations provided they are called in a logical sequence. However there are two minimum required steps to follow before any other operation can be performed, these are:

### Initialize SDK
Initialize the OmnyPay SDK using ```initialize(merchantId, null, OmnypayCallback())``` API by passing your merchant Id. The merchantId uniquely identifies your organization. Any API calls to access or update resources are scoped within the merchant id. This should be called prior to calling OmnyPay APIs and recommend this be invoked at application startup. The application can detect success through the completion callback.

### Authenticate shopper
OmnyPay supports authentication through a retailer authentication service. If a retailer authentication service is used, the Retailer should work with OmnyPay technical representative to establish the connection before invoking the authentication API. OmnyPay supports [Basic authentication] and OAuth token in its API and SDK

To verify using OAuth, the application should pass auth token and user id in ```authenticateShopper(authToken, merchantShopperId, OmnyPayCallback)``` API. If the OAuth token is refreshed, the application should call this API to re-authenticate the user in order to allow the shopper to continue seamlessly. These steps are necessary to make minimum validations in order to continue with any further operation.

# Anatomy of a typical transaction flow

All the APIs perform specific operations so you can design your own transaction flow and user experience.

An example flow can be created as below:

### Initialize the SDK.

```java
    // merchantId received as a part of registration process
    OmnyPayAPI omnyPayAPI = new OmnyPayAPI(context);
    omnyPayAPI.initialize(context, merchantId, null, new OmnyPayCallback<Session>(){
        @Override
        public void onResult(Session session) {
            // Initialization complete
            // Core APIs can be called now
        }

        @Override
        public void onFailure(OmnyPayError omnyPayError) {
            // Initialization failed check status code and verify merchantId
        }
    });
```

### Authenticate with identity service
Authenticate the user with your Identity service (which in turn can be catered by OmnyPay). Once authenticated, pass on the authorization token to OmnyPay service (next step).

### Authenticate user (shopper) on OmnyPay platform by passing your user/shopper id and auth token

```java
    OmnyPayAPI omnyPayAPI = new OmnyPayAPI(context);
    omnyPayAPI.authenticateShopper(merchantShopperId, merchantAuthToken, 
        new OmnypayCallback<AuthenticatedSession>() {
              @Override
              public void onResult(AuthenticatedSession result) {
                  // Authentication is successful
              }

              @Override
              public void onFailure(OmnyPayError e) {
                  // Authentiaction failed check token and shopper account.
              }
    });
```

### Add a payment instrument for shopper
Depending on the vault configuration requested during merchant onboarding, OmnyPay SDK will connect to its own vault or a third party vault including that of a retailer.

```java
    ProvisionCardParam provisionCardParam = new ProvisionCardParam();
    provisionCardParam.setCardNumber("1234567812345678");
    provisionCardParam.setCardAlias("My Card");
    provisionCardParam.setCardType(ProvisionCardParam.CardTypeEnum.debit_card);
    provisionCardParam.setCardIssuer("Thomas cook");
    provisionCardParam.setCardHolderZip("90211");
    provisionCardParam.setCardExpiryDate("July-24, 2020");
    provisionCardParam.setCardHolderName("Jane Wilson");

    OmnyPayAPI omnyPayAPI = new OmnyPayAPI(context);
    omnyPayAPI.provisionPaymentInstrument(provisionCardParam, new 
                            OmnyPayCallback<PaymentInstrumentInfo>(){
        @Override
        void onResult(PaymentInstrumentInfo pi){
            // Payment instrument has been successfully added
        }

        @Override
        void onFailure(OmnyPayError e){
            // Error in adding card, check the status code and message
        }
    });
```

### Create a basket
Every OmnyPay transaction should have a basket object. The basket object is used to store information about the transaction such as association with the retailer’s point of sale, line items or products purchased, associated offers, loyalty points, etc.

```java
    OmnyPayAPI omnyPayAPI = new OmnyPayAPI(context);
    omnyPayAPI.createBasket(new OmnyPayCallback<Basket>() {
        @Override
        public void onResult(Basket basket) {
            // Basket created successfully
            // goToPaymentSelection() or addItemsToBasket()
        }

        @Override
        public void onFailure(OmnyPayError omnyPayError) {
            //Basket creation failed check the error response
        }
    });
```

### Scan the Point of Sale QRCode

Scan the QRCode for the Point of Sale using **OmnyPayScan SDK**.

```java
    OmnyPayScan.getInstance().start(appContext, new ScannedResultCallback() {
        @Override
        public void onScanResult(String s) {
            // scan result of bar code or QR code
            processResult(s);
        }
    });
```

### Checkin basket at point of Sale terminal
Register a Checkin of the shopper at the Point of Sale through a scan of a QR code or a beacon. Either way, the application should pass the Point of Sale(POS) Id mapped to the QR code.

Prerequisites: 
- AuthenticatedShopper

```java
    OmnyPayAPI omnyPayAPI = new OmnyPayAPI(context);
    omnyPayAPI.checkIn(posID, new OmnyPayCallback<MerchantPos>() {
        @Override
        public void onResult(MerchantPos result) {
            // Basket successfully associated, now either choose the payment method
            // Or edit your kart from POS terminal
        }

        @Override
        public void onFailure(OmnyPayError error) {
            // Basket association failed, the POS might be busy or
            // mismatch in merchant ID and POS terminals
            // Check status code from error object
        }
    });
```

### POS adds items in the basket

The application must listen to following **local broadcast** actions to get any change in basket state:

- ```OmnyPayAPI.ACTION_UPDATE_BASKET: Notifies basket has been updated (contains updated Basket).```
- ```OmnyPayAPI.ACTION_BASKET_RECEIPT_UPDATE: Basket receipt received, app can show the receipt in UI (Contains BasketReceipt).```
- ```OmnyPayAPI.ACTION_TRANSACTION_CANCELLED: Transaction has been canceled by point of sale terminal. Update app accordingly.```
- ```OmnyPayAPI.ACTION_POST_PURCHASE_OFFER: Notifies app about any post purchase offers (Offer URL)```

Any relevant data in these broadcasts is passed as serializable extra with key ```OmnyPayAPI.BROADCAST_DATA```.

Get relevant data:

```java
    LocalBroadcastManager.getInstance(cxt).registerReceiver(basketUpdateReceiver,
                new IntentFilter(OmnyPayAPI.ACTION_UPDATE_BASKET));

    BroadcastReceiver basketUpdatereceiver = new BroadcastReceiver(){
        ...
        @Override
        onReceive(Context cxt, Intent i) {
            // get updated basket here
            Basket updatedBasket = (Basket) i.getSerializableExtra(OmnyPayAPI.BROADCAST_DATA);
        }
    }
```

### Preview basket for selected payment instrument

Preview the projected itemized payment for the payment instrument selected. The basket will contain:
- Itemized product with associated offer discounts in the basket
- Card linked offer discounts
- Eligible loyalty points redeemed
- Subtotal, tax and total amounts
    

Prerequisite:
- AuthenticatedShopper
- Basket Created 
- Successful Check-in
- Basket with a non-zero subtotal value
- Optional – Loyalty points redemption selected
- Optional – Coupons or Offers selected

```java
    OmnyPayAPI omnyPayAPI = new OmnyPayAPI(context);
    omnyPayAPI.previewBasketForPaymentInstrument(paymentInstrumentID, new
            OmnyPayCallback<BasketPreview>() {
        @Override
        public void onResult(BasketPreview result) {
            // BasketPreview with applied applied offers for selected 
            // payment instrument
        }

        @Override
        public void onFailure(OmnyPayError error) {
            // Check status code and message error object
        }
    });
```

### Start the payment

Pay the retail transaction using the payment instrument selected by the shopper.

Prerequisite:
- AuthenticatedShopper 
- Basket Created 
- Successful CheckIn
- Basket with a non-zero subtotal value.
- Optional – Loyalty points redemption selected.
- Optional – Coupons or Offers selected
- Optional – PreviewPayment done to estimate taxes, card-linked offers 

```java
    OmnyPayAPI omnyPayAPI = new OmnyPayAPI(context);
    omnyPayAPI.startPayment(paymentInstrumentationID, OmnyPayCallback
                                        <BasketPaymentConfirmation>() {
        @Override
        public void onFailure(OmnyPayError omnyPayError) {
            // Payment failed
        }

        @Override
        public void onResult(BasketPaymentConfirmation basketPaymentConfirmation) {
            // Payment successful
            showReceiptOrThankYou(basketPaymentConfirmation);
        }
    });
```

### Get payment receipt
Fetches the payment receipt for the current transaction

Prerequisite:
- AuthenticatedShopper
- Basket Created
- Successful CheckIn
- Basket with a non-zero subtotal value.

```java
    OmnyPayAPI omnyPayAPI = new OmnyPayAPI(context);
    omnyPayAPI.getPaymentReceipt(new OmnyPayCallback<BasketReceipt>() {
        @Override
        public void onFailure(OmnyPayError omnyPayError) {
            // Failed to fetch receipts
        }

        @Override
        public void onResult(BasketReceipt receipt) {
            // Receipts fetched successfully
            showReceiptOnUI(receipt);
        }
    });
```

# Using supporting OmnyPay libraries

### OmnyPayDLScan
This library helps in retrieving Driving licence information from device camera scan. To use this library: 

Add OmnyPayDLScan library in build path and scan a valid driving licence to get information

```java
        OmnyPayDLScanner.getInstance().start(
                myActivityContext, new DriversLicenseScanCallback() {
            @Override
            public void onDriversLicenseScan(IdentityData identityData) {
              
                // Driving licence information received from scan, use this
                // to populate the UI or verification.
            }
        });
```

### OmnyPayPIScan
This library helps in retrieving credit / debit card or payment instrument information from device camera scan. To use this library: 

Add OmnyPayPIScan library in build path and scan a valid credit / debit card to get information

```java
        OmnyPayPIScanner.getInstance().start(
                myActivityContext, new PaymentInstrumentScanCallback() {
            @Override
            public void onPaymentInstrumentScan(CardInformation cardInformation) {
              
                // payment instrumentation information received.
            }
        });
```

### OmnyPayAuth

This library is for user's touch authentication. Works with Samsung spass and android finger print APIs. This library can be used for authorizing user before displaying existing payment instruments or making a payment.

Add OmnyPayAuth library in build path and authenticate wherever needed requried in the app.
```java
        TouchAuthenticationManager.getInstance().initiateAuthentication(MainActivity.this,
                password, dialogTitleForFailure, new Authentication() {
                    @Override
                    public void authenticationComplete(boolean isAuthenticatedSuccessfully) {
                        if(isAuthenticatedSuccessfully){
                            // show success dialog
                        } else {
                            // Authentication failed, use some other authentication method
                        }
                    }
                });
```

### Sample app
Checkout our sample app <a href="https://github.com/omnypay/omnypay-sdk-android/tree/master/ExampleApp/ExampleApp">here</a>.

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
