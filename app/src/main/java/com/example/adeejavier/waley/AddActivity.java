package com.example.adeejavier.waley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText edittext_amount = (EditText) findViewById(R.id.edittext_amount);

        final ImageButton imagebutton_save = (ImageButton) findViewById(R.id.imagebutton_save);
        imagebutton_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinanceEntry finance_entry = new FinanceEntry("", 0.0);

                FinanceApplication application = (FinanceApplication) getApplication();
                application.addFinanceEntry(finance_entry);

                Intent intent_send = new Intent(AddActivity.this, application.getLastActivity());
                startActivity(intent_send);
            }
        });
    }
}
