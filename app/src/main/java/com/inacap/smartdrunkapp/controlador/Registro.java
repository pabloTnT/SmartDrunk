package com.inacap.smartdrunkapp.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
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
import java.util.regex.Pattern;


public class Registro extends AppCompatActivity {

    TextInputLayout tilCorreo, tilNombre, tilContraseña;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        tilCorreo = findViewById(R.id.tilCorreo);
        tilNombre = findViewById(R.id.tilNombre);
        tilContraseña = findViewById(R.id.tilContraseña);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClienteDto cliente = new ClienteDto();
                cliente.setNombre(tilNombre.getEditText().getText().toString());
                cliente.setCorreo(tilCorreo.getEditText().getText().toString());
                cliente.setContraseña(tilContraseña.getEditText().getText().toString());
                if(cliente.getNombre().isEmpty() || cliente.getCorreo().isEmpty() || cliente.getContraseña().isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(), "Por favor complete todos los datos", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }else if ( validarCorreo(cliente.getCorreo()) && validaContraseña(cliente.getContraseña())) {
                    registroCliente("http://smartdrunk.freetzi.com/SmartDrunk/insertCliente.php", cliente);
                }

            }
        });
    }

    private void registroCliente(String URL, ClienteDto dto) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Registro.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registro.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre", dto.getNombre());
                parametros.put("correo", dto.getCorreo());
                parametros.put("contraseña", dto.getContraseña());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean validarCorreo(String correo) {
        boolean res;
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        res = pattern.matcher(correo).matches();
        if (res) {
            res = true;
        } else {
            res = false;
            Toast toast = Toast.makeText(getApplicationContext(), "Correo invalido", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
        return res;
    }

    private boolean validaContraseña(String contraseña) {
        if (contraseña.length() < 4) {
            Toast toast = Toast.makeText(getApplicationContext(), "La Contraseña debe tener al menos 4 carácteres", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            return false;
        }
        return true;
    }
}



