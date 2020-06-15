package com.example.projetolocadora;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String modelo, String marca, String ano, String valor, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO carros VALUES ( NULL, ?, ?, ?, ?, ?)";

        SQLiteStatement statement =  db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, modelo);
        statement.bindString(2, marca);
        statement.bindString(3, ano);
        statement.bindString(4, valor);
        statement.bindBlob(5, image);

        statement.executeInsert();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public void updateData(String modelo, String marca, String ano, String valor, byte[] image, int id){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE carros SET modelo = ?, marca = ?, ano = ?, valor = ?, image = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, modelo);
        statement.bindString(2, marca);
        statement.bindString(3, ano);
        statement.bindString(4, valor);
        statement.bindBlob(5, image);
        statement.bindDouble(6, (double)id);

        statement.execute();
        database.close();
    }

    public void deleteData(int id){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM carros WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);

        statement.execute();
        database.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
