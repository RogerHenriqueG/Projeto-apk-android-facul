package com.example.projetolocadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText et_newemail, et_newpassword, et_confirmpassword;
    RadioButton rb_adm, rb_cliente;
    Button bt_newregister;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DBHelper(this);

        et_newemail = (EditText) findViewById(R.id.et_newemail);
        et_newpassword = (EditText) findViewById(R.id.et_newpassword);
        et_confirmpassword = (EditText) findViewById(R.id.et_confirmpassword);

        bt_newregister = (Button) findViewById(R.id.bt_newregister);

        bt_newregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_newemail.getText().toString();
                String p1 = et_newpassword.getText().toString();
                String p2 = et_confirmpassword.getText().toString();

                if (email.equals("")){
                    Toast.makeText(RegisterActivity.this,"Email não inserido, tente novamente", Toast.LENGTH_SHORT).show();
                } else if (p1.equals("") || p2.equals("")){
                    Toast.makeText(RegisterActivity.this,"Deve preencher a senha, tente novamente", Toast.LENGTH_SHORT).show();
                } else if (!p1.equals(p2)) {
                    Toast.makeText(RegisterActivity.this, "As senhas não correspondem, tente novamente", Toast.LENGTH_SHORT).show();
                } else {
                    long res = db.NovoCliente(email,p1);
                    if (res>0){
                        Toast.makeText(RegisterActivity.this,"Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this,"Erro no cadastro, tente novamente", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}
