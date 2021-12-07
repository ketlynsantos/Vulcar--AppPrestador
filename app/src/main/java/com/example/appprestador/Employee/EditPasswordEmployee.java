package com.example.appprestador.Employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appprestador.Business.EditPasswordBusiness;
import com.example.appprestador.Business.MyDataBusiness;
import com.example.appprestador.Model.Business;
import com.example.appprestador.Model.Employee;
import com.example.appprestador.R;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class EditPasswordEmployee extends AppCompatActivity {

    public ImageView imgBack;
    public TextInputEditText edtPassword;
    public TextInputEditText edtNewPassword;
    public AppCompatButton btnAlter;

    public String id, idBuss;

    //Connection MySQL
    //String HOST = "http://172.20.10.5/vulcar_database/";
    //String HOST = "http://192.168.0.106/vulcar_database/";
    //String HOST = "http://192.168.15.129/vulcar_database/Business/";
    String HOST = "http://192.168.15.113/Vulcar--Syncmysql/Employee/";

    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;

    public Employee employee = new Employee();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password_employee);

        getSupportActionBar().hide();
        getIds();

        cliente = new AsyncHttpClient();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(EditPasswordEmployee.this, MyDataEmployee.class);
                itI.putExtra("id", id);
                itI.putExtra("idBuss", idBuss);
                startActivity(itI);
                finish();
            }
        });

        btnAlter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaObj();
            }
        });

    }

    private void montaObj() {
        String newPass, oldPass;
        newPass = edtNewPassword.getText().toString();
        oldPass = edtPassword.getText().toString();

        employee.setId(id);
        employee.setPassword(oldPass);
        updatePassword(employee, newPass);
    }

    private void updatePassword(Employee employee, String newPass) {
        String url = HOST + "update_pass.php";

        params.put("id", employee.getId());
        params.put("old_pass", employee.getPassword());
        params.put("new_pass", newPass);

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject result = new JSONObject(new String(responseBody));
                        if(result.getString("UPDATE").equals("true")){
                            Toast.makeText(EditPasswordEmployee.this, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditPasswordEmployee.this, MyDataEmployee.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        } else {
                            Toast.makeText(EditPasswordEmployee.this, "Senha atual incorreta", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(EditPasswordEmployee.this, "Erro ao trocar senha!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditPasswordEmployee.this, "Erro ao trocar senha!", Toast.LENGTH_SHORT).show();
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
        edtPassword = findViewById(R.id.edt_password_emp);
        edtNewPassword = findViewById(R.id.edt_new_password_emp);
        btnAlter = findViewById(R.id.btn_alter_password);
    }
}