package com.example.appprestador.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.appprestador.Employee.HomeEmployee;
import com.example.appprestador.Employee.ProfileEmployee;
import com.example.appprestador.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Requests extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        getSupportActionBar().hide();
        getIds();


        bottomNavigationView.setSelectedItemId(R.id.requests);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.requests:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeEmployee.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileEmployee.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    public void getIds(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }
}