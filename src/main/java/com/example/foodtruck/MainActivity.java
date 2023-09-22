package com.example.foodtruck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static final long DELAY_IN_MILLISECONDS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                navigateToNextActivity();
            }
        }, DELAY_IN_MILLISECONDS);
    }
    private void navigateToNextActivity() {
        Intent intent = new Intent(MainActivity.this, second.class);
        startActivity(intent);

    }
}
