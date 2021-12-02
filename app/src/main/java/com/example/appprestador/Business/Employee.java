package com.example.appprestador.Business;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.appprestador.Model.Business;
import com.example.appprestador.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class Employee extends AppCompatActivity {


    public String id;
    Business business = new Business();
    Employee employee = new Employee();

    //Connection MySQL
    //String HOST = "http://172.20.10.5/vulcar_database/";
    //String HOST = "http://192.168.15.127/vulcar_database/";
    String HOST = "http://192.168.0.106/vulcar_database/";
    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;

    public BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        getSupportActionBar().hide();
        getIds();

        cliente = new AsyncHttpClient();

        bottomNavigationView.setSelectedItemId(R.id.employee);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.employee:
                        return true;
                    case R.id.home:
                        Intent intent_h = new Intent(Employee.this, Home.class);
                        intent_h.putExtra("id", id);
                        startActivity(intent_h);
                        return true;
                    case R.id.profile:
                        Intent intent_p = new Intent(Employee.this, Profile.class);
                        intent_p.putExtra("id", id);
                        startActivity(intent_p);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    private void getIds(){
        id = getIntent().getStringExtra("id");
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }
}