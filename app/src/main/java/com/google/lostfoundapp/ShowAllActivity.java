package com.google.lostfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.lostfoundapp.bean.Item;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {
    private MapView mapView;  // Reference to the MapView object
    private List<Item> items = new ArrayList<>();  // List to store items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        // Retrieve all items from the database by using LitePal
        items = LitePal.findAll(Item.class);
        // Get the MapView object from the layout
        mapView = findViewById(R.id.mapView);
        // Initialize the map with item positions
        initPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();  // Resume the MapView when the activity is resumed
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();  // Pause the MapView when the activity is paused
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();  // Destroy the MapView when the activity is destroyed
        mapView.getMap().setMyLocationEnabled(false);  // Disable the My Location feature on the map
    }

    private void initPosition() {
        //BitmapDescriptor bitmapA = BitmapDescriptorFactory.fromResource(R.drawable.marker);
        for (Item item : items) {  // Iterate over each item and add a marker on the map for each item's position
            // Define the coordinates for the marker
            LatLng point = new LatLng(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLon()));
            // Create BitmapDescriptor for the marker icon
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.marker);
            // Create MarkerOptions for adding a marker to the map
            OverlayOptions option = new MarkerOptions()
                    .position(point)  // Set the position of the marker
                    .icon(bitmap);  // Set the icon for the marker

            //Set the map's center position to the device's location
            MapStatus mMapStatus = new MapStatus.Builder().target(point).build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mapView.getMap().setMapStatus(mMapStatusUpdate);

            //Add marker to the map and display it
            //mapView.getMap().clear();
            mapView.getMap().addOverlay(option);
        }
    }
}