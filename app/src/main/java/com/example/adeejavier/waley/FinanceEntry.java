package com.example.adeejavier.waley;

import org.json.JSONException;
import org.json.JSONObject;

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

    public String getType() {
        return type;
    }

    public JSONObject toJSONObject() {
        JSONObject json_object = new JSONObject();
        try {
            json_object.put("description", description);
            json_object.put("type", type);
            json_object.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json_object;
    }
}
