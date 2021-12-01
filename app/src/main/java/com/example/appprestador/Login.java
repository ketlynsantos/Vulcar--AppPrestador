package com.example.appprestador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appprestador.Business.Home;
import com.example.appprestador.Employee.HomeEmployee;
import com.example.appprestador.Model.Business;
import com.example.appprestador.Model.Employee;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton rbEmpresa, rbFuncionario;
    RelativeLayout relativeBusiness, relativeEmployee;
    TextInputEditText editCnpj, editPassBuss, editCpf, editPassEmployee;
    TextView txtRegister;
    AppCompatButton btnLoginBusiness, btnLoginEmployee;
    LinearLayout linearLayout1;

    //Connection MySQL
    //String HOST = "http://172.20.10.5/vulcar_database/";
    String HOST = "http://192.168.15.127/vulcar_database/";

    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;
    Business business = new Business();
    Employee employee = new Employee();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        getIds();
        maskFormat();
        cliente = new AsyncHttpClient();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Verificar qual Radio button está selecionado
                switch (checkedId){
                    case R.id.rb_empresa:
                        relativeBusiness.setVisibility(View.VISIBLE);
                        relativeEmployee.setVisibility(View.GONE);
                        linearLayout1.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_funcionario:
                        relativeBusiness.setVisibility(View.GONE);
                        relativeEmployee.setVisibility(View.VISIBLE);
                        linearLayout1.setVisibility(View.GONE);
                        break;
                }
            }
        });

        btnLoginBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaObjBusiness();
            }
        });

        btnLoginEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaObjEmployee();
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    private void montaObjBusiness() {
        String cnpj, pass;
        cnpj = editCnpj.getText().toString();
        pass = editPassBuss.getText().toString();

        boolean checkValidations = validationRegisterBus(cnpj, pass);

        if(checkValidations == true) {
            business.setCnpj(cnpj);
            business.setPassword(pass);
            funcLoginBus(business);
        }
    }

    private void montaObjEmployee() {
        String cpf, pass;
        cpf = editCpf.getText().toString();
        pass = editPassEmployee.getText().toString();

        boolean checkValidations = validationRegisterEmp(cpf, pass);

        if(checkValidations == true) {
            employee.setCpf(cpf);
            employee.setPassword(pass);
            funcLoginEmp(employee);
        }
    }

    private void funcLoginBus(Business business) {
        String url = HOST + "Business/login.php";

        params.put("cnpj", business.getCnpj());
        params.put("pass", business.getPassword());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject result = new JSONObject(new String(responseBody));
                        if (!result.getString("LOGIN").equals(null)) {
                            String id = result.getString("LOGIN");
                            if(result.getString("STATUS").equals("2")){
                                Toast.makeText(Login.this, "Conta recusada!", Toast.LENGTH_SHORT).show();
                            } else if(result.getString("STATUS").equals("3")){
                                Toast.makeText(Login.this, "Conta em análise!", Toast.LENGTH_SHORT).show();
                            } else if(result.getString("STATUS").equals("5")){
                                Toast.makeText(Login.this, "Conta banida!", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(Login.this, Home.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(Login.this, "Erro ao Logar!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(Login.this, "Usuário ou senha incorretos!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void funcLoginEmp(Employee employee) {
        String url = HOST + "Employee/login.php";

        params.put("cpf", employee.getCpf());
        params.put("pass", employee.getPassword());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject result = new JSONObject(new String(responseBody));
                        if (!result.getString("LOGIN").equals(null)) {
                            String id = result.getString("LOGIN");

                            if((!result.getString("STATUS").equals("5"))) {
                                Intent intent = new Intent(Login. this, HomeEmployee.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Conta banida!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(Login.this, "Erro ao Logar!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(Login.this, "Erro ao Logar!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    public void getIds() {
        radioGroup = findViewById(R.id.radio_group);
        rbEmpresa = findViewById(R.id.rb_empresa);
        rbFuncionario = findViewById(R.id.rb_funcionario);
        relativeBusiness = findViewById(R.id.relative_business);
        relativeEmployee = findViewById(R.id.relative_employee);

        btnLoginBusiness = findViewById(R.id.btn_login_business);
        btnLoginEmployee = findViewById(R.id.btn_login_employee);

        editCnpj = findViewById(R.id.edit_cnpj);
        editPassBuss = findViewById(R.id.edit_password_buss);
        editCpf = findViewById(R.id.edit_cpf);
        editPassEmployee = findViewById(R.id.edit_password_employee);

        txtRegister = findViewById(R.id.txt_register);

        linearLayout1 = findViewById(R.id.linear_layout_1);
    }


    public void maskFormat() {
        SimpleMaskFormatter mask_cnpj = new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN");
        MaskTextWatcher mtw_cnpj = new MaskTextWatcher(editCnpj, mask_cnpj);
        editCnpj.addTextChangedListener(mtw_cnpj);

        SimpleMaskFormatter mask_cpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw_cpf = new MaskTextWatcher(editCpf, mask_cpf);
        editCpf.addTextChangedListener(mtw_cpf);
    }

    public Boolean validationRegisterBus(String cnpj, String pass) {
        if (cnpj.length() == 0) {
            editCnpj.requestFocus();
            editCnpj.setError("Campo vazio.");
            return false;
        } else if (cnpj.length() != 18) {
            editCnpj.requestFocus();
            editCnpj.setError("Campo vazio.");
            return false;
        } else if (pass.length() == 0) {
            editPassBuss.requestFocus();
            editPassBuss.setError("Campo vazio.");
            return false;
        } else {
            return true;
        }
    }

    public Boolean validationRegisterEmp(String cpf, String pass) {
        if (cpf.length() == 0) {
            editCpf.requestFocus();
            editCpf.setError("Campo vazio.");
            return false;
        } else if (cpf.length() != 14) {
            editCpf.requestFocus();
            editCpf.setError("Campo vazio.");
            return false;
        } else if (pass.length() == 0) {
            editPassEmployee.requestFocus();
            editPassEmployee.setError("Campo vazio.");
            return false;
        } else {
            return true;
        }
    }
}