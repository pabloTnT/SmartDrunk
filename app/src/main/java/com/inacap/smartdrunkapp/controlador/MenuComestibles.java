package com.inacap.smartdrunkapp.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.inacap.smartdrunkapp.R;
import com.inacap.smartdrunkapp.dto.AdaptadorProducto;
import com.inacap.smartdrunkapp.dto.ClienteDto;
import com.inacap.smartdrunkapp.dto.ProductoDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuComestibles extends AppCompatActivity {
    ImageButton btnVolverAtras;
    private ArrayList<ProductoDto> productos;
    ListView mListView;
    RequestQueue requestQueue;
    String mesa;
    ClienteDto cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_comestibles);
        btnVolverAtras = findViewById(R.id.btnVolverAtras);
        mListView = findViewById(R.id.lvComestibles);
        productos = new ArrayList<ProductoDto>();
        mesa = getIntent().getSerializableExtra("codMesa").toString();
        cliente = (ClienteDto)getIntent().getSerializableExtra("clienteDto");
        listaComestibles("http://smartdrunk.freetzi.com/SmartDrunk/buscarComestibles.php");
        btnVolverAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detalleProducto = new Intent(getApplicationContext(), DetalleProducto.class);
                detalleProducto.putExtra("producto", productos.get(position));
                detalleProducto.putExtra("codMesa", mesa);
                detalleProducto.putExtra("clienteDto",cliente);
                startActivity(detalleProducto);
            }
        });
    }

    private void listaComestibles(String URL){
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
                        Toast toast = Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,0);
                        toast.show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(), "No hay productos", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
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
