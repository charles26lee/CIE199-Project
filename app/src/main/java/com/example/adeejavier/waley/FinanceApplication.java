package com.example.adeejavier.waley;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.util.ArrayList;

/**
 * Created by ecce-219-pc09-user01 on 5/4/2016.
 */
public class FinanceApplication extends Application {
    private ArrayList<FinanceEntry> finance_entries_al = new ArrayList<>();
    private String finance_mode = "finances";
    private Currency currency = new Currency("PHP", 1);

    private JSONArray finance_entries_json = null;

    private HttpClient http_client = new DefaultHttpClient();

    public void addFinanceEntry(FinanceEntry finance_entry) throws JSONException {
        finance_entries_al.add(finance_entry);
        finance_entries_json.put(finance_entry.toJSONObject());
        JSONObject json_file = new JSONObject();
        json_file.put("finances", finance_entries_json);
        json_file.put("currency", currency.getName());
        FSUtil.write(json_file.toString().getBytes());
    }

    public void loadFinanceEntries() {
        if (FSUtil.isStorageReady()) {
            String data = "";
            try {
                Log.i("INFO", "Reading file...");
                BufferedInputStream input_stream = new BufferedInputStream(FSUtil.getFileInputStream());
                while (input_stream.available() > 0) {
                    data += (char)(input_stream.read());
                }
                input_stream.close();
                Log.i("INFO", "Reading done: " + data);

                finance_entries_json = parseJSONString(data);
                finance_entries_al.clear();

                for (int i = 0; i < finance_entries_json.length(); ++i) {
                    JSONObject entry_json = finance_entries_json.getJSONObject(i);

                    String description = entry_json.getString("description");
                    String type = entry_json.getString("type");
                    double amount = entry_json.getDouble("amount");

                    FinanceEntry entry = new FinanceEntry(description, type, amount);
                    finance_entries_al.add(entry);
                }
            } catch (Exception e) {
                Log.e("ERROR", "Exception occurred: " + e.getMessage());

                finance_entries_json = new JSONArray();
            }
        }
    }

    public ArrayList<FinanceEntry> getFinanceEntries() {
        if (finance_mode == "finances") {
            return finance_entries_al;
        } else if (finance_mode == "expenses") {
            ArrayList<FinanceEntry> expenses_entries_al = new ArrayList<>();
            for (FinanceEntry entry : finance_entries_al) {
                if (entry.getType().equals("expenses")) {
                    expenses_entries_al.add(entry);
                }
            }
            return expenses_entries_al;
        } else if (finance_mode == "income") {
            ArrayList<FinanceEntry> income_entries_al = new ArrayList<>();
            for (FinanceEntry entry : finance_entries_al) {
                if (entry.getType().equals("income")) {
                    income_entries_al.add(entry);
                }
            }
            return income_entries_al;
        }
        return null;
    }

    public double getTotalAmount() {
        double total_amount = 0;
        for (FinanceEntry entry : finance_entries_al) {
            if (finance_mode == "finances") {
                total_amount += entry.getAmount();
            } else if (finance_mode == "expenses") {
                if (entry.getType().equals("expenses")) {
                    total_amount += entry.getAmount();
                }
            } else if (finance_mode == "income") {
                if (entry.getType().equals("income")) {
                    total_amount += entry.getAmount();
                }
            }
        }
        return total_amount;
    }

    public void setFinanceMode(String _finance_mode) {
        finance_mode = _finance_mode;
    }

    public String getFinanceMode() {
        return finance_mode;
    }

    public HttpClient getHttpClient() {
        return http_client;
    }

    public void setCurrency(Currency _currency) {
        currency = _currency;

        for (FinanceEntry entry : finance_entries_al) {
            entry.convert(currency.getRate());
        }
    }

    public String getCurrencyName() {
        return currency.getName();
    }

    public String getCount() {
        return finance_entries_al.size() + " vs " + finance_entries_json.length();
    }

    private JSONArray parseJSONString(String json_string) throws JSONException {
        JSONObject json_object = new JSONObject(json_string);
        return json_object.getJSONArray("finances");
    }
}
