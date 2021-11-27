package com.example.appprestador.Employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.appprestador.R;

public class EditDataEmployee extends AppCompatActivity {

    public EditText edtNewName;
    public EditText edtNewCPF;
    public EditText edtNewEmail;
    public EditText edtNewPhone;
    public AppCompatButton btnEditData;
    public ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_employee);

        getSupportActionBar().hide();
        getIds();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itImg = new Intent(EditDataEmployee.this, ProfileEmployee.class);
                startActivity(itImg);
                finish();
            }
        });

//        btnEditData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void getIds() {
        edtNewName = findViewById(R.id.edt_new_name_emp);
        edtNewCPF = findViewById(R.id.edt_new_cpf_emp);
        edtNewEmail = findViewById(R.id.edt_new_email_emp);
        edtNewPhone = findViewById(R.id.edt_new_phone_emp);
        btnEditData = findViewById(R.id.btn_edit_data_emp);
        imgBack = findViewById(R.id.img_back);
    }
}