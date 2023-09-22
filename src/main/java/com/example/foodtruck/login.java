package com.example.foodtruck;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class login extends AppCompatActivity {
    EditText username, password;
    Button b1;
    int count = 0;
    String uname, pswd;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        b1 = findViewById(R.id.b1);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            uname = b.getString("username");
            pswd = b.getString("password");
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = null;

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (isCredentialsValid(user, pass)) {
                    Toast.makeText(login.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this, third.class);
                    startActivity(intent);
                } else {
                    count++;
                    if (count >= 3) {
                        b1.setEnabled(false);
                    }
                    Toast.makeText(login.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isCredentialsValid(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(getFilesDir() + "/login.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String storedUsername = data[0];
                String storedPassword = data[1];
                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            Toast.makeText(login.this, "Failed to read user data", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return false;
    }
}
