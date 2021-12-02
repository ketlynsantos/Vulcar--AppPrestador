package com.example.appprestador.Business;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

public class Employee extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    String id;

    //Connection MySQL
    //String HOST = "http://172.20.10.5/vulcar_database/";
    //String HOST = "http://192.168.0.106/vulcar_database/";
    String HOST = "http://192.168.15.123/vulcar_database/Business/";
    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;

    Business business = new Business();
    com.example.appprestador.Model.Employee employee = new com.example.appprestador.Model.Employee();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        getSupportActionBar().hide();
        getIds();

        cliente = new AsyncHttpClient();

        montaObj();

        bottomNavigationView.setSelectedItemId(R.id.employee);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.employee:
                        return true;
                    case R.id.home:
                        Intent intent_h = new Intent(Employee.this, Home.class);
                        intent_h.putExtra("id", id);
                        startActivity(intent_h);
                        return true;
                    case R.id.profile:
                        Intent intent_p = new Intent(Employee.this, Profile.class);
                        intent_p.putExtra("id", id);
                        startActivity(intent_p);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
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
                if (statusCode == 200) {
                    try {
                        JSONObject jsonarray = new JSONObject(new String(responseBody));
                        if (jsonarray.getString("STATUS_ID").equals("5")) {
                            Intent intent = new Intent(Employee.this, Login.class);
                            Toast.makeText(Employee.this, "Estabelecimento banido!", Toast.LENGTH_SHORT).show();
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

    private void getIds(){
        id = getIntent().getStringExtra("id");
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }
}