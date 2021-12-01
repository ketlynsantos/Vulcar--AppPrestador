package com.example.appprestador.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appprestador.R;
import com.google.android.material.textfield.TextInputEditText;

public class EditPasswordBusiness extends AppCompatActivity {

    public ImageView imgBack;
    public TextInputEditText edtPassword;
    public TextInputEditText edtNewPassword;
    public AppCompatButton btnAlter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password_business);

        getSupportActionBar().hide();
        getIds();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(EditPasswordBusiness.this, MyDataBusiness.class);
                startActivity(itI);
                finish();
            }
        });

//        btnAlter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void getIds() {
        imgBack = findViewById(R.id.img_back);
        edtPassword = findViewById(R.id.edt_password_business);
        edtNewPassword = findViewById(R.id.edt_new_password_business);
        btnAlter = findViewById(R.id.btn_alter_password);
    }
}