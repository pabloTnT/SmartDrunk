package com.inacap.smartdrunkapp.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.inacap.smartdrunkapp.R;

public class Menu extends AppCompatActivity {

    private ImageButton btnAtras;
    private Button btnComestibles, btnBebestibles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnAtras = findViewById(R.id.btnAtras);
        btnComestibles = findViewById(R.id.btnComestibles);
        btnBebestibles = findViewById(R.id.btnBebestibles);

        btnComestibles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {ingresoComestibles();}
        });
        btnBebestibles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {ingresoBebestibles();}
        });
    }

    private void ingresoComestibles(){
        Intent ingresoCartaComestible = new Intent(getApplicationContext(), MenuComestibles.class);
        startActivity(ingresoCartaComestible);
    }

    private void ingresoBebestibles(){
        Intent ingresoCartaBebestibles = new Intent(getApplicationContext(), MenuBebestibles.class);
        startActivity(ingresoCartaBebestibles);
    }
}
