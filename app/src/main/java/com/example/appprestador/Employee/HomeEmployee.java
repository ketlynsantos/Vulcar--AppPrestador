package com.example.appprestador.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appprestador.Business.EditPasswordBusiness;
import com.example.appprestador.Business.MyDataBusiness;
import com.example.appprestador.Business.Profile;
import com.example.appprestador.Login;
import com.example.appprestador.Model.ItemsBudget;
import com.example.appprestador.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import cz.msebera.android.httpclient.Header;

public class HomeEmployee extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    public TextView txtNameServices;
    public TextView txtAddress;
    public TextView txtNeigh;
    public TextView txtCep;
    public TextView txtNameClient;
    public TextView txtModelVehicle;
    public TextView txtStatus;
    public Switch swStatusEmp;
    public RelativeLayout rlActualServ;
    public RelativeLayout rlNoServ;
    public AppCompatButton btnConcluided;

    public String id, idBuss, idOrca, servico, endereco, num, bairro, cep, uf, nomeCliente, modelo;

    //Connection MySQL
    //String HOST = "http://172.20.10.5/vulcar_database/";
    String HOST = "http://192.168.15.112/vulcar_database/Employee/";
    //String HOST = "http://172.20.10.6/Vulcar--Syncmysql/Employee/";

    RequestParams params = new RequestParams();
    RequestParams params2 = new RequestParams();
    AsyncHttpClient cliente;
    ItemsBudget ibudget = new ItemsBudget();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_employee);

        getSupportActionBar().hide();
        getIds();
        cliente = new AsyncHttpClient();
        montaObj();
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.requests:
                        Intent itR = new Intent(getApplicationContext(), Requests.class);
                        itR.putExtra("id", id);
                        itR.putExtra("idBuss", idBuss);
                        overridePendingTransition(0,0);
                        startActivity(itR);
                        finish();
                        return true;
                    case R.id.profile:
                        Intent itP = new Intent(getApplicationContext(), ProfileEmployee.class);
                        itP.putExtra("id", id);
                        itP.putExtra("idBuss", idBuss);
                        overridePendingTransition(0,0);
                        startActivity(itP);
                        finish();
                        return true;
                }
                return false;
            }
        });

        btnConcluided.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibudget.setIdOrcamento(idOrca);
                updateService(ibudget);
            }
        });
    }

    private void updateService(ItemsBudget ibudget) {
        String url = HOST + "update_status_orc_complete.php";

        params2.put("id", idOrca);
        params2.put("idFunc", id);

        cliente.post(url, params2, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Intent intent = new Intent(HomeEmployee.this, HomeEmployee.class);
                    intent.putExtra("id", id);
                    intent.putExtra("idBuss", idBuss);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(HomeEmployee.this, "Erro!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void montaObj() {
        String url = HOST + "Select/select_actual_service.php";

        ibudget.setIdFuncionario(id);
        params.put("id", ibudget.getIdFuncionario());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    try {
                        JSONObject jsonarray = new JSONObject(new String(responseBody));
                        idOrca = jsonarray.getString("IDORCA");
                        servico = jsonarray.getString("servico");
                        modelo = jsonarray.getString("modelo");
                        endereco = jsonarray.getString("enderco");
                        num = jsonarray.getString("num");
                        bairro = jsonarray.getString("bairro");
                        cep = jsonarray.getString("cep");
                        uf = jsonarray.getString("uf");
                        nomeCliente = jsonarray.getString("nomecliente");

                        txtNameClient.setText(nomeCliente);
                        txtNameServices.setText(servico);
                        txtModelVehicle.setText(modelo);
                        txtAddress.setText(endereco+", "+num);
                        txtCep.setText(cep);
                        txtNeigh.setText(bairro);
                        txtCep.setText(cep);
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

    public void getIds() {
        id = getIntent().getStringExtra("id");
        idBuss = getIntent().getStringExtra("idBuss");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        btnConcluided = findViewById(R.id.btn_concluided);
        txtNameServices = findViewById(R.id.txt_name_services);
        txtAddress = findViewById(R.id.txt_address);
        txtNeigh = findViewById(R.id.txt_neigh);
        txtCep = findViewById(R.id.txt_cep);
        txtNameClient = findViewById(R.id.txt_name_client);
        txtModelVehicle = findViewById(R.id.txt_model_car);
        txtStatus = findViewById(R.id.txt_status);
        //swStatusEmp = findViewById(R.id.switch_status_employee);
        rlActualServ = findViewById(R.id.rl_serv_actual);
        rlNoServ = findViewById(R.id.rl_no_actual_serv);
    }
}