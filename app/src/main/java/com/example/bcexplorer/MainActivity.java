package com.example.bcexplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private Toast currentToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener((@NonNull MenuItem item) -> {
            if (currentToast != null)
                currentToast.cancel(); // Cancel currently displaying toast before showing a new one

            switch (item.getItemId()) {
                case R.id.bottom_nav_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutContainer, new HomeFragment()).commit();

                    // Test toast
                    currentToast = Toast.makeText(MainActivity.this, "Home fragment to be implemented", Toast.LENGTH_SHORT);

                    break;
                case R.id.bottom_nav_saved:
                    // TODO: Start fragment/activity from here
                    getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutContainer, new BlankFragment()).commit();

                    // Test toast
                    currentToast = Toast.makeText(MainActivity.this, "Saved fragment to be implemented", Toast.LENGTH_SHORT);

                    break;
                case R.id.bottom_nav_info:
                    // TODO: Start fragment/activity from here
                    getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutContainer, new BlankFragment()).commit();

                    // Test toast
                    currentToast = Toast.makeText(MainActivity.this, "Info fragment to be implemented", Toast.LENGTH_SHORT);

                    break;
            }
//            currentToast.show();

            return true;
        });

        // Show home page at start
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
    }
}