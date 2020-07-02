package com.inacap.smartdrunkapp.controlador;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.inacap.smartdrunkapp.R;
import com.inacap.smartdrunkapp.dto.CaptureActivityPortrait;
import com.inacap.smartdrunkapp.dto.ClienteDto;

import java.util.HashMap;
import java.util.Map;


public class LecturaCodigo extends AppCompatActivity {

    private Button btnLeerCodigo;
    private TextInputLayout tilCodigoMesa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura_codigo);

        tilCodigoMesa = findViewById(R.id.tilCodigoMesa);
        btnLeerCodigo = findViewById(R.id.btnLeerCodigo);

        btnLeerCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });
    }

    private void escanear() {
                    IntentIntegrator integrator = new IntentIntegrator(this);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setPrompt("Escanear Codigo");
                    integrator.setOrientationLocked(false);
                    integrator.setCameraId(0);
                    integrator.setBeepEnabled(true);
                    integrator.setCaptureActivity(CaptureActivityPortrait.class);
                    integrator.setBarcodeImageEnabled(false);
                    integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        ClienteDto cliente = (ClienteDto)getIntent().getSerializableExtra("clienteDto");
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(LecturaCodigo.this, "Cancelaste Escanner", Toast.LENGTH_SHORT).show();
            }else{
                String codMesa = result.getContents().toString();
                tilCodigoMesa.getEditText().setText(codMesa);
                Intent ingresoMesa = new Intent(getApplicationContext(), MenuPrincipal.class);
                ingresoMesa.putExtra("codMesa", codMesa);
                ingresoMesa.putExtra("clienteDto",cliente);
                startActivity(ingresoMesa);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
