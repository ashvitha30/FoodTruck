// ReceiveActivity.java
package com.example.foodtruck;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class recieve extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private DonationAdapter donationAdapter;
    String ngoName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve);
        Intent intent = getIntent();
         ngoName = intent.getStringExtra("ngoName");

        dbHelper = new DatabaseHelper(this);

        RecyclerView recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        donationAdapter = new DonationAdapter();
        recyclerView.setAdapter(donationAdapter);

        loadDonations();
    }

    private void loadDonations() {
        List<Donation> donations = dbHelper.getAllDonations(ngoName);
        donationAdapter.setDonations(donations);
    }
}
