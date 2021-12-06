package com.example.appprestador.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appprestador.R;

public class DataEmployee extends AppCompatActivity {

    public ImageView imgBack;
    public TextView txtNameEmployee;
    public TextView txtCpf;
    public TextView txtEmail;
    public TextView txtphone;
    public AppCompatButton btnRemoveEmployee;

    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_employee);

        getSupportActionBar().hide();
        getIds();

        txtNameEmployee.setText(getIntent().getStringExtra("nameEmp"));
        txtCpf.setText(getIntent().getStringExtra("cpfEmp"));
        txtEmail.setText(getIntent().getStringExtra("emailEmp"));
        txtphone.setText(getIntent().getStringExtra("phoneEmp"));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(DataEmployee.this, Employee.class);
                itI.putExtra("id", id);
                startActivity(itI);
                finish();
            }
        });

//        btnRemoveEmployee.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void getIds() {
        id = getIntent().getStringExtra("id");

        imgBack = findViewById(R.id.img_back);
        txtNameEmployee = findViewById(R.id.txt_name_employee);
        txtCpf = findViewById(R.id.txt_cpf);
        txtEmail = findViewById(R.id.txt_email);
        txtphone = findViewById(R.id.edt_phone);
        btnRemoveEmployee = findViewById(R.id.btn_remove);
    }
}