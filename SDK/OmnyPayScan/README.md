# [![OmnyPay](http://static1.squarespace.com/static/54ae3170e4b0afa8bbd35870/t/580cb7a09f7456d38de76cd7/1477511927583)](http://www.omnypay.net/)

## How to use

```java
    Scan.getInstance().startScan(appContext, new ScannedResultCallback() {
        @Override
        public void onScanResult(String s) {
            // scan result of bar code or QR code
            processResult(s);
        }
    });
```
