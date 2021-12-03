package com.example.appprestador.AdapterBusiness;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appprestador.Model.Services;
import com.example.appprestador.R;

import java.util.ArrayList;

public class AdapterServices extends ArrayAdapter<Services> {
    int groupid;
    ArrayList<Services> lista;
    Context context;

    public AdapterServices(@NonNull Context context, int vg, int id, ArrayList<Services> lista) {
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
        TextView txtNome = itemView.findViewById(R.id.txt_nome);
        TextView txtDesc = itemView.findViewById(R.id.txt_desc);
        TextView txtValor = itemView.findViewById(R.id.txt_valor);
        TextView txtIdCategoria = itemView.findViewById(R.id.txt_id_categoria);
        TextView txtNomeCategoria = itemView.findViewById(R.id.txt_nome_categoria);
        TextView txtIdLoja = itemView.findViewById(R.id.txt_id_loja);
        TextView txtNomeLoja = itemView.findViewById(R.id.txt_nome_loja);
        txtId.setText(lista.get(position).getId());
        txtNome.setText(lista.get(position).getNome());
        txtDesc.setText(lista.get(position).getDesc());
        txtValor.setText(lista.get(position).getValor());
        txtIdCategoria.setText(lista.get(position).getId_categoria());
        txtNomeCategoria.setText(lista.get(position).getNome_categoria());
        txtIdLoja.setText(lista.get(position).getId_loja());
        txtNomeLoja.setText(lista.get(position).getNome_loja());
        return itemView;
    }
}
