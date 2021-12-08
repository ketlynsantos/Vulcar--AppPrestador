package com.example.appprestador.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.appprestador.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.locks.ReentrantReadWriteLock;

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
    public RelativeLayout rlActualServ;
    public RelativeLayout rlNoServ;

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
                        Intent itR = new Intent(getApplicationContext(), Requests.class);
                        itR.putExtra("id", id);
                        itR.putExtra("idBuss", idBuss);
                        overridePendingTransition(0,0);
                        startActivity(itR);
                        finish();
                        return true;
                    case R.id.profile:
                        Intent itP = new Intent(getApplicationContext(), ProfileEmployee.class);
                        itP.putExtra("id", id);
                        itP.putExtra("idBuss", idBuss);
                        overridePendingTransition(0,0);
                        startActivity(itP);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    private void montaObj() {

    }

    public void getIds() {
        id = getIntent().getStringExtra("id");
        idBuss = getIntent().getStringExtra("idBuss");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        txtNameServices = findViewById(R.id.txt_name_services);
        txtAddress = findViewById(R.id.txt_address);
        txtNeigh = findViewById(R.id.txt_neigh);
        txtCep = findViewById(R.id.edt_cep);
        txtNameClient = findViewById(R.id.txt_name_client);
        txtModelVehicle = findViewById(R.id.txt_model_car);
        txtStatus = findViewById(R.id.txt_status);
        //swStatusEmp = findViewById(R.id.switch_status_employee);
        rlActualServ = findViewById(R.id.rl_serv_actual);
        rlNoServ = findViewById(R.id.rl_no_actual_serv);
    }
}