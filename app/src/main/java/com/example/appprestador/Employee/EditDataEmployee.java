package com.example.appprestador.Employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appprestador.Business.EditDataBusiness;
import com.example.appprestador.Business.MyDataBusiness;
import com.example.appprestador.Login;
import com.example.appprestador.Model.Employee;
import com.example.appprestador.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class EditDataEmployee extends AppCompatActivity {

    public ImageView imgBack;
    public TextInputEditText edtName;
    public TextInputEditText edtCPF;
    public AppCompatButton btnEdit;

    public String id, idBuss;

    //Connection MySQL
    //String HOST = "http://172.20.10.5/vulcar_database/";
    String HOST = "http://192.168.15.112/vulcar_database/Employee/";
    //String HOST = "http://172.20.10.6/Vulcar--Syncmysql/Employee/";

    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;

    public Employee employee = new Employee();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_employee);

        getSupportActionBar().hide();
        getIds();
        maskFormat();
        cliente = new AsyncHttpClient();
        montaObj();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(EditDataEmployee.this, MyDataEmployee.class);
                itI.putExtra("id", id);
                itI.putExtra("idBuss", idBuss);
                startActivity(itI);
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String cpf = edtCPF.getText().toString();

                boolean checkValidations = validationEdit(name, cpf);
                if(checkValidations == true){
                    employee.setId(id);
                    employee.setNome(name);
                    employee.setCpf(cpf);
                    updateData(employee);
                }
            }
        });
    }

    private void updateData(Employee employee) {
        String url = HOST + "update_data.php";

        params.put("id", employee.getId());
        params.put("name", employee.getNome());
        params.put("cpf", employee.getCpf());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    Toast.makeText(EditDataEmployee.this, "Dados atualizados!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditDataEmployee.this, MyDataEmployee.class);
                    intent.putExtra("id", id);
                    intent.putExtra("idBuss", idBuss);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

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
                        String cpf = jsonarray.getString("FUNCIONARIO_CPF");

                        edtName.setText(nome);
                        edtCPF.setText(cpf);

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

    private void getIds() {
        id = getIntent().getStringExtra("id");
        idBuss = getIntent().getStringExtra("idBuss");

        imgBack = findViewById(R.id.img_back);
        edtName = findViewById(R.id.edt_name_emp);
        edtCPF = findViewById(R.id.edt_cpf_emp);
        btnEdit = findViewById(R.id.btn_edit_data);
    }

    public Boolean validationEdit(String name, String cpf){
        if(name.length() == 0){
            edtName.requestFocus();
            edtName.setError("Campo vazio.");
            return false;
        } else if (cpf.length() == 0) {
            edtCPF.requestFocus();
            edtCPF.setError("Campo vazio.");
            return false;
        } else if (cpf.length() != 14) {
            edtCPF.requestFocus();
            edtCPF.setError("CPF inválido!");
            return false;
        } else {
            return true;
        }
    }

    public void maskFormat() {
        SimpleMaskFormatter mask_cpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw_cpf= new MaskTextWatcher(edtCPF, mask_cpf);
        edtCPF.addTextChangedListener(mtw_cpf);
    }

}