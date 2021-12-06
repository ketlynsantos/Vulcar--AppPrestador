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
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class EditPasswordBusiness extends AppCompatActivity {

    public ImageView imgBack;
    public TextInputEditText edtPassword;
    public TextInputEditText edtNewPassword;
    public AppCompatButton btnAlter;
    public String id;

    Business business = new Business();
    //Connection MySQL
    //String HOST = "http://192.168.15.108/vulcar_database/";
    //String HOST = "http://172.20.10.5/vulcar_database/";
    //String HOST = "http://192.168.15.129/vulcar_database/Business/";
    String HOST = "http://192.168.0.105/Vulcar--Syncmysql/Business/";

    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password_business);
        cliente = new AsyncHttpClient();

        getSupportActionBar().hide();
        getIds();
        verifyBan();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(EditPasswordBusiness.this, MyDataBusiness.class);
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
        business.setId(id);
        business.setPassword(oldPass);
        updatePassword(business, newPass);
    }

    private void updatePassword(Business business, String newPass) {
        String url = HOST+"update_pass.php";

        params.put("id", business.getId());
        params.put("old_pass", business.getPassword());
        params.put("new_pass", newPass);

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject result = new JSONObject(new String(responseBody));
                        if(result.getString("UPDATE").equals("true")){
                            Toast.makeText(EditPasswordBusiness.this, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditPasswordBusiness.this, MyDataBusiness.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        } else {
                            Toast.makeText(EditPasswordBusiness.this, "Senha atual incorreta", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(EditPasswordBusiness.this, "Erro ao trocar senha!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditPasswordBusiness.this, "Erro ao trocar senha!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void verifyBan() {
        String url = HOST+"Select/select_business.php";
        business.setId(id);
        params.put("id", business.getId());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    try {
                        JSONObject jsonarray = new JSONObject(new String(responseBody));
                        if (jsonarray.getString("STATUS_ID").equals("5")) {
                            Intent intent = new Intent(EditPasswordBusiness.this, Login.class);
                            Toast.makeText(EditPasswordBusiness.this, "Estabelecimento banido!", Toast.LENGTH_SHORT).show();
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
        edtPassword = findViewById(R.id.edt_password_business);
        edtNewPassword = findViewById(R.id.edt_new_password_business);
        btnAlter = findViewById(R.id.btn_alter_password);
    }
}