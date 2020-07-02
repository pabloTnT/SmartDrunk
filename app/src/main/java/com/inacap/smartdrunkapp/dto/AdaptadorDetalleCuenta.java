package com.inacap.smartdrunkapp.dto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.inacap.smartdrunkapp.R;

import java.util.ArrayList;


public class AdaptadorProducto extends ArrayAdapter<ProductoDto> {

    private Context mContext;
    int mResource;

    public AdaptadorProducto(Context context, int resource, ArrayList<ProductoDto> obj) {
        super(context, resource, obj);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String nombreProducto = getItem(position).getNombre();
        int precioProducto = getItem(position).getPrecio();
        int tipoProd = getItem(position).getTipoProd();
        int codProducto = getItem(position).getCodProducto();
        int st = getItem(position).getSt();

        ProductoDto dto = new ProductoDto();
        dto.setTipoProd(tipoProd);
        dto.setCodProducto(codProducto);
        dto.setNombre(nombreProducto);
        dto.setPrecio(precioProducto);
        dto.setSt(st);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView tvNombre = (TextView) convertView.findViewById(R.id.tvNombreCom);
        TextView tvPrecio = (TextView) convertView.findViewById(R.id.tvPrecioCom);

        tvNombre.setText(dto.getNombre());
        tvPrecio.setText(String.valueOf("$ " + dto.getPrecio()));

        return convertView;
    }
}
