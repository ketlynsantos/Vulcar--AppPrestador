package com.example.appprestador.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appprestador.Login;
import com.example.appprestador.Model.Business;
import com.example.appprestador.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class EditDataContact extends AppCompatActivity {

    public ImageView imgBack;
    public TextInputEditText edtEmail;
    public TextInputEditText edtPhone;
    public AppCompatButton btnEdit;

    public String id;

    Business business = new Business();
    //Connection MySQL
    //String HOST = "http://172.20.10.5/vulcar_database/";
    String HOST = "http://192.168.15.112/vulcar_database/Business/";
    //String HOST = "http://172.20.10.6/Vulcar--Syncmysql/Business/";

    RequestParams params = new RequestParams();
    AsyncHttpClient cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_contact);

        cliente = new AsyncHttpClient();

        getSupportActionBar().hide();
        getIds();
        montaObj();
        maskFormat();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itI = new Intent(EditDataContact.this, MyDataBusiness.class);
                itI.putExtra("id", id);
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
                    business.setId(id);
                    business.setEmail(email);
                    business.setPhone(phone);
                    updateContact(business);
                }
            }
        });
    }

    private void montaObj() {
        String url = HOST+"Select/select_business.php";
        business.setId(id);
        params.put("id", business.getId());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    try {
                        JSONObject jsonarray = new JSONObject(new String(responseBody));
                        String email = jsonarray.getString("LOJA_EMAIL");
                        edtEmail.setText(email);
                        String phone = jsonarray.getString("LOJA_TEL");
                        edtPhone.setText(phone);
                        if (jsonarray.getString("STATUS_ID").equals("5")) {
                            Intent intent = new Intent(EditDataContact.this, Login.class);
                            Toast.makeText(EditDataContact.this, "Estabelecimento banido!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
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

    private void updateContact(Business business) {
        String url = HOST+"update_contact.php";

        params.put("id", business.getId());
        params.put("email", business.getEmail());
        params.put("phone", business.getPhone());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    Toast.makeText(EditDataContact.this, "Dados atualizados!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditDataContact.this, MyDataBusiness.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void getIds() {
        id = getIntent().getStringExtra("id");
        imgBack = findViewById(R.id.img_back);
        edtEmail = findViewById(R.id.edt_email_business);
        edtPhone = findViewById(R.id.edt_phone_businesss);
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