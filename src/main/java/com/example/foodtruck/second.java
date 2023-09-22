package com.example.foodtruck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class second extends AppCompatActivity {
    Button b2,b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        b2=(Button) findViewById(R.id.button2);
        b3=(Button) findViewById(R.id.button3);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigateToPage1();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigateToPage2();
            }
        });
    }

    private void navigateToPage1() {
        Intent intent = new Intent(second.this, signup.class);
        startActivity(intent);
    }

    private void navigateToPage2() {
        Intent intent = new Intent(second.this, NgoLogin.class);
        startActivity(intent);
    }
}

