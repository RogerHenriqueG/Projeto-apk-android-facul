package com.example.projetolocadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText et_email, et_password;
    Button bt_login;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DBHelper(this);

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        bt_login = (Button) findViewById(R.id.bt_login);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String senha = et_password.getText().toString();

                if (email.equals("")){
                    Toast.makeText(LoginActivity.this,"Email não inserido, tente novamente", Toast.LENGTH_SHORT).show();
                }else if (senha.equals("")){
                    Toast.makeText(LoginActivity.this,"Senha não inserida, tente novamente", Toast.LENGTH_SHORT).show();
                }else {
                    String res = db.ValidarLogin(email, senha);

                    if (res.equals("OK")){
                        Toast.makeText(LoginActivity.this,"Login ok", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, MenuActivity.class );
                        startActivity(i);
                    }else {
                        Toast.makeText(LoginActivity.this,"Erro no login, tente novamente", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
