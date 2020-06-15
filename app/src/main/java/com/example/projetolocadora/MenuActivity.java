package com.example.projetolocadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button bt_sair, bt_newcar, bt_seecar, bt_editcar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bt_newcar = (Button) findViewById(R.id.bt_newcar);
        bt_seecar = (Button) findViewById(R.id.bt_seecar);
        bt_sair = (Button) findViewById(R.id.bt_sair);

        bt_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_newcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, AddNewCarActivity.class);
                startActivity(i);
            }
        });

        bt_seecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, listCar.class);
                startActivity(intent);
            }
        });
    }
}
