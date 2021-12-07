package com.example.appprestador.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appprestador.Login;
import com.example.appprestador.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileEmployee extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    public RelativeLayout rlData;
    public RelativeLayout rlLogout;
    public TextView txtNameEmp;

    public String id, idBuss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_employee);

        getSupportActionBar().hide();
        getIds();

        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        return true;
                    case R.id.requests:
                        Intent itR = new Intent(getApplicationContext(), Requests.class);
                        itR.putExtra("id", id);
                        itR.putExtra("idBuss", idBuss);
                        overridePendingTransition(0,0);
                        startActivity(itR);
                        return true;
                    case R.id.home:
                        Intent itH = new Intent(getApplicationContext(), HomeEmployee.class);
                        itH.putExtra("id", id);
                        itH.putExtra("idBuss", idBuss);
                        overridePendingTransition(0,0);
                        startActivity(itH);
                        return true;
                }
                return false;
            }
        });

        rlData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ProfileEmployee.this, MyDataEmployee.class);
                it.putExtra("id", id);
                it.putExtra("idBuss", idBuss);
                startActivity(it);
            }
        });

        rlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileEmployee.this, Login.class));
                finish();
            }
        });
    }

    public void getIds(){
        id = getIntent().getStringExtra("id");
        idBuss = getIntent().getStringExtra("idBuss");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        rlData = findViewById(R.id.rl_data);
        rlLogout = findViewById(R.id.rl_logout);
        txtNameEmp = findViewById(R.id.txt_name_employee);
    }
}