package com.google.lostfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.permissionx.guolindev.PermissionX;

public class MainActivity extends AppCompatActivity {
    private Button createNew, showAll, showOnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNew = findViewById(R.id.createNew);
        createNew.setOnClickListener(new View.OnClickListener() { // Set click listener for the button to start the respective activity
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NewActivity.class));
            }
        });

        showAll = findViewById(R.id.showAll);
        showAll.setOnClickListener(new View.OnClickListener() {  // Set click listener for the button to start the respective activity
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AllActivity.class));
            }
        });

        showOnMap = findViewById(R.id.showOnMap);
        showOnMap.setOnClickListener(new View.OnClickListener() {  // Set click listener for the button to start the respective activity
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShowAllActivity.class));
            }
        });

        // Initialize permissions
        initPermissions();
    }

    private void initPermissions() {
        // Use PermissionX library to request permissions
        PermissionX.init(this)
                .permissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request((allGranted, grantedList, deniedList) -> {
                    // Check if all permissions are granted
                    if (!allGranted) {
                        // If any permission is denied, show a toast message
                        Toast.makeText(this, "The following permissions are prohibited to run" + grantedList + "Please allow them and restart the app", Toast.LENGTH_LONG).show();
                    }
                });
    }
}