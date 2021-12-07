package com.example.appprestador.Employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.appprestador.Business.EditDataBusiness;
import com.example.appprestador.Business.EditDataContact;
import com.example.appprestador.Business.EditPasswordBusiness;
import com.example.appprestador.Business.MyDataBusiness;
import com.example.appprestador.Business.Profile;
import com.example.appprestador.R;

public class MyDataEmployee extends AppCompatActivity {

    public ImageView imgBack;
    public RelativeLayout rlData;
    public RelativeLayout rlContact;
    public RelativeLayout rlPassword;
    public String id, idBuss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data_employee);

        getSupportActionBar().hide();
        getIds();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(MyDataEmployee.this, ProfileEmployee.class);
                itI.putExtra("id", id);
                itI.putExtra("idBuss", idBuss);
                startActivity(itI);
            }
        });

        rlData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itD = new Intent(MyDataEmployee.this, EditDataEmployee.class);
                itD.putExtra("id", id);
                itD.putExtra("idBuss", idBuss);
                startActivity(itD);
            }
        });

        rlContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itC = new Intent(MyDataEmployee.this, EditDataContactEmployee.class);
                itC.putExtra("id", id);
                itC.putExtra("idBuss", idBuss);
                startActivity(itC);
            }
        });

        rlPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itP = new Intent(MyDataEmployee.this, EditPasswordEmployee.class);
                itP.putExtra("id", id);
                itP.putExtra("idBuss", idBuss);
                startActivity(itP);
            }
        });
    }

    private void getIds() {
        id = getIntent().getStringExtra("id");
        idBuss = getIntent().getStringExtra("idBuss");

        imgBack = findViewById(R.id.img_back);
        rlData = findViewById(R.id.rl_data_emp);
        rlContact = findViewById(R.id.rl_data_contact);
        rlPassword = findViewById(R.id.rl_alter_password);
    }
}