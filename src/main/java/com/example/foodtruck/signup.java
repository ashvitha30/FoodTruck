package com.example.foodtruck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {
    EditText username, password, location, number;
    TextView loginHere;
    Button b1;
    String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        location = (EditText) findViewById(R.id.l1);
        number = (EditText) findViewById(R.id.ph1);
        b1 = (Button) findViewById(R.id.b1);
        loginHere = (TextView) findViewById(R.id.t5);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = username.getText().toString();
                String pswd = password.getText().toString();
                String loc = location.getText().toString();
                String pho = number.getText().toString();
                if (uname.isEmpty() || pswd.isEmpty() || loc.isEmpty() || pho.isEmpty()) {
                    Toast.makeText(signup.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(pswd)) {
                    Toast.makeText(signup.this, "Invalid password", Toast.LENGTH_SHORT).show();
                }  else if (!isValidPhoneNumber(pho)) {
                    Toast.makeText(signup.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                } else {
                    saveUserData(uname, pswd, loc, pho);
                    Intent i1 = new Intent(signup.this, login.class);
                    startActivity(i1);
                }
            }

            private boolean isValidPhoneNumber(String pho) {
                // Remove any whitespace or special characters from the phone number
                pho = pho.replaceAll("\\s+|-", "");

                // Check if the phone number is exactly 10 digits long
                if (pho.length() == 10) {
                    // Check if the phone number contains only digits
                    if (pho.matches("\\d+")) {
                        return true; // Valid phone number
                    }
                }
                return false; // Invalid phone number
            }

            private boolean isValidPassword(String pswd) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(pswd);
                return matcher.matches();
            }
        });

        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(signup.this, login.class);
                startActivity(i2);
            }
        });
    }

    private void saveUserData(String username, String password, String location, String phoneNumber) {
        String userData = username + "," + password + "," + location + "," + phoneNumber + "\n";

        try {
            FileWriter writer = new FileWriter(getFilesDir() + "/login.csv", true);
            writer.append(userData);
            writer.close();
            Toast.makeText(signup.this, "Signup Successful", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(signup.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}

