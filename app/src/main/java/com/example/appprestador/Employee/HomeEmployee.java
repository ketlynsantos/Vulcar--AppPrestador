package com.example.appprestador.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.appprestador.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeEmployee extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    public TextView txtNameServices;
    public TextView txtAddress;
    public TextView txtNeigh;
    public TextView txtCep;
    public TextView txtNameClient;
    public TextView txtModelVehicle;
    public TextView txtStatus;
    public Switch swStatusEmp;

    public String id, idBuss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_employee);

        getSupportActionBar().hide();
        getIds();

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.requests:
                        startActivity(new Intent(getApplicationContext(), Requests.class));
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

    public void getIds() {
        id = getIntent().getStringExtra("id");
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        txtNameServices = findViewById(R.id.txt_name_services);
        txtAddress = findViewById(R.id.txt_address);
        txtNeigh = findViewById(R.id.txt_neigh);
        txtCep = findViewById(R.id.edt_cep);
        txtNameClient = findViewById(R.id.txt_name_client);
        txtModelVehicle = findViewById(R.id.txt_model_car);
        txtStatus = findViewById(R.id.txt_status);
        swStatusEmp = findViewById(R.id.switch_status_employee);
    }
}