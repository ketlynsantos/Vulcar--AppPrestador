package com.example.appprestador.AdapterBusiness;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appprestador.Model.Employee;
import com.example.appprestador.R;

import java.util.ArrayList;

public class AdapterEmployees extends ArrayAdapter<Employee> {
    int groupid;
    ArrayList<Employee> lista;
    Context context;

    public AdapterEmployees(@NonNull Context context, int vg, int id, ArrayList<Employee> lista) {
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
        TextView txtId = itemView.findViewById(R.id.txt_id);
        TextView txtNome = itemView.findViewById(R.id.txt_name);
        TextView txtCpf = itemView.findViewById(R.id.txt_cpf);
        TextView txtEmail = itemView.findViewById(R.id.txt_email);
        TextView txtPhone = itemView.findViewById(R.id.txt_phone);
        TextView txtIdLoja = itemView.findViewById(R.id.txt_id_loja);
        //TextView txtNomeLoja = itemView.findViewById(R.id.txt_nome_loja);
        TextView txtIdStatus = itemView.findViewById(R.id.txt_id_status);
        TextView txtStatus = itemView.findViewById(R.id.txt_status);

        txtId.setText(lista.get(position).getId());
        txtNome.setText(lista.get(position).getNome());
        txtCpf.setText(lista.get(position).getCpf());
        txtEmail.setText(lista.get(position).getEmail());
        txtPhone.setText(lista.get(position).getPhone());
        txtIdLoja.setText(lista.get(position).getId_loja());
        //txtNomeLoja.setText(lista.get(position).getLoja_nome());
        txtIdStatus.setText(lista.get(position).getId_status());
        txtStatus.setText(lista.get(position).getStatus_nome());
        return itemView;
    }
}
