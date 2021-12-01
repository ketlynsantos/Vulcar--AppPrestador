package com.example.appprestador.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appprestador.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

public class EditDataBusiness extends AppCompatActivity {

    public ImageView imgBack;
    public TextInputEditText edtName;
    public TextInputEditText edtCNPJ;
    public AppCompatButton btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_business);

        getSupportActionBar().hide();
        getIds();
        maskFormat();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(EditDataBusiness.this, MyDataBusiness.class);
                startActivity(itI);
                finish();
            }
        });
    }

    private void getIds() {
        imgBack = findViewById(R.id.img_back);
        edtName = findViewById(R.id.edt_name_business);
        edtCNPJ = findViewById(R.id.edt_cnpj_businesss);
        btnEdit = findViewById(R.id.btn_edit_data);
    }

    public void maskFormat() {
        SimpleMaskFormatter mask_cnpj = new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN");
        MaskTextWatcher mtw_cnpj = new MaskTextWatcher(edtCNPJ, mask_cnpj);
        edtCNPJ.addTextChangedListener(mtw_cnpj);
    }
}