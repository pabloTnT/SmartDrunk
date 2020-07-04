package com.inacap.smartdrunkapp.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inacap.smartdrunkapp.R;
import com.inacap.smartdrunkapp.dto.ClienteDto;
import com.inacap.smartdrunkapp.dto.DetalleCuentaDto;
import com.inacap.smartdrunkapp.dto.MesaDto;
import com.inacap.smartdrunkapp.dto.ProductoDto;

import java.util.HashMap;
import java.util.Map;

public class DetalleProducto extends AppCompatActivity {

    private ProductoDto producto;
    TextView tvProducto, tvCantProducto,tvPrecioVal, tvPrecioTotalVal;
    ImageView ivProd;
    ImageButton btnAumentar, btnDisminuir, btnVuelveListado;
    Button btnAgregarDet;
    int cantidadAct, precioAgregar;
    String mesa;
    ClienteDto cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        ivProd = findViewById(R.id.ivProd);
        tvProducto = findViewById(R.id.tvProducto);
        producto = (ProductoDto) getIntent().getSerializableExtra("producto");
        asignaImagen(producto.getCodProducto());
        tvProducto.setText(producto.getNombre());
        tvPrecioTotalVal = findViewById(R.id.tvPrecioTotalVal);
        tvPrecioVal = findViewById(R.id.tvPrecioVal);
        tvPrecioVal.setText("$ " + String.valueOf(producto.getPrecio()));
        btnAumentar = findViewById(R.id.btnAumentar);
        btnDisminuir = findViewById(R.id.btnDisminuir);
        btnAgregarDet = findViewById(R.id.btnAgregarDet);
        btnVuelveListado = findViewById(R.id.btnVuelveListado);
        tvCantProducto = findViewById(R.id.tvCantProducto);
        mesa = getIntent().getSerializableExtra("codMesa").toString();
        cliente = (ClienteDto)getIntent().getSerializableExtra("clienteDto");
        cantidadAct = Integer.valueOf(tvCantProducto.getText().toString());
        precioAgregar = producto.getPrecio();
        tvPrecioTotalVal.setText(String.valueOf(precioAgregar));
        btnVuelveListado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAumentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cantidadAct < 99){
                    cantidadAct++;
                    precioAgregar+=producto.getPrecio();
                    tvCantProducto.setText(String.valueOf(cantidadAct));
                    tvPrecioTotalVal.setText(String.valueOf(precioAgregar));
                }
            }
        });
        btnDisminuir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cantidadAct > 1){
                    cantidadAct--;
                    precioAgregar -= producto.getPrecio();
                    tvCantProducto.setText(String.valueOf(cantidadAct));
                    tvPrecioTotalVal.setText(String.valueOf(precioAgregar));
                }
            }
        });
        btnAgregarDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetalleCuentaDto detalleAgregar = new DetalleCuentaDto();
                detalleAgregar.setProducto(producto.getCodProducto());
                detalleAgregar.setMesa(Integer.valueOf(mesa));
                detalleAgregar.setCantidadProd(cantidadAct);
                ingresarDetalle("http://smartdrunk.freetzi.com/SmartDrunk/insertDetalleCuenta.php", detalleAgregar);
                finish();
            }
        });
    }

    private void ingresarDetalle (String URL, DetalleCuentaDto dto){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {

                } else {
                    Toast.makeText(DetalleProducto.this, "Ups.. Hubo un problema al regristrar el pedido", Toast.LENGTH_SHORT).show();
                    Toast toast = Toast.makeText(getApplicationContext(),"Ups.. Hubo un problema al regristrar el pedido", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,0);
                    toast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("codMesa", String.valueOf(dto.getMesa()));
                parametros.put("codProducto", String.valueOf(dto.getProducto()));
                parametros.put("cantidad", String.valueOf(dto.getCantidadProd()));
                Log.d("DTO-",String.valueOf(dto.getMesa()));
                Log.d("DTO-",String.valueOf(dto.getProducto()));
                Log.d("DTO-",String.valueOf(dto.getCantidadProd()));
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void asignaImagen(int codProducto){
        switch (codProducto){
            case 1: ivProd.setImageResource(R.drawable.producto1);break;
            case 2: ivProd.setImageResource(R.drawable.producto2);break;
            case 3: ivProd.setImageResource(R.drawable.producto3);break;
            case 4: ivProd.setImageResource(R.drawable.producto4);break;
            case 5: ivProd.setImageResource(R.drawable.producto5);break;
            case 6: ivProd.setImageResource(R.drawable.producto6);break;
        }

    }
}
