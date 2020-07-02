package com.inacap.smartdrunkapp.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.inacap.smartdrunkapp.R;
import com.inacap.smartdrunkapp.dto.ProductoDto;

public class DetalleProducto extends AppCompatActivity {

    private ProductoDto producto;
    TextView tvProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        tvProducto = findViewById(R.id.tvProducto);
        producto = (ProductoDto) getIntent().getSerializableExtra("producto");
        tvProducto.setText(producto.getNombre());
    }
}
