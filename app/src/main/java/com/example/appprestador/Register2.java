package com.example.appprestador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class Register2 extends AppCompatActivity {

    EditText edtEndereco, edtComplemento, edtNumero, edtBairro, edtCidade, edtUf, edtCEP;
    AppCompatButton btnRegister2;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        getSupportActionBar().hide();
        getIds();
        maskFormat();

        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getIntent().getStringExtra("name");
                String cnpj = getIntent().getStringExtra("cnpj");
                String email = getIntent().getStringExtra("email");
                String phone = getIntent().getStringExtra("phone");
                String password = getIntent().getStringExtra("password");

                String endereco = edtEndereco.getText().toString();
                String complemento = edtComplemento.getText().toString();
                String numero = edtNumero.getText().toString();
                String bairro = edtBairro.getText().toString();
                String cidade = edtCidade.getText().toString();
                String uf = edtUf.getText().toString();
                String cep = edtCEP.getText().toString();

                boolean checkValidations = validationRegister(endereco, numero, bairro,
                                                                 cidade, uf, cep);

                if(checkValidations == true){
                    Toast.makeText(Register2.this, "Sucesso!"+ " "+ name +" "+ complemento, Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register2.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getIds(){
        imgBack = findViewById(R.id.img_back);
        edtEndereco = findViewById(R.id.edt_endereco);
        edtComplemento = findViewById(R.id.edt_complemento);
        edtNumero = findViewById(R.id.edt_num);
        edtBairro = findViewById(R.id.edt_bairro);
        edtCidade = findViewById(R.id.edt_cidade);
        edtUf = findViewById(R.id.edt_uf);
        edtCEP = findViewById(R.id.edt_cep);

        btnRegister2 = findViewById(R.id.btn_register2);
    }

    public void maskFormat(){
        SimpleMaskFormatter mask_uf = new SimpleMaskFormatter("LL");
        MaskTextWatcher mtw_uf = new MaskTextWatcher(edtUf, mask_uf);
        edtUf.addTextChangedListener(mtw_uf);

        SimpleMaskFormatter mask_cep = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher mtw_cep = new MaskTextWatcher(edtCEP, mask_cep);
        edtCEP.addTextChangedListener(mtw_cep);
    }

    public Boolean validationRegister (String endereco, String numero,
                                       String bairro, String cidade, String uf, String cep){
        if(endereco.length() == 0){
            edtEndereco.requestFocus();
            edtEndereco.setError("Campo vazio.");
            return false;
        } else if(numero.length() == 0){
            edtNumero.requestFocus();
            edtNumero.setError("Campo vazio.");
            return false;
        } else if(bairro.length() == 0){
            edtBairro.requestFocus();
            edtBairro.setError("Campo vazio.");
            return false;
        } else if(cidade.length() == 0){
            edtCidade.requestFocus();
            edtCidade.setError("Campo vazio.");
            return false;
        } else if(uf.length() == 0){
            edtUf.requestFocus();
            edtUf.setError("Campo vazio.");
            return false;
        } else if(uf.length() != 2){
            edtUf.requestFocus();
            edtUf.setError("UF inválido!");
            return false;
        } else if(cep.length() == 0){
            edtCEP.requestFocus();
            edtCEP.setError("Campo vazio.");
            return false;
        } else if(cep.length() != 9){
            edtCEP.requestFocus();
            edtCEP.setError("CEP inválido!");
            return false;
        } else {
            return true;
        }
    }
}