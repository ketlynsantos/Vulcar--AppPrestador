package com.example.appprestador.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appprestador.Login;
import com.example.appprestador.Model.Business;
import com.example.appprestador.R;
import com.example.appprestador.Register2;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class RegisterEmployee extends AppCompatActivity {

    public ImageView imgBack;
    public TextInputEditText edtName;
    public TextInputEditText edtEmail;
    public TextInputEditText edtPhone;
    public TextInputEditText edtPassword;
    public TextInputEditText edtCpf;
    public AppCompatButton btnRegister;

    public String id;

    //Connection MySQL
    //String HOST = "http://172.20.10.5/vulcar_database/Business/";
    //String HOST = "http://192.168.15.129/vulcar_database/Business/";
    String HOST = "http://172.20.10.6/Vulcar--Syncmysql/Employee/";

    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;
    com.example.appprestador.Model.Employee employee = new com.example.appprestador.Model.Employee();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);

        getSupportActionBar().hide();
        getIds();
        maskFormat();
        cliente = new AsyncHttpClient();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(RegisterEmployee.this, Employee.class);
                it.putExtra("id", id);
                startActivity(it);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaObj();
            }
        });
    }

    private void montaObj() {
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String phone = edtPhone.getText().toString();
        String password = edtPassword.getText().toString();
        String cpf = edtCpf.getText().toString();
        String sts = "10";
        boolean checkValidations = validationRegister(name, email, phone, password, cpf);

        //Testando validação
        if (checkValidations == true) {
            employee.setNome(name);
            employee.setCpf(cpf);
            employee.setEmail(email);
            employee.setPhone(phone);
            employee.setPassword(password);
            employee.setId_status(sts);
            employee.setId_loja(id);
            cadastrarEmployee(employee);
        } else {
            //Toast.makeText(getApplicationContext(), phone.length(), Toast.LENGTH_SHORT).show();
        }
    }

    private void cadastrarEmployee(com.example.appprestador.Model.Employee employee) {
        String url = HOST + "create.php";

        params.put("name", employee.getNome());
        params.put("cpf", employee.getCpf());
        params.put("email", employee.getEmail());
        params.put("phone", employee.getPhone());
        params.put("pass", employee.getPassword());
        params.put("loja", employee.getId_loja());
        params.put("sts", employee.getId_status());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    Toast.makeText(RegisterEmployee.this, "Funcionário adicionado!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterEmployee.this, Employee.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterEmployee.this, "Falha ao adicionar novo funcionário!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void getIds() {
        id = getIntent().getStringExtra("id");

        edtName = this.findViewById(R.id.edt_name);
        edtEmail = this.findViewById(R.id.edt_email);
        edtPhone = this.findViewById(R.id.edt_phone);
        edtPassword = this.findViewById(R.id.edt_password);
        btnRegister = this.findViewById(R.id.btn_add_employee);
        imgBack = this.findViewById(R.id.img_back);
        edtCpf = this.findViewById(R.id.edt_cpf);
    }

    private void maskFormat() {
        SimpleMaskFormatter mask_tel = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtw_tel = new MaskTextWatcher(edtPhone, mask_tel);
        edtPhone.addTextChangedListener(mtw_tel);

        SimpleMaskFormatter mask_cpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw_cpf = new MaskTextWatcher(edtCpf, mask_cpf);
        edtCpf.addTextChangedListener(mtw_cpf);
    }

    private Boolean validationRegister(String name, String email, String phone, String password, String cpf) {
        if (name.length() == 0) {
            edtName.requestFocus();
            edtName.setError("Campo vazio.");
            return false;
        } else if(!name.matches("^[a-zA-Z]+[\\-'\\s]?[a-zA-Z ]+$")) {
            edtName.requestFocus();
            edtName.setError("Apenas letras, por favor!");
            return false;
        }   else if (cpf.length() == 0) {
            edtCpf.requestFocus();
            edtCpf.setError("Campo vazio");
            return false;
        } else if (cpf.length() != 14) {
            edtCpf.requestFocus();
            edtCpf.setError("CPF inválido!");
            return false;
        } else if(email.length() == 0) {
            edtEmail.requestFocus();
            edtEmail.setError("Campo vazio.");
            return false;
        } else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            edtEmail.requestFocus();
            edtEmail.setError("Email inválido!");
            return false;
        } else if(phone.length() == 0) {
            edtPhone.requestFocus();
            edtPhone.setError("Campo vazio.");
            return false;
        } else if(phone.length() != 15) {
            edtPhone.requestFocus();
            edtPhone.setError("Número incorreto.");
            return false;
        } else if (password.length() < 5) {
            edtPassword.requestFocus();
            edtPassword.setError("Mínino 6 caracteres.");
            return false;
        } else {
            return true;
        }
    }


}