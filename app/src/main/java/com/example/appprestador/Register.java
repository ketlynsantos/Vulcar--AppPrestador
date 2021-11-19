package com.example.appprestador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class Register extends AppCompatActivity {

    EditText edtName, edtCnpj, edtTelefone, edtEmail, edtPassword;
    ImageButton btnRegister;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        getIds();
        maskFormat();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String cnpj = edtCnpj.getText().toString();
                String email = edtEmail.getText().toString();
                String phone = edtTelefone.getText().toString();
                String password = edtPassword.getText().toString();

                boolean checkValidations = validationRegister(name, cnpj, email, phone, password);
                Intent intent = new Intent(Register.this, Register2.class);

                if(checkValidations == true){
                    intent.putExtra("name", name);
                    intent.putExtra("cnpj", cnpj);
                    intent.putExtra("email", email);
                    intent.putExtra("phone", phone);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });


    }


    public void getIds(){
        edtName = findViewById(R.id.edt_name);
        edtCnpj = findViewById(R.id.edt_cnpj);
        edtEmail = findViewById(R.id.edt_email);
        edtTelefone = findViewById(R.id.edt_phone);
        edtPassword = findViewById(R.id.edt_password);
        btnRegister = findViewById(R.id.btn_register);
        imgBack = findViewById(R.id.img_back);
    }

    public void maskFormat(){
        SimpleMaskFormatter mask_cnpj = new SimpleMaskFormatter("NN-NNN.NNN/NNNN-NN");
        MaskTextWatcher mtw_cnpj = new MaskTextWatcher(edtCnpj, mask_cnpj);
        edtCnpj.addTextChangedListener(mtw_cnpj);

        SimpleMaskFormatter mask_tel = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtw_tel = new MaskTextWatcher(edtTelefone, mask_tel);
        edtTelefone.addTextChangedListener(mtw_tel);
    }

    private Boolean validationRegister(String name, String cnpj, String email, String phone, String password){
        if(name.length() == 0){
            edtName.requestFocus();
            edtName.setError("Campo vazio.");
            return false;
        } else if(cnpj.length() == 0){
            edtCnpj.requestFocus();
            edtCnpj.setError("Campo vazio.");
            return false;
        } else if (cnpj.length() != 18){
            edtCnpj.requestFocus();
            edtCnpj.setError("CNPJ inválido!");
            return false;
        } else if(email.length() == 0){
            edtEmail.requestFocus();
            edtEmail.setError("Campo vazio.");
            return false;
        } else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            edtEmail.requestFocus();
            edtEmail.setError("Email inválido!");
            return false;
        } else if(phone.length() == 0){
            edtTelefone.requestFocus();
            edtTelefone.setError("Campo vazio.");
            return false;
        } else if(phone.length() != 15){
            edtTelefone.requestFocus();
            edtTelefone.setError("Telefone inválido!");
            return false;
        } else if(password.length() == 0){
            edtPassword.requestFocus();
            edtPassword.setError("Campo vazio.");
            return false;
        } else if (password.length() < 5){
            edtPassword.requestFocus();
            edtPassword.setError("Mínimo 6 caracteres.");
            return false;
        } else {
            return true;
        }
    }
}