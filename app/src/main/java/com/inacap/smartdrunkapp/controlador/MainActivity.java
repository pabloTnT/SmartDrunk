package com.inacap.smartdrunkapp.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.inacap.smartdrunkapp.R;
import com.inacap.smartdrunkapp.dto.ClienteDto;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnIngresar, btnRegistrar;
    TextInputLayout tilCorreo, tilContraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tilCorreo = findViewById(R.id.tilCorreo);
        tilContraseña = findViewById(R.id.tilContraseña);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrar = findViewById(R.id.btnCrearCuenta);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ingresarRegistro = new Intent(MainActivity.this, Registro.class);
                startActivity(ingresarRegistro);
            }
        });
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClienteDto cliente = new ClienteDto();
                cliente.setCorreo(tilCorreo.getEditText().getText().toString());
                cliente.setContraseña(tilContraseña.getEditText().getText().toString());
                if(!cliente.getCorreo().isEmpty() && !cliente.getContraseña().isEmpty()){
                    validarCliente("http://smartdrunk.freetzi.com/SmartDrunk/validaCliente.php", cliente);
                }else{
                    Toast.makeText(MainActivity.this, "Por favor complete los datos de acceso", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void validarCliente(String URL, ClienteDto dto){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent ingresarApp = new Intent(getApplicationContext(), LecturaCodigo.class);
                    ingresarApp.putExtra("clienteDto",dto);
                    startActivity(ingresarApp);
                }else{
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", dto.getCorreo());
                parametros.put("contraseña", dto.getContraseña());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void datosLogin(ClienteDto dto){
        SharedPreferences preferences = getSharedPreferences("datosLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombre", dto.getNombre());
        editor.putString("correo", dto.getCorreo());
        editor.putString("contraseña", dto.getContraseña());
        editor.commit();
    }
}
