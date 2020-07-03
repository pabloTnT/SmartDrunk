package com.inacap.smartdrunkapp.dto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.inacap.smartdrunkapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class AdaptadorDetalleCuenta extends ArrayAdapter<DetalleCuentaNormalizado> {

    private Context mContext;
    int mResource;
    TextView tvTotal;

    public AdaptadorDetalleCuenta(Context context, int resource, ArrayList<DetalleCuentaNormalizado> obj) {
        super(context, resource, obj);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int codProducto = getItem(position).getCodProducto();
        String nombreProducto = getItem(position).getNombreProducto();
        int codMesa = getItem(position).getCodMesa();
        int cantProd = getItem(position).getCantProd();
        int precioProd = getItem(position).getPrecioProd();

        DetalleCuentaNormalizado dto = new DetalleCuentaNormalizado();
        dto.setCodProducto(codProducto);
        dto.setNombreProducto(nombreProducto);
        dto.setCodMesa(codMesa);
        dto.setCantProd(cantProd);
        dto.setPrecioProd(precioProd);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView tvNombreDet = (TextView) convertView.findViewById(R.id.tvNombreDet);
        TextView tvCantiadDet = (TextView) convertView.findViewById(R.id.tvCantiadDet);
        TextView tvPrecioDet = (TextView) convertView.findViewById(R.id.tvPrecioDet);
        TextView tvSubTotalDet = (TextView) convertView.findViewById(R.id.tvSubTotalDet);

        tvNombreDet.setText(dto.getNombreProducto());
        tvCantiadDet.setText(String.valueOf(dto.getCantProd()));
        tvPrecioDet.setText("$ " +String.valueOf(dto.getPrecioProd()));
        tvSubTotalDet.setText("$ " +String.valueOf(cantProd * precioProd));
        return convertView;
    }

}
