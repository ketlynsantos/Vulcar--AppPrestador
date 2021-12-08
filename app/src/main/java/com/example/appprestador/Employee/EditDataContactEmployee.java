package com.example.appprestador.Employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appprestador.Business.EditDataContact;
import com.example.appprestador.Business.MyDataBusiness;
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

public class EditDataContactEmployee extends AppCompatActivity {

    public ImageView imgBack;
    public TextInputEditText edtEmail;
    public TextInputEditText edtPhone;
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
        setContentView(R.layout.activity_edit_data_contact_employee);

        getSupportActionBar().hide();
        getIds();

        cliente = new AsyncHttpClient();
        montaObj();
        maskFormat();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(EditDataContactEmployee.this, MyDataEmployee.class);
                itI.putExtra("id", id);
                itI.putExtra("idBuss", idBuss);
                startActivity(itI);
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String phone = edtPhone.getText().toString();

                boolean checkValidations = validationEdit(email, phone);
                if(checkValidations == true){
                    employee.setId(id);
                    employee.setEmail(email);
                    employee.setPhone(phone);
                    updateContact(employee);
                }
            }
        });
    }

    private void updateContact(Employee employee) {
        String url = HOST + "update_contact.php";

        params.put("id", employee.getId());
        params.put("email", employee.getEmail());
        params.put("phone", employee.getPhone());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    Toast.makeText(EditDataContactEmployee.this, "Dados atualizados!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditDataContactEmployee.this, MyDataEmployee.class);
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
                        String email = jsonarray.getString("FUNCIONARIO_EMAIL");
                        String phone = jsonarray.getString("FUNCIONARIO_TEL");

                        edtEmail.setText(email);
                        edtPhone.setText(phone);

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
        edtEmail = findViewById(R.id.edt_email_emp);
        edtPhone = findViewById(R.id.edt_phone_emp);
        btnEdit = findViewById(R.id.btn_edit_contact);
    }


    private void maskFormat() {
        SimpleMaskFormatter mask_tel = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtw_tel = new MaskTextWatcher(edtPhone, mask_tel);
        edtPhone.addTextChangedListener(mtw_tel);
    }

    private Boolean validationEdit(String email, String phone){
        if(email.length() == 0){
            edtEmail.requestFocus();
            edtEmail.setError("Campo vazio.");
            return false;
        } else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            edtEmail.requestFocus();
            edtEmail.setError("Email inválido!");
            return false;
        } else if (phone.length() == 0) {
            edtPhone.requestFocus();
            edtPhone.setError("Campo vazio.");
            return false;
        } else if (phone.length() != 15) {
            edtPhone.requestFocus();
            edtPhone.setError("Telefone inválido!");
            return false;
        } else {
            return true;
        }
    }
}