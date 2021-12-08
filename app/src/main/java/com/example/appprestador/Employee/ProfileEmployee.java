package com.example.appprestador.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appprestador.Business.Profile;
import com.example.appprestador.Login;
import com.example.appprestador.Model.Business;
import com.example.appprestador.Model.Employee;
import com.example.appprestador.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileEmployee extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    public RelativeLayout rlData;
    public RelativeLayout rlLogout;
    public TextView txtNameEmp;

    public String id, idBuss;

    //Connection MySQL
    //String HOST = "http://172.20.10.5/vulcar_database/";
    String HOST = "http://192.168.15.112/vulcar_database/Employee/";
    //String HOST = "http://172.20.10.6/Vulcar--Syncmysql/Employee/";

    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;

    Employee employee = new Employee();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_employee);

        getSupportActionBar().hide();
        getIds();
        cliente = new AsyncHttpClient();
        montaObj();

        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        return true;
                    case R.id.requests:
                        Intent itR = new Intent(getApplicationContext(), Requests.class);
                        itR.putExtra("id", id);
                        itR.putExtra("idBuss", idBuss);
                        overridePendingTransition(0,0);
                        startActivity(itR);
                        return true;
                    case R.id.home:
                        Intent itH = new Intent(getApplicationContext(), HomeEmployee.class);
                        itH.putExtra("id", id);
                        itH.putExtra("idBuss", idBuss);
                        overridePendingTransition(0,0);
                        startActivity(itH);
                        return true;
                }
                return false;
            }
        });

        rlData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ProfileEmployee.this, MyDataEmployee.class);
                it.putExtra("id", id);
                it.putExtra("idBuss", idBuss);
                startActivity(it);
            }
        });

        rlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileEmployee.this, Login.class));
                //finish();
            }
        });
    }

    private void montaObj() {
        String url = HOST + "Select/select_employee.php";

        employee.setId(id);
        params.put("id", employee.getId());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    try {
                        JSONObject jsonarray = new JSONObject(new String(responseBody));
                        String nome = jsonarray.getString("FUNCIONARIO_NOME");
                        txtNameEmp.setText(nome);
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

    public void getIds(){
        id = getIntent().getStringExtra("id");
        idBuss = getIntent().getStringExtra("idBuss");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        rlData = findViewById(R.id.rl_data_emp);
        rlLogout = findViewById(R.id.rl_logout);
        txtNameEmp = findViewById(R.id.txt_name_employee);
    }
}