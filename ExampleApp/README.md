# [![OmnyPay](http://static1.squarespace.com/static/54ae3170e4b0afa8bbd35870/t/580cb7a09f7456d38de76cd7/1477511927583)](http://www.omnypay.net/)

## Requirements
Follow below steps to import exampleapp in your workspace:
- Clone/Download the repository placed at https://github.com/omnypay/omnypay-sdk-android/tree/master/ExampleApp/ExampleApp
- Manually add OmnyPayAPI, OmnyPayAuth, OmnyPayIdentity, OmnyPayScan(OmnyPayDLScan and OmnyPayPIScan) aars placed at https://github.com/omnypay/omnypay-sdk-android
- Sync workspace.
- InitializeActivity is marked as the launcher class.
- Replace constants in InitializeActivity
	- InitializeActivity.merchantId with YOUR MERCHANT ID.
	- InitializeActivity.username with YOUR USERNAME.
	- InitializeActivity.password with YOUR PASSWORD.
	- Constants.API_KEY with APIKEY for the Merchant.
	- Constants.API_SECRET with APISECRET for the Merchant.
	- Constants.CORRELATION_ID with CORRELATION_ID for the Merchant.