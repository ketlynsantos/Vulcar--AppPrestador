package com.example.appprestador.Employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appprestador.Business.EditDataContact;
import com.example.appprestador.Business.MyDataBusiness;
import com.example.appprestador.Model.Employee;
import com.example.appprestador.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

public class EditDataContactEmployee extends AppCompatActivity {

    public ImageView imgBack;
    public TextInputEditText edtEmail;
    public TextInputEditText edtPhone;
    public AppCompatButton btnEdit;

    public String id, idBuss;
    public Employee employee = new Employee();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_contact_employee);

        getSupportActionBar().hide();
        getIds();
        //montaObj();
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
                    //updateContact(employee);
                }
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