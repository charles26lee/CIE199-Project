package com.example.adeejavier.waley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class AddActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final ToggleButton togglebutton_expenses = (ToggleButton) findViewById(R.id.togglebutton_expenses);
        final EditText edittext_amount = (EditText) findViewById(R.id.edittext_amount);

        final ImageButton imagebutton_save = (ImageButton) findViewById(R.id.imagebutton_save);
        imagebutton_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(edittext_amount.getText().toString());

                if (togglebutton_expenses.isChecked()) {
                    amount *= -1;
                }

                FinanceEntry finance_entry = new FinanceEntry("", amount);

                FinanceApplication application = (FinanceApplication) getApplication();
                application.addFinanceEntry(finance_entry);

                Intent intent_send = new Intent(AddActivity.this, application.getLastActivity());
                startActivity(intent_send);
            }
        });
    }
}
