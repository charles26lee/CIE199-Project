package com.example.adeejavier.waley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class FinancesActivity extends Activity {
    private final ArrayAdapter<FinanceEntry> finance_entries_aa = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances);

        final ImageButton imagebutton_add = (ImageButton) findViewById(R.id.imagebutton_add);
        imagebutton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_send = new Intent(FinancesActivity.this, AddActivity.class);
                startActivity(intent_send);
            }
        });

        final ListView listview_finances = (ListView) findViewById(R.id.listview_finances);
        listview_finances.setAdapter(finance_entries_aa);

        FinanceApplication application = (FinanceApplication) getApplication();
        application.loadFinanceEntries();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FinanceApplication application = (FinanceApplication) getApplication();

        finance_entries_aa.clear();
        finance_entries_aa.addAll(application.getFinanceEntries());
    }
}
