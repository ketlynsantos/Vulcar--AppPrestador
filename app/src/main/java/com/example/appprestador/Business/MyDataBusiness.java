package com.example.appprestador.Business;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.appprestador.R;

public class MyDataBusiness extends AppCompatActivity {

    public ImageView imgBack;
    public RelativeLayout rlData;
    public RelativeLayout rlContact;
    public RelativeLayout rlPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data_business);

        getSupportActionBar().hide();
        getIds();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(MyDataBusiness.this, Profile.class);
                startActivity(itI);
            }
        });

        rlData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itD = new Intent(MyDataBusiness.this, EditDataBusiness.class);
                startActivity(itD);
            }
        });

        rlContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itC = new Intent(MyDataBusiness.this, EditDataContact.class);
                startActivity(itC);
            }
        });
    }

    private void getIds() {
        imgBack = findViewById(R.id.img_back);
        rlData = findViewById(R.id.rl_data_business);
        rlContact = findViewById(R.id.rl_data_contact);
        rlPassword = findViewById(R.id.rl_alter_password);
    }
}