package com.example.appprestador.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.appprestador.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileEmployee extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    public AppCompatButton btnEdit;
    public TextView txtNameProfile;
    public TextView txtCpf;
    public TextView txtEmail;
    public TextView txtPhone;

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
                        startActivity(new Intent(getApplicationContext(), Requests.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeEmployee.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ProfileEmployee.this, EditDataEmployee.class);
                startActivity(it);
            }
        });
    }

    public void getIds(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        btnEdit = findViewById(R.id.btn_edit_data);
        txtNameProfile = findViewById(R.id.txt_name_employee);
        txtCpf = findViewById(R.id.txt_cpf);
        txtEmail = findViewById(R.id.txt_email);
        txtPhone = findViewById(R.id.txt_phone);
    }
}