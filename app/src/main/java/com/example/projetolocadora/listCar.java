package com.example.projetolocadora;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class listCar extends AppCompatActivity {

    GridView gridView;
    ArrayList<Car> list;
    CarListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_car);

        gridView = findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new CarListAdapter(this, R.layout.car_items, list);
        gridView.setAdapter(adapter);

        Cursor cursor = AddNewCarActivity.sqLiteHelper.getData("SELECT * FROM carros");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String modelo = cursor.getString(1);
            String marca = cursor.getString(2);
            String ano = cursor.getString(3);
            String valor = cursor.getString(4);
            byte[] image = cursor.getBlob(5);

            list.add(new Car(id, modelo, marca, ano, valor, image));
        }
        adapter.notifyDataSetChanged();

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items  = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(listCar.this);

                dialog.setTitle("Escolha uma Ação");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0){
                            Cursor c = AddNewCarActivity.sqLiteHelper.getData("SELECT * FROM carros");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }

                            showDialogUpdate(listCar.this, arrID.get(position));
                        }else{
                            Cursor c = AddNewCarActivity.sqLiteHelper.getData("SELECT * FROM carros");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }

                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    ImageView imageviewcar;

    private void showDialogUpdate(Activity activity, final int position){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_car_activity);
        dialog.setTitle("Update");

        imageviewcar = dialog.findViewById(R.id.imageviewcar);
        final EditText et_upmodelo = dialog.findViewById(R.id.et_upmodelo);
        final EditText et_upmarca = dialog.findViewById(R.id.et_upmarca);
        final EditText et_upano = dialog.findViewById(R.id.et_upano);
        final EditText et_upvalor = dialog.findViewById(R.id.et_upvalor);
        Button bt_update = dialog.findViewById(R.id.bt_update);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);

        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.9);

        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageviewcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    String[] permissao = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissao, 1001);
                }else {
                    escolherimagem();
                }
            }
        });

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AddNewCarActivity.sqLiteHelper.updateData(
                            et_upmodelo.getText().toString().trim(),
                            et_upmarca.getText().toString().trim(),
                            et_upano.getText().toString().trim(),
                            et_upvalor.getText().toString().trim(),
                            AddNewCarActivity.imageViewToByte(imageviewcar),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                }catch (Exception error){
                    Log.d("Erro ao atualizar", error.getMessage());
                }
                updatelistcar();
            }
        });
    }

    private void showDialogDelete(final int idCar){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(listCar.this);
        dialogDelete.setTitle("Atenção");
        dialogDelete.setMessage("Você tem certeza de que deseja excluir");

        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    AddNewCarActivity.sqLiteHelper.deleteData(idCar);
                    Toast.makeText(getApplicationContext(), "Deletado com sucesso!", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updatelistcar();
            }
        });

        dialogDelete.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updatelistcar(){
        Cursor cursor = AddNewCarActivity.sqLiteHelper.getData("SELECT * FROM carros");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String modelo = cursor.getString(1);
            String marca = cursor.getString(2);
            String ano = cursor.getString(3);
            String valor = cursor.getString(4);
            byte[] image = cursor.getBlob(5);

            list.add(new Car(id, modelo, marca, ano, valor, image));
        }
        adapter.notifyDataSetChanged();
    }

    private void escolherimagem(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1001:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    escolherimagem();
                }else {
                    Toast.makeText(this, "permissão negada!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1000) {
            Uri uri = data.getData();
//            imageView.setImageURI(data.getData());

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageviewcar.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
