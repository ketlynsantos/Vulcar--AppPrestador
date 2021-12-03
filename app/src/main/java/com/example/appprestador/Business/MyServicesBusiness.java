package com.example.appprestador.Business;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.appprestador.AdapterBusiness.AdapterServices;
import com.example.appprestador.Model.Services;
import com.example.appprestador.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyServicesBusiness extends AppCompatActivity {

    public ImageView imgBack;
    public RelativeLayout rlAddService;
    public ListView lvServices;
    public String id;

    Services services = new Services();
    //Connection MySQL
    //String HOST = "http://192.168.15.108/vulcar_database/";
    //String HOST = "http://172.20.10.5/vulcar_database/";
    String HOST = "http://192.168.15.129/vulcar_database/";
    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;

    Activity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_services_business);

        cliente = new AsyncHttpClient();
        context = MyServicesBusiness.this;

        getSupportActionBar().hide();
        getIds();

        carregarServices();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(MyServicesBusiness.this, Profile.class);
                itI.putExtra("id", id);
                startActivity(itI);
            }
        });
    }

    private void carregarServices() {
        String url = HOST + "Business/Select/select_services.php";
        services.setId_loja(id);
        params.put("id", services.getId_loja());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listarServicos(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void listarServicos(String resposta) {
        final ArrayList<Services> lista = new ArrayList<>();

        try {
            JSONArray jsonarray = new JSONArray(resposta);

            for (int i = 0; i < jsonarray.length(); i++){
                Services s = new Services();

                s.setId(jsonarray.getJSONObject(i).getString("id"));
                s.setNome(jsonarray.getJSONObject(i).getString("nome"));
                s.setDesc(jsonarray.getJSONObject(i).getString("desc"));
                s.setValor(jsonarray.getJSONObject(i).getString("valor"));
                s.setId_categoria(jsonarray.getJSONObject(i).getString("id_categoria"));
                s.setNome_categoria(jsonarray.getJSONObject(i).getString("nome_categoria"));
                s.setId_loja(jsonarray.getJSONObject(i).getString("id_loja"));
                s.setNome_loja(jsonarray.getJSONObject(i).getString("nome_loja"));

                lista.add(s);

            }

            AdapterServices adapter = new AdapterServices(context, R.layout.adapter_services, R.id.txt_id, lista);
            lvServices.setAdapter(adapter);

        } catch(Exception erro) {
            Log.d("erro", "erro"+erro);
        }
    }

    private void getIds() {
        id = getIntent().getStringExtra("id");
        imgBack = findViewById(R.id.img_back);
        rlAddService = findViewById(R.id.rl_add_service);
        lvServices = findViewById(R.id.lv_services);
    }
}