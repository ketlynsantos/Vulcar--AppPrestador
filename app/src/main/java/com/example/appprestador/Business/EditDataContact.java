package com.example.appprestador.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appprestador.R;
import com.google.android.material.textfield.TextInputEditText;

public class EditDataContact extends AppCompatActivity {

    public ImageView imgBack;
    public TextInputEditText edtEmail;
    public TextInputEditText edtPhone;
    public AppCompatButton btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_contact);

        getSupportActionBar().hide();
        getIds();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(EditDataContact.this, MyDataBusiness.class);
                startActivity(itI);
                finish();
            }
        });

//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void getIds() {
        imgBack = findViewById(R.id.img_back);
        edtEmail = findViewById(R.id.edt_email_business);
        edtPhone = findViewById(R.id.edt_phone_businesss);
        btnEdit = findViewById(R.id.btn_edit_contact);
    }
}