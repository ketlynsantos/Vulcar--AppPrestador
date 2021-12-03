package com.example.appprestador.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appprestador.Model.Business;
import com.example.appprestador.Model.Services;
import com.example.appprestador.R;
import com.example.appprestador.Register;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class RegisterServices extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton rbSelect, rbAll;
    TextInputEditText edtName, edtDesc, edtValue;
    ImageView imgBack;
    Spinner spiCategory;
    AppCompatButton btnAdd;
    public String id, categoryId;

    Services services = new Services();
    //Connection MySQL
    //String HOST = "http://192.168.15.108/vulcar_database/Business/";
    //String HOST = "http://172.20.10.5/vulcar_database/Business/";
    String HOST = "http://192.168.0.106/vulcar_database/Business/";
    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;

    Activity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_services);

        cliente = new AsyncHttpClient();
        context = RegisterServices.this;
        Services services = new Services();

        getSupportActionBar().hide();
        getIds();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(RegisterServices.this, MyServicesBusiness.class);
                itI.putExtra("id", id);
                startActivity(itI);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaObj();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Verificar qual Radio button está selecionado
                switch (checkedId){
                    case R.id.rb_all:
                        spiCategory.setVisibility(View.GONE);
                        categoryId = "12";
                        break;
                    case R.id.rb_funcionario:
                        spiCategory.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        spiCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)view).setTextColor(Color.WHITE);
                switch(adapterView.getItemAtPosition(i).toString()){
                    case "CATEGORIA":
                        spiCategory.requestFocus();
                        Toast.makeText(RegisterServices.this, "Selecione uma categoria!", Toast.LENGTH_SHORT).show();
                        break;
                    case "RACING":
                        categoryId = "1";
                        break;
                    case "SEDAN":
                        categoryId = "2";
                        break;
                    case "PICKUP":
                        categoryId = "3";
                        break;
                    case "UTILITÁRIO":
                        categoryId = "4";
                        break;
                    case "WAGON":
                        categoryId = "5";
                        break;
                    case "HATCH":
                        categoryId = "6";
                        break;
                    case "MOTOCICLETA":
                        categoryId = "7";
                        break;
                    case "COUPÊ":
                        categoryId = "8";
                        break;
                    case "SUV":
                        categoryId = "9";
                        break;
                    case "OFF-ROAD":
                        categoryId = "10";
                        break;
                    case "OUTRO":
                        categoryId = "11";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void montaObj() {
        String name = edtName.getText().toString();
        String desc = edtDesc.getText().toString();
        String value = edtValue.getText().toString();
        String category = categoryId;
        services.setId_loja(id);
        services.setNome(name);
        services.setDesc(desc);
        services.setValor(value);
        services.setId_categoria(category);
        registerService(services);
    }

    private void registerService(Services services) {
        String url = HOST+"register_services.php";

        params.put("id", services.getId_loja());
        params.put("name", services.getNome());
        params.put("desc", services.getDesc());
        params.put("value", services.getValor());
        params.put("category", services.getId_categoria());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    Intent intent = new Intent(RegisterServices.this, RegisterServices.class);
                    intent.putExtra("id", id);
                    Toast.makeText(RegisterServices.this, "Registrado com sucesso!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    private void getIds() {
        id = getIntent().getStringExtra("id");
        imgBack = findViewById(R.id.img_back);
        edtName = findViewById(R.id.edt_name_service);
        edtDesc = findViewById(R.id.edt_desc_service);
        edtValue = findViewById(R.id.edt_value);
        radioGroup = findViewById(R.id.radio_group);
        rbSelect = findViewById(R.id.rb_select);
        rbAll = findViewById(R.id.rb_all);
        spiCategory = findViewById(R.id.spinner_category);
        btnAdd = findViewById(R.id.btn_add);
    }
}