package com.example.appprestador.Employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appprestador.Business.EditPasswordBusiness;
import com.example.appprestador.Business.MyDataBusiness;
import com.example.appprestador.Model.Employee;
import com.example.appprestador.R;
import com.google.android.material.textfield.TextInputEditText;

public class EditPasswordEmployee extends AppCompatActivity {

    public ImageView imgBack;
    public TextInputEditText edtPassword;
    public TextInputEditText edtNewPassword;
    public AppCompatButton btnAlter;

    public String id, idBuss;
    public Employee employee = new Employee();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password_employee);

        getSupportActionBar().hide();
        getIds();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(EditPasswordEmployee.this, MyDataEmployee.class);
                itI.putExtra("id", id);
                itI.putExtra("idBuss", idBuss);
                startActivity(itI);
                finish();
            }
        });

        btnAlter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaObj();
            }
        });

    }

    private void montaObj() {
        String newPass, oldPass;
        newPass = edtNewPassword.getText().toString();
        oldPass = edtPassword.getText().toString();

        employee.setId(id);
        employee.setPassword(oldPass);
        //updatePassword(employee, newPass);
    }

    private void getIds() {
        id = getIntent().getStringExtra("id");
        idBuss = getIntent().getStringExtra("idBuss");

        imgBack = findViewById(R.id.img_back);
        edtPassword = findViewById(R.id.edt_password_emp);
        edtNewPassword = findViewById(R.id.edt_new_password_emp);
        btnAlter = findViewById(R.id.btn_alter_password);
    }
}