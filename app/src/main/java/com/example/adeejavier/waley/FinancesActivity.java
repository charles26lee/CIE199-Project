package com.example.adeejavier.waley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class FinancesActivity extends AppCompatActivity {

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
    }
}
