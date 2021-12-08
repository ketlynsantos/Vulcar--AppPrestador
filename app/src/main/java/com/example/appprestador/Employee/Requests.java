package com.example.appprestador.Employee;

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

import com.example.appprestador.AdapterBusiness.AdapterEmployees;
import com.example.appprestador.AdapterEmployee.AdapterRequests;
import com.example.appprestador.Business.DataEmployee;
import com.example.appprestador.Business.Employee;
import com.example.appprestador.Employee.HomeEmployee;
import com.example.appprestador.Employee.ProfileEmployee;
import com.example.appprestador.Model.ItemsBudget;
import com.example.appprestador.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Requests extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    //List View para lista os serviços que estão em espera
    public ListView lvServiceWait;
    public String id, idBuss;
    Activity context;

    //Connection MySQL
    //String HOST = "http://172.20.10.5/vulcar_database/";
    String HOST = "http://192.168.15.112/vulcar_database/Employee/";
    //String HOST = "http://172.20.10.6/Vulcar--Syncmysql/Employee/";

    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;
    ItemsBudget ibudget = new ItemsBudget();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        getSupportActionBar().hide();
        getIds();
        cliente = new AsyncHttpClient();
        context = Requests.this;

        montaObj();

        bottomNavigationView.setSelectedItemId(R.id.requests);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.requests:
                        return true;
                    case R.id.home:
                        Intent itH = new Intent(getApplicationContext(), HomeEmployee.class);
                        overridePendingTransition(0,0);
                        itH.putExtra("id", id);
                        itH.putExtra("idBuss", idBuss);
                        startActivity(itH);
                        return true;
                    case R.id.profile:
                        Intent itP = new Intent(getApplicationContext(), ProfileEmployee.class);
                        overridePendingTransition(0,0);
                        itP.putExtra("id", id);
                        itP.putExtra("idBuss", idBuss);
                        startActivity(itP);
                        return true;
                }
                return false;
            }
        });
    }

    private void montaObj() {
        String url = HOST + "Select/select_services_wait.php";

        ibudget.setIdLoja(idBuss);
        params.put("id", idBuss);

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listarRequests(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void listarRequests(String resposta) {
        final ArrayList<ItemsBudget> lista = new ArrayList<>();

        try {
            JSONArray jsonarray = new JSONArray(resposta);

            for (int i = 0; i < jsonarray.length(); i++){
                ItemsBudget b = new ItemsBudget();

                b.setId(jsonarray.getJSONObject(i).getString("id"));
                b.setIdOrcamento(jsonarray.getJSONObject(i).getString("idOrca"));
                b.setNomeServico(jsonarray.getJSONObject(i).getString("servico"));
                b.setIdServico(jsonarray.getJSONObject(i).getString("idservico"));
                b.setDescServico(jsonarray.getJSONObject(i).getString("descservico"));
                b.setIdAutomovel(jsonarray.getJSONObject(i).getString("idauto"));
                b.setModeloAutomovel(jsonarray.getJSONObject(i).getString("modelo"));
                b.setIdLoja(jsonarray.getJSONObject(i).getString("idloja"));
                b.setNomeLoja(jsonarray.getJSONObject(i).getString("nomeloja"));
                b.setEndereço(jsonarray.getJSONObject(i).getString("endereco"));
                b.setNum(jsonarray.getJSONObject(i).getString("num"));
                b.setBairro(jsonarray.getJSONObject(i).getString("bairro"));
                b.setCep(jsonarray.getJSONObject(i).getString("cep"));

                lista.add(b);

            }

            AdapterRequests adapter = new AdapterRequests(this, R.layout.adapter_services_wait, R.id.txt_id, lista, id, idBuss, cliente);
            lvServiceWait.setAdapter(adapter);

        } catch(Exception erro) {
            Log.d("erro", "erro"+erro);
        }
    }


    public void getIds() {
        id = getIntent().getStringExtra("id");
        idBuss = getIntent().getStringExtra("idBuss");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        lvServiceWait = findViewById(R.id.lv_serv_wait);
    }
}