package com.example.appprestador.Business;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.appprestador.AdapterBusiness.AdapterEmployees;
import com.example.appprestador.AdapterBusiness.AdapterServices;
import com.example.appprestador.Login;
import com.example.appprestador.Model.Business;
import com.example.appprestador.Model.Services;
import com.example.appprestador.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Employee extends AppCompatActivity {

    public ListView lvEmployees;
    public BottomNavigationView bottomNavigationView;
    public RelativeLayout rlAddEmployee;
    String id, idBuss;

    com.example.appprestador.Model.Employee employee = new com.example.appprestador.Model.Employee();
    Business business = new Business();
    //Connection MySQL
    //String HOST = "http://172.20.10.5/vulcar_database/";
    String HOST = "http://172.20.10.6/Vulcar--Syncmysql/Business/";
    //String HOST = "http://192.168.15.129/vulcar_database/Business/";
    //String HOST = "http://192.168.0.13/Vulcar--Syncmysql/Business/";

    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;

    Activity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        getSupportActionBar().hide();
        getIds();

        cliente = new AsyncHttpClient();
        context = Employee.this;

        montaObj();
        carregarEmployees();

        bottomNavigationView.setSelectedItemId(R.id.employee);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.employee:
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

        rlAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Employee.this, RegisterEmployee.class);
                it.putExtra("id", id);
                startActivity(it);
            }
        });
    }

    private void carregarEmployees() {
        String url = HOST + "Select/select_employee.php";

        employee.setId_loja(id);
        params.put("id", employee.getId_loja());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listarEmplooyes(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void listarEmplooyes(String resposta) {
        final ArrayList<com.example.appprestador.Model.Employee> lista = new ArrayList<>();

        try {
            JSONArray jsonarray = new JSONArray(resposta);

            for (int i = 0; i < jsonarray.length(); i++){
                com.example.appprestador.Model.Employee e = new com.example.appprestador.Model.Employee();

                e.setId(jsonarray.getJSONObject(i).getString("id"));
                e.setNome(jsonarray.getJSONObject(i).getString("nome"));
                e.setCpf(jsonarray.getJSONObject(i).getString("cpf"));
                e.setEmail(jsonarray.getJSONObject(i).getString("email"));
                e.setPhone(jsonarray.getJSONObject(i).getString("tel"));
                e.setId_loja(jsonarray.getJSONObject(i).getString("id_loja"));
                e.setLoja_nome(jsonarray.getJSONObject(i).getString("nome_loja"));
                e.setId_status(jsonarray.getJSONObject(i).getString("id_status"));
                e.setStatus_nome(jsonarray.getJSONObject(i).getString("status_nome"));

                lista.add(e);

            }

            AdapterEmployees adapter = new AdapterEmployees(context, R.layout.adapter_employees, R.id.txt_id, lista);
            lvEmployees.setAdapter(adapter);

            lvEmployees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
                    Intent it = new Intent(Employee.this, DataEmployee.class);
                    it.putExtra("idEmp", lista.get(position).getId());
                    it.putExtra("nameEmp", lista.get(position).getNome());
                    it.putExtra("cpfEmp", lista.get(position).getCpf());
                    it.putExtra("emailEmp", lista.get(position).getEmail());
                    it.putExtra("phoneEmp", lista.get(position).getPhone());
                    it.putExtra("idBus", lista.get(position).getId_loja());
                    it.putExtra("nameBus", lista.get(position).getLoja_nome());
                    it.putExtra("id", id);
                    startActivity(it);
                    finish();
                }
            });

        } catch(Exception erro) {
            Log.d("erro", "erro"+erro);
        }
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
        lvEmployees = findViewById(R.id.lv_employees);
        rlAddEmployee = findViewById(R.id.rl_add_employee);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }
}