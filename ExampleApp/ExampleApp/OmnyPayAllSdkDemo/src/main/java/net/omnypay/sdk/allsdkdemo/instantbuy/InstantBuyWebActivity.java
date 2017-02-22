package net.omnypay.sdk.allsdkdemo.instantbuy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import net.omnypay.sdk.allsdkdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class InstantBuyWebActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_buy_web);

        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "omnypay");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String scanResult = extras.getString("scan_result");

            try {
                JSONObject jObj = new JSONObject(scanResult);

                Map<String, String> values = new HashMap<>();

                String sku = jObj.getString("sku");
                String name = jObj.getString("name");
                String price = String.valueOf(jObj.getDouble("price"));

                values.put("sku", sku);
                values.put("name", name);
                values.put("price", price);

                loadHtmlInWebView(values);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(InstantBuyWebActivity.this, "JSON Exception",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(InstantBuyWebActivity.this, R.string.invalid_parameters_instantbuy,
                    Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void loadHtmlInWebView(Map<String, String> values) {
        String htmlContent = readHtmlFile();
        Template tmpl = Mustache.compiler().compile(htmlContent);

        htmlContent = tmpl.execute(values);

        webView.loadDataWithBaseURL("assets:///instantbuy/", htmlContent, "text/html; " +
                "charset=utf-8", "UTF-8", "");
    }

    public String readHtmlFile() {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream html = getAssets().open("instantbuy/index.html");
            in = new BufferedReader(new InputStreamReader(html, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            return buf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
