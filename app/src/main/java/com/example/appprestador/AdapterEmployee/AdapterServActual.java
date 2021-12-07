package com.example.appprestador.AdapterEmployee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.appprestador.Model.ItemsBudget;
import com.example.appprestador.R;

import java.util.ArrayList;

public class AdapterServActual extends ArrayAdapter<ItemsBudget> {
    int groupid;
    ArrayList<ItemsBudget> lista;
    Context context;

    public AdapterServActual(@NonNull Context context, int vg, int id, ArrayList<ItemsBudget> lista) {
        super(context, vg, id, lista);
        this.context = context;
        this.groupid = vg;
        this.lista = lista;
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
        AppCompatButton btn = itemView.findViewById(R.id.btn_accept_serv);

        txtId.setText(lista.get(position).getId());
        txtIdServ.setText(lista.get(position).getIdServico());
        txtNameServices.setText(lista.get(position).getNomeServico());
        txtDescServices.setText(lista.get(position).getDescServico());
        txtModelCar.setText(lista.get(position).getModeloAutomovel());
        txtAddress.setText(lista.get(position).getEndereço() + ", " + lista.get(position).getNum());
        txtNeigh.setText(lista.get(position).getBairro());
        txtCep.setText(lista.get(position).getCep());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
            }
        });

        return itemView;
    }
}
