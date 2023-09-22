package com.example.foodtruck;// DonateActivity.java
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class DonateActivity extends AppCompatActivity {

    private static final int SMS_REQUEST_CODE = 101;

    private DatabaseHelper dbHelper;
    private TextView textViewReceiver;
    private EditText name;
    private EditText number;
    private EditText type;
    private EditText quantity;
    private EditText address;
    private Button donateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        dbHelper = new DatabaseHelper(this);
        textViewReceiver = findViewById(R.id.reciever);
        name = findViewById(R.id.editTextTextPersonName);
        type = findViewById(R.id.editTextTextPersonName2);
        number = findViewById(R.id.editTextNumber);
        quantity = findViewById(R.id.editTextNumber2);
        address = findViewById(R.id.editTextTextPersonName3);
        donateButton = findViewById(R.id.buttonDonate);

        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndSendNotification();
            }

        });

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    textViewReceiver.setText("");
                }
                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String query = searchView.getQuery().toString().trim();
                    if (!query.isEmpty()) {
                        textViewReceiver.setText(query);
                    }
                }
            }
        });
    }

    private void checkAndSendNotification() {
        String receiverName = textViewReceiver.getText().toString().trim();
        if (!receiverName.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm Donation");
            builder.setMessage("Do you want to donate to " + receiverName);
            String phoneNumber = dbHelper. getNGOPhoneNumber(receiverName);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sendNotificationToNGO(receiverName, phoneNumber);
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();
        } else {
            Toast.makeText(this, "Please select an NGO", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendNotificationToNGO(String receiverName, String phoneNumber) {
        String Name = name.getText().toString().trim();
        String itemType = type.getText().toString().trim();
        String itemQuantityString = quantity.getText().toString().trim();
        String itemAddress = address.getText().toString().trim();
        String ngoName = textViewReceiver.getText().toString().trim();
        if (Name.isEmpty() || itemType.isEmpty() || itemQuantityString.isEmpty() || itemAddress.isEmpty()) {
            Toast.makeText(this, "Please enter all donation details", Toast.LENGTH_SHORT).show();
            return;
        }

        int itemQuantity = Integer.parseInt(itemQuantityString);

        // Create a Donation object with the entered details
        Donation donation = new Donation(Name,itemType, itemQuantity, itemAddress,ngoName);

        // Insert the donation into the database
        dbHelper.insertDonation(donation);

        String message = "You have received a donation!\n\n" +
                "Donor Name: " + Name + "\n" +
                "Item Type: " + itemType + "\n" +
                "Item Quantity: " + itemQuantity + "\n" +
                "Item Address: " + itemAddress;

        // Send the notification via SMS
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "Donation sent successfully", Toast.LENGTH_SHORT).show();
            clearDonationDetails();
        } else {
            requestPermission(Manifest.permission.SEND_SMS, SMS_REQUEST_CODE);
        }
    }


    private void clearDonationDetails() {
        name.setText("");
        type.setText("");
        quantity.setText("");
        address.setText("");
        textViewReceiver.setText("");
    }

    private boolean checkPermission(String permission) {
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permission, int requestCode) {
        this.requestPermissions(new String[]{permission}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied. Please grant the permission to send the donation.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
