package com.example.adeejavier.waley;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by ecce-219-pc09-user01 on 5/4/2016.
 */
public class FinanceEntry {
    private String description = "";
    private String type = "";
    private double amount = 0.0;

    public FinanceEntry(String _description, String _type, double _amount) {
        description = _description;
        type = _type;
        amount = _amount;
    }

    public void convert(double rate) {
        amount *= rate;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public JSONObject toJSONObject() {
        JSONObject json_object = new JSONObject();
        try {
            json_object.put("description", description);
            json_object.put("type", type);
            json_object.put("amount", amount);
        } catch (Exception e) {
            Log.e("ERROR", "Exception occurred: " + e.getMessage());
        }
        return json_object;
    }

    public String toString() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(amount);
    }
}
