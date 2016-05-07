package com.example.adeejavier.waley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class FinancesActivity extends AppCompatActivity {
    private ArrayAdapter<FinanceEntry> finance_entries_aa = null;
    private ArrayList<FinanceEntry> finance_entries_al = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances);

        final Button button_finances_1 = (Button) findViewById(R.id.button_finances_1);
        final Button button_income_1 = (Button) findViewById(R.id.button_income_1);
        final Button button_expenses_1 = (Button) findViewById(R.id.button_expenses_1);

        button_finances_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFinances("finances");
            }
        });

        button_income_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFinances("income");
            }
        });

        button_expenses_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFinances("expenses");
            }
        });

        final ImageButton imagebutton_add = (ImageButton) findViewById(R.id.imagebutton_add);
        imagebutton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_send = new Intent(FinancesActivity.this, AddActivity.class);
                startActivity(intent_send);
            }
        });

        finance_entries_aa = new ArrayAdapter<FinanceEntry>(this, android.R.layout.simple_list_item_1, finance_entries_al);
        final ListView listview_finances = (ListView) findViewById(R.id.listview_finances);
        listview_finances.setAdapter(finance_entries_aa);

        FinanceApplication application = (FinanceApplication) getApplication();
        application.loadFinanceEntries();
        loadFinances(application.getFinanceMode());
    }

    private void loadFinances(String finance_mode) {
        FinanceApplication application = (FinanceApplication) getApplication();
        application.setFinanceMode(finance_mode);

        finance_entries_aa.clear();
        finance_entries_aa.addAll(application.getFinanceEntries());

        final Button button_finances = (Button) findViewById(R.id.button_finances_1);
        final Button button_income = (Button) findViewById(R.id.button_income_1);
        final Button button_expenses = (Button) findViewById(R.id.button_expenses_1);

        button_finances.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        button_income.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        button_expenses.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        button_finances.setTextColor(0xFF000000);
        button_income.setTextColor(0xFF000000);
        button_expenses.setTextColor(0xFF000000);

        if (finance_mode == "finances") {
            button_finances.setBackgroundColor(0xFF91DC5A);
            button_finances.setTextColor(0xFFFFFFFF);
        } else if (finance_mode == "expenses") {
            button_expenses.setBackgroundColor(0xFF91DC5A);
            button_expenses.setTextColor(0xFFFFFFFF);
        } else if (finance_mode == "income") {
            button_income.setBackgroundColor(0xFF91DC5A);
            button_income.setTextColor(0xFFFFFFFF);
        }

        final TextView textview_total = (TextView) findViewById(R.id.textview_total);
        NumberFormat formatter = new DecimalFormat("#0.00");
        textview_total.setText(formatter.format(application.getTotalAmount()));
    }
}
