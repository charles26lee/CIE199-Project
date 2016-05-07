package com.example.adeejavier.waley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button button_finances = (Button) findViewById(R.id.button_finances);
        button_finances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity("finances");
            }
        });

        final Button button_expenses = (Button) findViewById(R.id.button_expenses);
        button_expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity("expenses");
            }
        });

        final Button button_income = (Button) findViewById(R.id.button_income);
        button_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity("income");
            }
        });
    }

    private void goToNextActivity(String finance_mode) {
        FinanceApplication application = (FinanceApplication) getApplication();
        application.setFinanceMode(finance_mode);

        Intent intent_send = new Intent(HomeActivity.this, FinancesActivity.class);
        startActivity(intent_send);
    }
}
