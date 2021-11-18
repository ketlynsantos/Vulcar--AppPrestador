package com.example.appprestador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class Login extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton rbEmpresa, rbFuncionario;
    RelativeLayout relativeBusiness, relativeEmployee;
    EditText editCnpj, editPassBuss, editCpf, editPassEmployee;
    TextView txtRegister;
    AppCompatButton btnLoginBusiness, btnLoginEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        getIds();
        maskFormat();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Verificar qual Radio button está selecionado
                switch (checkedId){
                    case R.id.rb_empresa:
                        relativeBusiness.setVisibility(View.VISIBLE);
                        relativeEmployee.setVisibility(View.GONE);
                        break;
                    case R.id.rb_funcionario:
                        relativeBusiness.setVisibility(View.GONE);
                        relativeEmployee.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });


        btnLoginBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Home.class);
                startActivity(intent);
                finish();

            }
        });

        btnLoginEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login. this, HomeEmployee.class);
                startActivity(intent);
                finish();
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });


    }

    public void getIds(){
        radioGroup = findViewById(R.id.radio_group);
        rbEmpresa = findViewById(R.id.rb_empresa);
        rbFuncionario = findViewById(R.id.rb_funcionario);
        relativeBusiness = findViewById(R.id.relative_business);
        relativeEmployee = findViewById(R.id.relative_employee);

        btnLoginBusiness = findViewById(R.id.btn_login_business);
        btnLoginEmployee = findViewById(R.id.btn_login_employee);

        editCnpj = findViewById(R.id.edit_cnpj);
        editPassBuss = findViewById(R.id.edit_password_buss);
        editCpf = findViewById(R.id.edit_cpf);
        editPassEmployee = findViewById(R.id.edit_password_employee);

        txtRegister = findViewById(R.id.txt_register);
    }



    public void maskFormat(){
        SimpleMaskFormatter mask_cnpj = new SimpleMaskFormatter("NN-NNN.NNN/NNNN-NN");
        MaskTextWatcher mtw_cnpj = new MaskTextWatcher(editCnpj, mask_cnpj);
        editCnpj.addTextChangedListener(mtw_cnpj);

        SimpleMaskFormatter mask_cpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw_cpf = new MaskTextWatcher(editCpf, mask_cpf);
        editCpf.addTextChangedListener(mtw_cpf);
    }
}