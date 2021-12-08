package com.example.appprestador.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

import cz.msebera.android.httpclient.Header;

public class EditAddress extends AppCompatActivity {

    ImageView imgBack;
    TextInputEditText edtNewAddress;
    TextInputEditText edtNewNeighborhood;
    TextInputEditText edtNewComplement;
    TextInputEditText edtNewNumber;
    TextInputEditText edtNewCity;
    TextInputEditText edtNewUF;
    TextInputEditText edtNewCep;
    AppCompatButton btnEditar;
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
        setContentView(R.layout.activity_edit_address);

        cliente = new AsyncHttpClient();

        getSupportActionBar().hide();
        getIds();
        montaObj();
        maskFormat();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAddress.this, Profile.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = edtNewAddress.getText().toString();
                String number = edtNewNumber.getText().toString();
                String neighborhood = edtNewNeighborhood.getText().toString();
                String complement = edtNewComplement.getText().toString();
                String city = edtNewCity.getText().toString();
                String uf = edtNewUF.getText().toString();
                String cep = edtNewCep.getText().toString();

                boolean checkValidations = validationEdit(address, number, neighborhood, city, uf, cep);

                if(checkValidations == true){
                    business.setId(id);
                    business.setAddress(address);
                    business.setNumber(number);
                    business.setNeighborhood(neighborhood);
                    business.setComplement(complement);
                    business.setCity(city);
                    business.setUf(uf);
                    business.setCep(cep);
                    updateAddress(business);
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
                        String addres = jsonarray.getString("LOJA_ENDERECO");
                        edtNewAddress.setText(addres);
                        String number = jsonarray.getString("LOJA_NUM");
                        edtNewNumber.setText(number);
                        String neighborhood = jsonarray.getString("LOJA_BAIRRO");
                        edtNewNeighborhood.setText(neighborhood);
                        String complement = jsonarray.getString("LOJA_COMP");
                        edtNewComplement.setText(complement);
                        String city = jsonarray.getString("LOJA_CIDADE");
                        edtNewCity.setText(city);
                        String uf = jsonarray.getString("LOJA_UF");
                        edtNewUF.setText(uf);
                        String cep = jsonarray.getString("LOJA_CEP");
                        edtNewCep.setText(cep);

                        if (jsonarray.getString("STATUS_ID").equals("5")) {
                            Intent intent = new Intent(EditAddress.this, Login.class);
                            Toast.makeText(EditAddress.this, "Estabelecimento banido!", Toast.LENGTH_SHORT).show();
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

    private void updateAddress(Business business) {
        String url = HOST+"update_address.php";

        params.put("id", business.getId());
        params.put("address", business.getAddress());
        params.put("number", business.getNumber());
        params.put("neighborhood", business.getNeighborhood());
        params.put("complement", business.getComplement());
        params.put("city", business.getCity());
        params.put("uf", business.getUf());
        params.put("cep", business.getCep());

        cliente.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200) {
                    Toast.makeText(EditAddress.this, "Endereço atualizado!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditAddress.this, Profile.class);
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

    private void getIds(){
        id = getIntent().getStringExtra("id");
        imgBack = findViewById(R.id.img_back);
        edtNewAddress = findViewById(R.id.edt_new_address);
        edtNewNumber = findViewById(R.id.edt_new_num);
        edtNewNeighborhood = findViewById(R.id.edt_new_neigh);
        edtNewComplement = findViewById(R.id.edt_new_comp);
        edtNewCity = findViewById(R.id.edt_new_city);
        edtNewUF = findViewById(R.id.edt_new_uf);
        edtNewCep = findViewById(R.id.edt_new_cep);
        btnEditar = findViewById(R.id.btn_edit_address);
    }

    public void maskFormat(){
        SimpleMaskFormatter mask_uf = new SimpleMaskFormatter("LL");
        MaskTextWatcher mtw_uf = new MaskTextWatcher(edtNewUF, mask_uf);
        edtNewUF.addTextChangedListener(mtw_uf);

        SimpleMaskFormatter mask_cep = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher mtw_cep = new MaskTextWatcher(edtNewCep, mask_cep);
        edtNewCep.addTextChangedListener(mtw_cep);
    }

    public Boolean validationEdit(String address, String number, String neighborhood,
                                  String city, String uf, String cep){
        if(address.length() == 0){
            edtNewAddress.requestFocus();
            edtNewAddress.setError("Campo vazio.");
            return false;
        } else if (number.length() == 0) {
            edtNewNumber.requestFocus();
            edtNewNumber.setError("Campo vazio.");
            return false;
        } else if (neighborhood.length() == 0){
            edtNewNeighborhood.requestFocus();
            edtNewNeighborhood.setError("Campo vazio.");
            return false;
        } else if (city.length() == 0){
            edtNewCity.requestFocus();
            edtNewCity.setError("Campo vazio");
            return false;
        } else if (uf.length() == 0){
            edtNewUF.requestFocus();
            edtNewUF.setError("Campo vazio.");
            return false;
        } else if (uf.length() != 2){
            edtNewUF.requestFocus();
            edtNewUF.setError("UF inválido!");
            return false;
        } else if (cep.length() == 0){
            edtNewCep.requestFocus();
            edtNewCep.setError("Campo vazio.");
            return false;
        } else if (cep.length() != 9){
            edtNewCep.requestFocus();
            edtNewCep.setError("CEP inválido!");
            return false;
        } else {
            return true;
        }
    }
}