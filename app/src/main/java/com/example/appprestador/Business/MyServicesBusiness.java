package com.example.appprestador.Business;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.appprestador.R;

public class MyServicesBusiness extends AppCompatActivity {

    public ImageView imgBack;
    public RelativeLayout rlAddService;
    public ListView lvServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_services_business);

        getSupportActionBar().hide();
        getIds();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(MyServicesBusiness.this, Profile.class);
                startActivity(itI);
            }
        });
    }

    private void getIds() {
        imgBack = findViewById(R.id.img_back);
        rlAddService = findViewById(R.id.rl_add_service);
        lvServices = findViewById(R.id.lvServices);
    }
}