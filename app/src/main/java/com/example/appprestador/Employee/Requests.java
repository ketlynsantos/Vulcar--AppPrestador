package com.example.appprestador.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.appprestador.Employee.HomeEmployee;
import com.example.appprestador.Employee.ProfileEmployee;
import com.example.appprestador.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Requests extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    //List View para lista os serviços que estão em espera
    public ListView lvServiceWait;
    public String id, idBuss;

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
                        Intent itH = new Intent(getApplicationContext(), HomeEmployee.class);
                        overridePendingTransition(0,0);
                        itH.putExtra("id", id);
                        itH.putExtra("idBuss", idBuss);
                        startActivity(itH);
                        return true;
                    case R.id.profile:
                        Intent itP = new Intent(getApplicationContext(), ProfileEmployee.class);
                        overridePendingTransition(0,0);
                        itP.putExtra("id", id);
                        itP.putExtra("idBuss", idBuss);
                        startActivity(itP);
                        return true;
                }
                return false;
            }
        });
    }

    public void getIds() {
        id = getIntent().getStringExtra("id");
        idBuss = getIntent().getStringExtra("idBuss");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        lvServiceWait = findViewById(R.id.lv_serv_wait);
    }
}