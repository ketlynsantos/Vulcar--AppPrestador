package com.example.appprestador.Business;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appprestador.Login;
import com.example.appprestador.Model.Business;
import com.example.appprestador.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Profile extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    public ImageView imgLogo;
    public TextView txtNameBusiness;
    public RelativeLayout rlData;
    public RelativeLayout rlServices;
    public RelativeLayout rlAddress;
    public RelativeLayout rlLogout;
    public String id;
    public TextView txtStatus;
    public Switch swStatusBusiness;

    //Connection MySQL
    //String HOST = "http://172.20.10.5/vulcar_database/";
    //String HOST = "http://192.168.0.106/vulcar_database/";
    //String HOST = "http://192.168.15.129/vulcar_database/Business/";
    String HOST = "http://172.20.10.6/Vulcar--Syncmysql/Business/";

    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;

    Business business = new Business();
    Employee employee = new Employee();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().hide();
        getIds();
        cliente = new AsyncHttpClient();
        montaObj();
        montaObjStatus();

        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        return true;
                    case R.id.employee:
                        Intent intent_e = new Intent(Profile.this, Employee.class);
                        intent_e.putExtra("id", id);
                        startActivity(intent_e);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

        swStatusBusiness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true) {
                    openBusiness();
                } else {
                    closeBusiness();
                }
            }
        });

        rlData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itD = new Intent(Profile.this, MyDataBusiness.class);
                itD.putExtra("id", id);
                startActivity(itD);
            }
        });

        rlServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itS = new Intent(Profile.this, MyServicesBusiness.class);
                itS.putExtra("id", id);
                startActivity(itS);
            }
        });

        rlAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itAddress = new Intent(Profile.this, EditAddress.class);
                itAddress.putExtra("id", id);
                startActivity(itAddress);
            }
        });

        rlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Login.class));
                finish();
            }
        });
    }

    private void montaObj() {
        String url = HOST + "Select/select_business.php";

        business.setId(id);
        params.put("id", business.getId());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    try {
                        JSONObject jsonarray = new JSONObject(new String(responseBody));
                        String nome = jsonarray.getString("LOJA_NOME");
                        txtNameBusiness.setText(nome);
                        if (jsonarray.getString("STATUS_ID").equals("5")) {
                            Intent intent = new Intent(Profile.this, Login.class);
                            Toast.makeText(Profile.this, "Estabelecimento banido!", Toast.LENGTH_SHORT).show();
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

    private void montaObjStatus() {
        business.setId(id);
        verifyStatus(business);
    }

    private void verifyStatus(Business business) {
        String url = HOST + "Select/select_business.php";

        params.put("id", business.getId());
        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    try{
                        JSONObject result = new JSONObject(new String(responseBody));
                        if(result.getString("STATUS_ID").equals("13")){
                            swStatusBusiness.setChecked(true);
                        } else if(result.getString("STATUS_ID").equals("14")){
                            swStatusBusiness.setChecked(false);
                        } else if(result.getString("STATUS_ID").equals("1")){
                            swStatusBusiness.setChecked(false);
                        } else if(result.getString("STATUS_ID").equals("5")){
                            Intent intent = new Intent(Profile.this, Login.class);
                            Toast.makeText(Profile.this, "Estabelecimento banido!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        } else{
                            Toast.makeText(employee, "Erro!", Toast.LENGTH_SHORT).show();
                        }
                    }catch(JSONException e){
                        Toast.makeText(employee, "Erro!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void openBusiness() {
        String url = HOST + "Select/select_business.php";

        params.put("id", business.getId());
        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    try{
                        JSONObject result = new JSONObject(new String(responseBody));
                        if(!result.getString("STATUS_ID").equals("5")){
                            int sts = 13;
                            business.setId(id);
                            business.setSts(sts);

                            String url = HOST + "update_status.php";
                            params.put("id", business.getId());
                            params.put("sts", business.getSts());
                            cliente.post(url, params, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    if(statusCode == 200){
                                        txtStatus.setText("Aberto");
                                    } else{
                                        txtStatus.setText("Fechado");
                                        Toast.makeText(employee, "Falha ao abrir!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                }
                            });
                        } else {
                            Intent intent = new Intent(Profile.this, Login.class);
                            Toast.makeText(Profile.this, "Estabelecimento banido!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }
                    }catch(JSONException e){
                        Toast.makeText(employee, "Erro!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void closeBusiness() {
        String url = HOST + "Select/select_business.php";

        params.put("id", business.getId());
        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    try{
                        JSONObject result = new JSONObject(new String(responseBody));
                        if(!result.getString("STATUS_ID").equals("5")){
                            int sts = 14;
                            business.setId(id);
                            business.setSts(sts);

                            String url = HOST + "update_status.php";
                            params.put("id", business.getId());
                            params.put("sts", business.getSts());
                            cliente.post(url, params, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    if(statusCode == 200){
                                        txtStatus.setText("Fechado");
                                    } else{
                                        txtStatus.setText("Aberto");
                                        Toast.makeText(employee, "Falha ao fechar!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                }
                            });
                        } else {
                            Intent intent = new Intent(Profile.this, Login.class);
                            Toast.makeText(Profile.this, "Estabelecimento banido!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }
                    }catch(JSONException e){
                        Toast.makeText(employee, "Erro!", Toast.LENGTH_SHORT).show();
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

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        imgLogo = findViewById(R.id.img_logo_business);
        txtNameBusiness = findViewById(R.id.txt_name_profile);
        rlData = findViewById(R.id.rl_data);
        rlServices = findViewById(R.id.rl_services);
        rlAddress = findViewById(R.id.rl_address);
        rlLogout = findViewById(R.id.rl_logout);
        txtStatus = findViewById(R.id.txt_status);
        swStatusBusiness = findViewById(R.id.switch_status_business);
    }
}