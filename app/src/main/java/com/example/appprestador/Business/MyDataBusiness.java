package com.example.appprestador.Business;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.appprestador.Login;
import com.example.appprestador.Model.Business;
import com.example.appprestador.Model.Services;
import com.example.appprestador.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MyDataBusiness extends AppCompatActivity {

    public ImageView imgBack;
    public RelativeLayout rlData;
    public RelativeLayout rlContact;
    public RelativeLayout rlPassword;
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
        setContentView(R.layout.activity_my_data_business);

        cliente = new AsyncHttpClient();

        getSupportActionBar().hide();
        getIds();
        verifyBan();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(MyDataBusiness.this, Profile.class);
                itI.putExtra("id", id);
                startActivity(itI);
            }
        });

        rlData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itD = new Intent(MyDataBusiness.this, EditDataBusiness.class);
                itD.putExtra("id", id);
                startActivity(itD);
            }
        });

        rlContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itC = new Intent(MyDataBusiness.this, EditDataContact.class);
                itC.putExtra("id", id);
                startActivity(itC);
            }
        });

        rlPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itP = new Intent(MyDataBusiness.this, EditPasswordBusiness.class);
                itP.putExtra("id", id);
                startActivity(itP);
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
                            Intent intent = new Intent(MyDataBusiness.this, Login.class);
                            Toast.makeText(MyDataBusiness.this, "Estabelecimento banido!", Toast.LENGTH_SHORT).show();
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
        rlData = findViewById(R.id.rl_data_business);
        rlContact = findViewById(R.id.rl_data_contact);
        rlPassword = findViewById(R.id.rl_alter_password);
    }
}