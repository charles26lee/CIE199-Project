package com.example.adeejavier.waley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final ToggleButton togglebutton_expenses = (ToggleButton) findViewById(R.id.togglebutton_expenses);
        final EditText edittext_amount = (EditText) findViewById(R.id.edittext_amount);
        final EditText edittext_description = (EditText) findViewById(R.id.edittext_description);

        final ImageButton imagebutton_cancel = (ImageButton) findViewById(R.id.imagebutton_cancel);
        imagebutton_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_send = new Intent(AddActivity.this, FinancesActivity.class);
                startActivity(intent_send);
            }
        });

        final ImageButton imagebutton_save = (ImageButton) findViewById(R.id.imagebutton_save);
        imagebutton_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edittext_amount.getText().toString().equals("")) {
                    String description = edittext_description.getText().toString();
                    String type = "income";
                    double amount = Double.parseDouble(edittext_amount.getText().toString());

                    if (togglebutton_expenses.isChecked()) {
                        type = "expenses";
                        amount *= -1;
                    }

                    FinanceApplication application = (FinanceApplication) getApplication();
                    FinanceEntry finance_entry = new FinanceEntry(description, type, amount);
                    try {
                        application.addFinanceEntry(finance_entry);
                        Intent intent_send = new Intent(AddActivity.this, FinancesActivity.class);
                        startActivity(intent_send);
                    } catch (Exception e) {
                        Log.e("ERROR", "Exception occurred: " + e.getMessage());
                    }
                } else {
                    Toast.makeText(AddActivity.this, "Please specify an amount.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button button_currency = (Button) findViewById(R.id.button_currency);
        button_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_send = new Intent(AddActivity.this, CurrencyActivity.class);
                startActivity(intent_send);
            }
        });

        FinanceApplication application = (FinanceApplication) getApplication();
        button_currency.setText(application.getCurrencyName());
    }
}
