package com.example.appprestador.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appprestador.Login;
import com.example.appprestador.Model.Business;
import com.example.appprestador.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class EditDataBusiness extends AppCompatActivity {

    public ImageView imgBack;
    public TextInputEditText edtName;
    public TextInputEditText edtCNPJ;
    public AppCompatButton btnEdit;

    public String id;

    Business business = new Business();
    //Connection MySQL
    //String HOST = "http://172.20.10.5/vulcar_database/";
    String HOST = "http://192.168.15.112/vulcar_database/Business/";
    //String HOST = "http://172.20.10.6/Vulcar--Syncmysql/Business/";

    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_business);

        cliente = new AsyncHttpClient();

        getSupportActionBar().hide();
        getIds();
        montaObj();
        maskFormat();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(EditDataBusiness.this, MyDataBusiness.class);
                itI.putExtra("id", id);
                startActivity(itI);
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String cnpj = edtCNPJ.getText().toString();

                boolean checkValidations = validationEdit(name, cnpj);
                if(checkValidations == true){
                    business.setId(id);
                    business.setNome(name);
                    business.setCnpj(cnpj);
                    updateData(business);
                }
            }
        });
    }

    public Boolean validationEdit(String name, String cnpj){
        if(name.length() == 0){
            edtName.requestFocus();
            edtName.setError("Campo vazio.");
            return false;
        } else if (cnpj.length() == 0) {
            edtCNPJ.requestFocus();
            edtCNPJ.setError("Campo vazio.");
            return false;
        } else if (cnpj.length() != 18) {
            edtCNPJ.requestFocus();
            edtCNPJ.setError("CNPJ inválido!");
            return false;
        } else {
            return true;
        }
    }

    private void updateData(Business business) {
        String url = HOST+"update_data.php";

        params.put("id", business.getId());
        params.put("name", business.getNome());
        params.put("cnpj", business.getCnpj());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    Toast.makeText(EditDataBusiness.this, "Dados atualizados!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditDataBusiness.this, MyDataBusiness.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void montaObj() {
        String url = HOST+"Select/select_business.php";
        business.setId(id);
        params.put("id", business.getId());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    try {
                        JSONObject jsonarray = new JSONObject(new String(responseBody));
                        String nome = jsonarray.getString("LOJA_NOME");
                        edtName.setText(nome);
                        String cnpj = jsonarray.getString("LOJA_CNPJ");
                        edtCNPJ.setText(cnpj);
                        if (jsonarray.getString("STATUS_ID").equals("5")) {
                            Intent intent = new Intent(EditDataBusiness.this, Login.class);
                            Toast.makeText(EditDataBusiness.this, "Estabelecimento banido!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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