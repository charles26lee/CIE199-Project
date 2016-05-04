package com.example.adeejavier.waley;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by ecce-219-pc09-user01 on 5/4/2016.
 */
public class FinanceApplication extends Application {
    private ArrayList<FinanceEntry> finance_entries_al = new ArrayList<>();
    private Class last_activity;

    public void addFinanceEntry(FinanceEntry finance_entry) {
        finance_entries_al.add(finance_entry);
    }

    public ArrayList<FinanceEntry> getFinanceEntries() {
        return finance_entries_al;
    }

    public void setLastActivity(Class _last_activity) {
        last_activity = _last_activity;
    }

    public Class getLastActivity() {
        return last_activity;
    }
}
