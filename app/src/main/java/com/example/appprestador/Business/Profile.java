package com.example.appprestador.Business;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appprestador.Business.Employee;
import com.example.appprestador.Business.Home;
import com.example.appprestador.Login;
import com.example.appprestador.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    public ImageView imgLogo;
    public TextView txtNameBusiness;
    public RelativeLayout rlData;
    public RelativeLayout rlServices;
    public RelativeLayout rlAddress;
    public RelativeLayout rlLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().hide();
        getIds();
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        return true;
                    case R.id.employee:
                        startActivity(new Intent(getApplicationContext(), Employee.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

//        Só tirar as barras
//        rlData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        rlServices.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        rlAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itAddress = new Intent(Profile.this, MyAddress.class);
                startActivity(itAddress);
            }
        });

        rlAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Login.class));
                finish();
            }
        });
    }

    private void getIds() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        imgLogo = findViewById(R.id.img_logo_business);
        txtNameBusiness = findViewById(R.id.txt_name_profile);
        rlData = findViewById(R.id.rl_data);
        rlServices = findViewById(R.id.rl_services);
        rlAddress = findViewById(R.id.rl_address);
        rlLogout = findViewById(R.id.rl_logout);
    }
}