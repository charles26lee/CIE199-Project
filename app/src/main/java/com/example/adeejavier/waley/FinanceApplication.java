package com.example.adeejavier.waley;

import android.app.Application;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.util.ArrayList;

/**
 * Created by ecce-219-pc09-user01 on 5/4/2016.
 */
public class FinanceApplication extends Application {
    private ArrayList<FinanceEntry> finance_entries_al = new ArrayList<>();

    private HttpClient http_client = new DefaultHttpClient();
    private Currency currency = new Currency("PHP", 1);

    private String finance_mode = "finances";

    private String username = "";
    private String password = "";

    public void addFinanceEntry(FinanceEntry finance_entry) {
        finance_entries_al.add(0, finance_entry);
    }

    public void saveFinanceEntries() {
        JSONObject json_file = new JSONObject();
        try {
            JSONArray finance_entries_json = new JSONArray();
            for (FinanceEntry entry : finance_entries_al) {
                finance_entries_json.put(entry.toJSONObject());
            }
            json_file.put("finances", finance_entries_json);
            json_file.put("currency", currency.getName());
            json_file.put("password", password);
        } catch (Exception e) {
            Log.e("ERROR", "Exception occurred: " + e.getMessage());
        }
        FSUtil.write(json_file.toString().getBytes(), username);
    }

    public void loadFinanceEntries() {
        if (FSUtil.isStorageReady() && finance_entries_al.isEmpty()) {
            String data = "";
            try {
                Log.i("INFO", "Reading file...");
                BufferedInputStream input_stream = new BufferedInputStream(FSUtil.getFileInputStream(username));
                while (input_stream.available() > 0) {
                    data += (char) (input_stream.read());
                }
                input_stream.close();
                Log.i("INFO", "Reading done: " + data);

                JSONObject json = new JSONObject(data);

                JSONArray finance_entries_json = json.getJSONArray("finances");
                currency = new Currency(json.getString("currency"), 1);

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

    public void setUsername(String _username) {
        username = _username;
    }

    public void setPassword(String _password) {
        password = _password;
    }

    public boolean isValidPassword() {
        finance_entries_al.clear();
        if (FSUtil.isStorageReady()) {
            String data = "";
            try {
                Log.i("INFO", "Reading file...");
                BufferedInputStream input_stream = new BufferedInputStream(FSUtil.getFileInputStream(username));
                while (input_stream.available() > 0) {
                    data += (char) (input_stream.read());
                }
                input_stream.close();
                Log.i("INFO", "Reading done: " + data);

                JSONObject json = new JSONObject(data);

                return json.getString("password").equals(password);
            } catch (Exception e) {
                Log.e("ERROR", "Exception occurred: " + e.getMessage());
            }
        }
        return true;
    }
}
