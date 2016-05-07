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

public class CurrencyActivity extends AppCompatActivity {
    private ArrayAdapter<Currency> currencies_aa = null;
    private ArrayList<Currency> currencies_al = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        currencies_aa = new ArrayAdapter<Currency>(this, android.R.layout.simple_list_item_1, currencies_al);
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
        AsyncTask<Void, Void, Void> download_task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                FinanceApplication application = (FinanceApplication) getApplication();
                HttpClient http_client = application.getHttpClient();
                String api_url = "http://api.fixer.io/latest?base=" + application.getCurrencyName();

                HttpGet http_request = new HttpGet(api_url);

                try {
                    HttpResponse http_response = http_client.execute(http_request);
                    if (http_response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                        Log.e("ERROR", "Invalid response status code");
                    }
                    String currencies = EntityUtils.toString(http_response.getEntity());
                    parseCurrencyJSON(currencies);
                } catch (Exception e) {

                }

                return null;
            }
        };
        download_task.execute();
    }

    private void parseCurrencyJSON(String currency_json) throws JSONException {
        JSONObject json_object = new JSONObject(currency_json);
        JSONArray currency_names = json_object.getJSONObject("rates").names();
        JSONObject rates_json = json_object.getJSONObject("rates");

        currencies_al.clear();
        for (int i = 0; i < currency_names.length(); ++i) {
            Currency currency = new Currency(currency_names.getString(i), rates_json.getDouble(currency_names.getString(i)));
            currencies_al.add(currency);
        }
        currencies_aa.notifyDataSetChanged();
    }
}
