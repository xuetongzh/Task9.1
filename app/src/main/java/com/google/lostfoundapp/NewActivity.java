package com.google.lostfoundapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.google.lostfoundapp.bean.Item;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class NewActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_REGISTER = 1;
    private List<Item> items = new ArrayList<>();
    private int itemId;

    private EditText name, phone, description, date;
    private TextView locationEt;
    private RadioGroup radioGroup;
    private Button save, current;
    private LocationClient mLocationClient;
    private MyLocationListener myListener = new MyLocationListener();
    private String mName, mPhone, mDescription, mDate, mLocation, mType;
    private String lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        // Retrieve existing items from the database
        items = LitePal.findAll(Item.class);
        itemId = items.size() + 1;  // Assign the next available ID for a new item

        // Initialize UI components
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        locationEt = findViewById(R.id.location);

        // Set a click listener for the location EditText field to pick a location
        locationEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(NewActivity.this, PickLocationActivity.class), REQUEST_CODE_REGISTER);
            }
        });

        // Initialize the radio group and set a listener to determine the selected type (Lost/Found)
        radioGroup = findViewById(R.id.radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.lost:
                        mType = "Lost";
                        break;
                    case R.id.found:
                        mType = "Found";
                        break;
                }
            }
        });

        // Initialize the location client for obtaining the current location
        try {
            mLocationClient = new LocationClient(this);
            LocationClientOption option = new LocationClientOption();
            option.setIsNeedAddress(true);
            option.setNeedNewVersionRgc(true);
            mLocationClient.setLocOption(option);
            mLocationClient.registerLocationListener(myListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set a click listener for the Current button to fetch the current location
        current = findViewById(R.id.current);
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLocationClient == null) {
                    Toast.makeText(NewActivity.this, "mLocationClient is null", Toast.LENGTH_SHORT).show();
                    return;
                }
                mLocationClient.start();
            }
        });

        // Set a click listener for the Save button to save the item
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve input values
                mName = name.getText().toString();
                mPhone = phone.getText().toString();
                mDescription = description.getText().toString();
                mDate = date.getText().toString();
                mLocation = locationEt.getText().toString();

                // Check if any field is empty
                if (mName.isEmpty() || mPhone.isEmpty() || mDescription.isEmpty() || mDate.isEmpty() || mLocation.isEmpty() || mType.isEmpty()) {
                    Toast.makeText(NewActivity.this, "Please enter the full content", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save the item to the database
                if (new Item(itemId, mType, mName, mPhone, mDescription, mDate, mLocation, lat, lon).save()) {
                    Toast.makeText(NewActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(NewActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Custom location listener to receive location updates
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // The BDLocation here is a class that contains information about the location result. Its various get methods can be used to gain all the relevant location results.
            // Only partial address-related result information is listed below
            // Retrieve latitude and longitude of the location
            lat = String.valueOf(location.getLatitude());
            lon = String.valueOf(location.getLongitude());
            locationEt.setText(location.getAddrStr());
        }
    }

    @Override
    protected void onDestroy() {
        // Stop the location client when the activity is destroyed
        mLocationClient.stop();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle the result from the PickLocationActivity
        if (requestCode == REQUEST_CODE_REGISTER && resultCode == PickLocationActivity.RESULT_CODE_REGISTER && data != null) {
            Bundle extras = data.getExtras();
            // Retrieve the selected address, latitude, and longitude
            String address = extras.getString("address", "");
            String lat = extras.getString("lat", "");
            String lon = extras.getString("lon", "");
            // Update the location with the selected address
            locationEt.setText(address);
            // Update the latitude and longitude values
            this.lat = lat;
            this.lon = lon;
        }
    }
}