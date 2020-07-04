package com.inacap.smartdrunkapp.controlador;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
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

import java.util.regex.Pattern;


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
            if(!mensaje.isEmpty()){
                Toast toast = Toast.makeText(getApplicationContext(), "Gracias por su compra", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
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
                if(cliente.getCorreo().isEmpty() || cliente.getContraseña().isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(), "Por favor complete todos los datos", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }else if(validarCorreo(cliente.getCorreo()) && validaContraseña(cliente.getContraseña())){
                    accesoCliente("http://smartdrunk.freetzi.com/SmartDrunk/buscarCliente.php?correo=" + cliente.getCorreo(), cliente);
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
            Toast toast = Toast.makeText(getApplicationContext(), "Datos de acceso incorrectos", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
        return false;
    }

    private boolean validarCorreo(String correo){
        boolean res;
            Pattern pattern = Patterns.EMAIL_ADDRESS;
            res = pattern.matcher(correo).matches();
            if(res){
                res = true;
            }else{
                res = false;
                Toast toast = Toast.makeText(getApplicationContext(),"Correo invalido", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
            }
            return  res;
    }

    private boolean validaContraseña(String contraseña){
        boolean res;
        if(contraseña.length()<4){
            Toast toast = Toast.makeText(getApplicationContext(),"La Contraseña debe tener al menos 4 carácteres", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            return false;
        }
        return true;
    }
}
