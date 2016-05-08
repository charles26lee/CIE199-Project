package com.example.adeejavier.waley;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CurrencyActivity extends AppCompatActivity {
    private ArrayAdapter<Currency> currencies_aa = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        currencies_aa = new ArrayAdapter<Currency>(this, android.R.layout.simple_list_item_1);
        final ListView listview_currencies = (ListView) findViewById(R.id.listview_currencies);
        listview_currencies.setAdapter(currencies_aa);

        listview_currencies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FinanceApplication application = (FinanceApplication) getApplication();
                application.setCurrency(currencies_aa.getItem(position));

                Intent intent_send = new Intent(CurrencyActivity.this, AddActivity.class);
                startActivity(intent_send);
            }
        });

        downloadExchangeRates();
    }

    private void downloadExchangeRates() {
        AsyncTask<Void, Void, List<Currency>> download_task = new AsyncTask<Void, Void, List<Currency>>() {
            @Override
            protected List<Currency> doInBackground(Void... params) {
                FinanceApplication application = (FinanceApplication) getApplication();
                HttpClient http_client = application.getHttpClient();
                String api_url = "http://api.fixer.io/latest?base=" + application.getCurrencyName();

                HttpGet http_request = new HttpGet(api_url);
                List<Currency> currencies_al = new ArrayList<>();

                try {
                    HttpResponse http_response = http_client.execute(http_request);
                    if (http_response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                        Log.e("ERROR", "Invalid response status code");
                    }
                    String currencies_json = EntityUtils.toString(http_response.getEntity());
                    parseCurrencyJSON(currencies_json, currencies_al);
                } catch (Exception e) {
                    Log.e("ERROR", "Exception occurred: " + e.getMessage());
                }

                return currencies_al;
            }


            @Override
            protected void onPostExecute(List<Currency> currencies) {
                currencies_aa.clear();
                currencies_aa.addAll(currencies);

                super.onPostExecute(currencies);
            }
        };
        download_task.execute();
    }

    private void parseCurrencyJSON(String currency_json, List<Currency> currencies_al) throws JSONException {
        JSONObject json_object = new JSONObject(currency_json);
        JSONArray currency_names = json_object.getJSONObject("rates").names();
        JSONObject rates_json = json_object.getJSONObject("rates");

        for (int i = 0; i < currency_names.length(); ++i) {
            Currency currency = new Currency(currency_names.getString(i), rates_json.getDouble(currency_names.getString(i)));
            currencies_al.add(currency);
        }
    }
}
