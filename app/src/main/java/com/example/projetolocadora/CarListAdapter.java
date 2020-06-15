package com.example.projetolocadora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CarListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Car> carsList;

    public CarListAdapter(Context context, int layout, ArrayList<Car> carsList) {
        this.context = context;
        this.layout = layout;
        this.carsList = carsList;
    }

    @Override
    public int getCount() {
        return carsList.size();
    }

    @Override
    public Object getItem(int position) {
        return carsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtModelo, txtMarca, txtAno, txtValor;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtModelo = row.findViewById(R.id.modelocar);
            holder.txtMarca = row.findViewById(R.id.marcacar);
            holder.txtAno = row.findViewById(R.id.anocar);
            holder.txtValor = row.findViewById(R.id.valorcar);
            holder.imageView = row.findViewById(R.id.imagecar);
            row.setTag(holder);
        }else {
            holder = (ViewHolder) row.getTag();
        }

        Car car = carsList.get(position);

        holder.txtModelo.setText(car.getModelo());
        holder.txtMarca.setText(car.getMarca());
        holder.txtAno.setText(car.getAno());
        holder.txtValor.setText(car.getValor());

        byte[] carImage = car.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(carImage, 0, carImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
