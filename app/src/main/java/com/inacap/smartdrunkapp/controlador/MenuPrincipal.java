package com.inacap.smartdrunkapp.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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
import com.inacap.smartdrunkapp.dto.MesaDto;

import java.util.HashMap;
import java.util.Map;

public class MenuPrincipal extends AppCompatActivity {

    TextView tvCodMesa, tvUsuario;
    Button btnMenu, btnCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        tvCodMesa = findViewById(R.id.tvCodMesa);
        tvUsuario = findViewById(R.id.tvUsuario);
        btnMenu = findViewById(R.id.btnMenu);
        btnCuenta = findViewById(R.id.btnCuenta);
        String mesa = getIntent().getSerializableExtra("codMesa").toString();
        ClienteDto cliente = (ClienteDto)getIntent().getSerializableExtra("clienteDto");
        tvCodMesa.setText(mesa);
        tvUsuario.setText(nombreClienteUpperCase(cliente.getNombre()));
        MesaDto mesaDto = new MesaDto();
        mesaDto.setCodCliente(cliente.getId());
        mesaDto.setCodMesa(Integer.valueOf(mesa));
        mesaDto.setSt(1);
        registraMesa("http://smartdrunk.freetzi.com/SmartDrunk/insertMesa.php", mesaDto);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accesoMenu = new Intent(getApplicationContext(), Menu.class);
                accesoMenu.putExtra("codMesa", mesa);
                accesoMenu.putExtra("clienteDto",cliente);
                startActivity(accesoMenu);
            }
        });

        btnCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accesoCuenta = new Intent(getApplicationContext(), CuentaCliente.class);
                accesoCuenta.putExtra("codMesa", mesa);
                accesoCuenta.putExtra("clienteDto",cliente);
                startActivity(accesoCuenta);
            }
        });
    }

    private void registraMesa (String URL, MesaDto dto){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error en el registro", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,0);
                    toast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("codMesa", String.valueOf(dto.getCodMesa()));
                parametros.put("codCliente", String.valueOf(dto.getCodCliente()));
                parametros.put("st", String.valueOf(dto.getSt()));
                Log.d("DTO-", String.valueOf(dto.getCodMesa()));
                Log.d("DTO-", String.valueOf(dto.getCodCliente()));
                Log.d("DTO-", String.valueOf(dto.getSt()));
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public String nombreClienteUpperCase(String nombre){
        if(nombre.isEmpty()){
            return nombre;
        }else{
            return Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1);
        }
    }
}
