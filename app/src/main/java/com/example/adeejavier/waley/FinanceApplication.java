package com.example.adeejavier.waley;

import android.app.Application;
import android.util.Log;

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

    private JSONArray finance_entries_json = null;

    public void addFinanceEntry(FinanceEntry finance_entry) {
        finance_entries_al.add(finance_entry);
        finance_entries_json.put(finance_entry.toJSONObject());
        FSUtil.write(finance_entries_json.toString().getBytes());
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

                parseJSONString(data);
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
                if (entry.getType() == "expenses") {
                    expenses_entries_al.add(entry);
                }
            }
            return expenses_entries_al;
        } else if (finance_mode == "income") {
            ArrayList<FinanceEntry> income_entries_al = new ArrayList<>();
            for (FinanceEntry entry : income_entries_al) {
                if (entry.getType() == "income") {
                    income_entries_al.add(entry);
                }
            }
            return income_entries_al;
        }
        return null;
    }

    public void setFinanceMode(String _finance_mode) {
        finance_mode = _finance_mode;
    }

    public String getFinanceMode() {
        return finance_mode;
    }

    private void parseJSONString(String json_string) throws JSONException {
        JSONObject json_object = new JSONObject(json_string);
        finance_entries_json = json_object.getJSONArray("finances");
    }
}
