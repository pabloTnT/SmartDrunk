package com.inacap.smartdrunkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.inacap.smartdrunkapp.dto.AdaptadorProducto;
import com.inacap.smartdrunkapp.dto.ProductoDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CuentaCliente extends AppCompatActivity {
    private ArrayList<ProductoDto> productos;
    ListView mListView;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_cliente);
        mListView = findViewById(R.id.lvDetalleCuenta);
        productos = new ArrayList<ProductoDto>();
        listarDetalleMesa("http://smartdrunk.freetzi.com/SmartDrunk/buscarDetalleCuenta.php");
    }

    private void listarDetalleMesa(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        ProductoDto dto = new ProductoDto();
                        jsonObject = response.getJSONObject(i);
                        dto.setCodProducto(jsonObject.getInt("codProducto"));
                        dto.setNombre(jsonObject.getString("nombre"));
                        dto.setPrecio(jsonObject.getInt("tipoProd"));
                        dto.setPrecio(jsonObject.getInt("precio"));
                        dto.setSt(jsonObject.getInt("st"));
                        Log.d("DTO-", String.valueOf(dto.getNombre()));
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

    private void agregaListado(ProductoDto dto){
        productos.add(dto);
        AdaptadorProducto adaptador = new AdaptadorProducto(this, R.layout.adaptador_producto, productos);
        mListView.setAdapter(adaptador);
    }
}
