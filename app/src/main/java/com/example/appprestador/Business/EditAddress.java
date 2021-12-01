package com.example.appprestador.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appprestador.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

public class EditAddress extends AppCompatActivity {

    ImageView imgBack;
    TextInputEditText edtNewAddress;
    TextInputEditText edtNewNeighborhood;
    TextInputEditText edtNewComplement;
    TextInputEditText edtNewNumber;
    TextInputEditText edtNewCity;
    TextInputEditText edtNewUF;
    TextInputEditText edtNewCep;
    AppCompatButton btnEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        getSupportActionBar().hide();
        getIds();
        maskFormat();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAddress.this, Profile.class);
                startActivity(intent);
                finish();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = edtNewAddress.getText().toString();
                String number = edtNewNumber.getText().toString();
                String neighborhood = edtNewNeighborhood.getText().toString();
                String complement = edtNewComplement.getText().toString();
                String city = edtNewCity.getText().toString();
                String uf = edtNewUF.getText().toString();
                String cep = edtNewCep.getText().toString();

                boolean checkValidations = validationEdit(address, number, neighborhood, city, uf, cep);

                if(checkValidations == true){
                    Toast.makeText(EditAddress.this, "Sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getIds(){
        imgBack = findViewById(R.id.img_back);
        edtNewAddress = findViewById(R.id.edt_new_address);
        edtNewNumber = findViewById(R.id.edt_new_num);
        edtNewNeighborhood = findViewById(R.id.edt_new_neigh);
        edtNewComplement = findViewById(R.id.edt_new_comp);
        edtNewCity = findViewById(R.id.edt_new_city);
        edtNewUF = findViewById(R.id.edt_new_uf);
        edtNewCep = findViewById(R.id.edt_new_cep);
        btnEditar = findViewById(R.id.btn_edit_address);
    }

    public void maskFormat(){
        SimpleMaskFormatter mask_uf = new SimpleMaskFormatter("LL");
        MaskTextWatcher mtw_uf = new MaskTextWatcher(edtNewUF, mask_uf);
        edtNewUF.addTextChangedListener(mtw_uf);

        SimpleMaskFormatter mask_cep = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher mtw_cep = new MaskTextWatcher(edtNewCep, mask_cep);
        edtNewCep.addTextChangedListener(mtw_cep);
    }

    public Boolean validationEdit(String address, String number, String neighborhood,
                                  String city, String uf, String cep){
        if(address.length() == 0){
            edtNewAddress.requestFocus();
            edtNewAddress.setError("Campo vazio.");
            return false;
        } else if (number.length() == 0) {
            edtNewNumber.requestFocus();
            edtNewNumber.setError("Campo vazio.");
            return false;
        } else if (neighborhood.length() == 0){
            edtNewNeighborhood.requestFocus();
            edtNewNeighborhood.setError("Campo vazio.");
            return false;
        } else if (city.length() == 0){
            edtNewCity.requestFocus();
            edtNewCity.setError("Campo vazio");
            return false;
        } else if (uf.length() == 0){
            edtNewUF.requestFocus();
            edtNewUF.setError("Campo vazio.");
            return false;
        } else if (uf.length() != 2){
            edtNewUF.requestFocus();
            edtNewUF.setError("UF inválido!");
            return false;
        } else if (cep.length() == 0){
            edtNewCep.requestFocus();
            edtNewCep.setError("Campo vazio.");
            return false;
        } else if (cep.length() != 9){
            edtNewCep.requestFocus();
            edtNewCep.setError("CEP inválido!");
            return false;
        } else {
            return true;
        }
    }
}