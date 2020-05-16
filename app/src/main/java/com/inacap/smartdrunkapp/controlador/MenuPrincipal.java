package com.inacap.smartdrunkapp.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.inacap.smartdrunkapp.R;
import com.inacap.smartdrunkapp.dto.ClienteDto;

public class MenuPrincipal extends AppCompatActivity {

    TextView tvCodMesa, tvUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        ClienteDto cliente = (ClienteDto) getIntent().getExtras().getSerializable("clienteDto");
        tvCodMesa = findViewById(R.id.tvCodMesa);
        tvUsuario = findViewById(R.id.tvUsuario);
        tvCodMesa.setText(getIntent().getExtras().getString("codMesa"));
        tvUsuario.setText(cliente.getNombre());
    }
}
