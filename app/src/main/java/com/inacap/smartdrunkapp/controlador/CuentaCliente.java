package com.inacap.smartdrunkapp.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inacap.smartdrunkapp.R;
import com.inacap.smartdrunkapp.dto.AdaptadorDetalleCuenta;
import com.inacap.smartdrunkapp.dto.AdaptadorProducto;
import com.inacap.smartdrunkapp.dto.ClienteDto;
import com.inacap.smartdrunkapp.dto.DetalleCuentaDto;
import com.inacap.smartdrunkapp.dto.DetalleCuentaNormalizado;
import com.inacap.smartdrunkapp.dto.ProductoDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CuentaCliente extends AppCompatActivity {
    private ArrayList<DetalleCuentaNormalizado> detalleNorm;
    ListView mListView;
    RequestQueue requestQueue;
    ImageButton btnVolver;
    Button btnPagar;
    TextView tvTotal;
    int precioTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_cliente);
        btnVolver = findViewById(R.id.btnVolver);
        btnPagar = findViewById(R.id.btnPagar);
        tvTotal = findViewById(R.id.tvTotal);
        String mesa = getIntent().getSerializableExtra("codMesa").toString();
        ClienteDto cliente = (ClienteDto)getIntent().getSerializableExtra("clienteDto");
        mListView = findViewById(R.id.lvDetCuenta);
        detalleNorm = new ArrayList<DetalleCuentaNormalizado>();
        listarDetalleMesa("http://smartdrunk.freetzi.com/SmartDrunk/buscarDetalleCuenta.php?mesa=" + mesa);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desactivaDetalle("http://smartdrunk.freetzi.com/SmartDrunk/desactivaDetalleCuenta.php", mesa);
                Intent volverPrincipal = new Intent(getApplicationContext(), MainActivity.class);
                volverPrincipal.putExtra("finaliza", "si");
                startActivity(volverPrincipal);
                finish();
            }
        });
    }

    private void listarDetalleMesa(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        DetalleCuentaNormalizado dto = new DetalleCuentaNormalizado();
                        jsonObject = response.getJSONObject(i);
                        dto.setCodProducto(jsonObject.getInt("codProducto"));
                        dto.setNombreProducto(jsonObject.getString("nombre"));
                        dto.setPrecioProd(jsonObject.getInt("precio"));
                        dto.setCodMesa(jsonObject.getInt("mesa"));
                        dto.setCantProd(jsonObject.getInt("cantidadProd"));
                        calculaTotal(dto.getPrecioProd(), dto.getCantProd());
                        Log.d("DTO-", dto.getNombreProducto());
                        agregaListado(dto);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No hay productos", Toast.LENGTH_SHORT).show();
            }

        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void agregaListado(DetalleCuentaNormalizado dto){
        detalleNorm.add(dto);
        AdaptadorDetalleCuenta adaptador = new AdaptadorDetalleCuenta(this, R.layout.adaptador_detalle_cuenta, detalleNorm);
        mListView.setAdapter(adaptador);
    }

    private void desactivaDetalle(String URL, String mesa){
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                }else{
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CuentaCliente.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("codMesa", mesa);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void calculaTotal(int precio, int cantidad){
        precio = precio * cantidad;
        precioTotal += precio;
        tvTotal.setText(String.valueOf(precioTotal));
    }
}
