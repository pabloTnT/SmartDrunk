package com.inacap.smartdrunkapp.controlador;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.inacap.smartdrunkapp.R;
import com.inacap.smartdrunkapp.dto.ClienteDto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    Button btnIngresar, btnRegistrar;
    TextInputLayout tilCorreo, tilContraseña;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tilCorreo = findViewById(R.id.tilCorreo);
        tilContraseña = findViewById(R.id.tilContraseña);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrar = findViewById(R.id.btnCrearCuenta);
        try{
            String mensaje = getIntent().getSerializableExtra("finaliza").toString();
            if(!mensaje.equals("") || !mensaje.equals(null)){
                Toast.makeText(getApplicationContext(), "Gracias por su compra", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }
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
                    accesoCliente("http://smartdrunk.freetzi.com/SmartDrunk/buscarCliente.php?correo=" + cliente.getCorreo(), cliente);
                }else{
                    Toast.makeText(MainActivity.this, "Por favor complete los datos de acceso", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void accesoCliente(String URL, ClienteDto dtoView){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        ClienteDto dto = new ClienteDto();
                        jsonObject = response.getJSONObject(i);
                        dto.setId(jsonObject.getInt("id"));
                        dto.setNombre(jsonObject.getString("nombre"));
                        dto.setCorreo(jsonObject.getString("correo"));
                        dto.setContraseña(jsonObject.getString("contraseña"));
                        if(validarCliente(dto, dtoView)){
                            Intent ingresarApp = new Intent(getApplicationContext(), LecturaCodigo.class);
                            ingresarApp.putExtra("clienteDto",dto);
                            startActivity(ingresarApp);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Datos de acceso incorrectos", Toast.LENGTH_SHORT).show();
            }

        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private boolean validarCliente(ClienteDto dto, ClienteDto dtoView){
        if(dto.getCorreo().equals(dtoView.getCorreo()) && dto.getContraseña().equals(dtoView.getContraseña())){
            return true;
        }else{
            Toast.makeText(getApplicationContext(), "Datos de acceso incorrectos", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
