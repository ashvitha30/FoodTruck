package com.example.foodtruck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodtruck.DatabaseHelper;
import com.example.foodtruck.Fourth;
import com.example.foodtruck.R;

public class NgoLogin extends AppCompatActivity {
    EditText nu1, np1;
    Button ngob2;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngologin);

        nu1 = findViewById(R.id.nu1);
        np1 = findViewById(R.id.np1);
        ngob2 = findViewById(R.id.ngob2);
        databaseHelper = new DatabaseHelper(this);

        ngob2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = nu1.getText().toString().trim();
                String password = np1.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(NgoLogin.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the entered username and password match the predefined credentials
                    boolean isValid = isValidCredentials(username, password);
                    if (isValid) {
                        Toast.makeText(NgoLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        // Proceed to the next activity
                        Intent intent = new Intent(NgoLogin.this, Fourth.class);
                        intent.putExtra("ngoName",username);
                        startActivity(intent);
                        // Clear the input fields
                        nu1.setText("");
                        np1.setText("");
                    } else {
                        Toast.makeText(NgoLogin.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isValidCredentials(String username, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_PASSWORD};
        String selection = DatabaseHelper.COLUMN_NAME + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(DatabaseHelper.TABLE_NGOS, projection, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PASSWORD));
            cursor.close();
            return password.equals(storedPassword);
        }
        return false;
    }

}
