package com.example.appprestador.AdapterEmployee;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.appprestador.Business.Profile;
import com.example.appprestador.Employee.HomeEmployee;
import com.example.appprestador.Login;
import com.example.appprestador.Model.ItemsBudget;
import com.example.appprestador.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AdapterRequests extends ArrayAdapter<ItemsBudget> {
    int groupid;
    ArrayList<ItemsBudget> lista;
    Context context;
    String idFunc, idBus;
    AsyncHttpClient cliente;
    RequestParams params = new RequestParams();

    public AdapterRequests(@NonNull Context context, int vg, int id, ArrayList<ItemsBudget> lista, String idFunc, String idBus, AsyncHttpClient AsyncHttpClient) {
        super(context, vg, id, lista);
        this.context = context;
        this.groupid = vg;
        this.lista = lista;
        this.idFunc = idFunc;
        this.idBus = idBus;
        this.cliente = AsyncHttpClient;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(groupid, parent, false);

        TextView txtNameServices = itemView.findViewById(R.id.txt_name_services);
        TextView txtDescServices = itemView.findViewById(R.id.txt_desc_services);
        TextView txtModelCar = itemView.findViewById(R.id.txt_model_car);
        TextView txtAddress = itemView.findViewById(R.id.txt_address);
        TextView txtNeigh = itemView.findViewById(R.id.txt_neigh);
        TextView txtCep = itemView.findViewById(R.id.txt_cep);
        TextView txtIdServ = itemView.findViewById(R.id.txt_id_serv);
        TextView txtId = itemView.findViewById(R.id.txt_id);
        TextView txtIdOrc = itemView.findViewById(R.id.txt_id_orca);
        AppCompatButton btn = itemView.findViewById(R.id.btn_accept_serv);

        txtId.setText(lista.get(position).getId());
        txtIdOrc.setText(lista.get(position).getIdOrcamento());
        txtIdServ.setText(lista.get(position).getIdServico());
        txtNameServices.setText(lista.get(position).getNomeServico());
        txtDescServices.setText(lista.get(position).getDescServico());
        txtModelCar.setText(lista.get(position).getModeloAutomovel());
        txtAddress.setText(lista.get(position).getEndereço() + ", " + lista.get(position).getNum());
        txtNeigh.setText(lista.get(position).getBairro());
        txtCep.setText(lista.get(position).getCep());

        String idOrc = lista.get(position).getIdOrcamento();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //montaObj(idOrc);
                //Toast.makeText(context, "Entra", Toast.LENGTH_SHORT).show();

                cliente = new AsyncHttpClient();
                String HOST = "http://192.168.15.113/Vulcar--Syncmysql/Employee/";
                String url = HOST+"update_status_orc.php";

                params.put("id", idOrc);
                params.put("idFunc", idFunc);
                cliente.post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if(statusCode == 200){
                            try {
                                JSONObject jsonarray = new JSONObject(new String(responseBody));
                                if(jsonarray.getString("UPDATE").equals("true")){
                                    Intent intent = new Intent(context, HomeEmployee.class);
                                    intent.putExtra("id", idFunc);
                                    intent.putExtra("idBus", idBus);
                                    Toast.makeText(context, "Foi", Toast.LENGTH_SHORT).show();
                                    context.startActivity(intent);
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
        });

        return itemView;
    }



    private void montaObj(String idOrc) {
        cliente = new AsyncHttpClient();
        String HOST = "http://192.168.15.113/Vulcar--Syncmysql/Employee/Select/";
        String url = HOST+"update_status_orc.php";

        params.put("id", idOrc);
        params.put("idFunc", idFunc);
        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    Intent intent = new Intent(context, HomeEmployee.class);
                    intent.putExtra("id", idFunc);
                    intent.putExtra("idBus", idBus);
                    Toast.makeText(context, "Foi", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
