package com.example.adeejavier.waley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WaleyLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waley_login);

        final EditText edittext_username = (EditText) findViewById(R.id.edittext_username);
        final EditText edittext_password = (EditText) findViewById(R.id.edittext_password);

        final Button button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edittext_username.getText().toString();
                String password = edittext_password.getText().toString();

                FinanceApplication application = (FinanceApplication) getApplication();
                application.setUsername(username);
                application.setPassword(password);

                if (!username.equals("") && application.isValidPassword()) {
                    Intent intent_send = new Intent(WaleyLogin.this, HomeActivity.class);
                    startActivity(intent_send);
                } else {
                    Toast.makeText(WaleyLogin.this, "Invalid username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
