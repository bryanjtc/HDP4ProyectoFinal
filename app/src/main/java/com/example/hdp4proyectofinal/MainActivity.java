package com.example.hdp4proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioButton rbGravedad = findViewById(R.id.opcionGravedad);
        RadioButton rbBrujula = findViewById(R.id.opcionBrujula);
        RadioButton rbHuella = findViewById(R.id.opcionHuella);
        RadioButton rbRostro = findViewById(R.id.opcionRostro);
        Button btnContinuar = findViewById(R.id.btnContinuar);
        final Boolean[] isSelectedGravedad = new Boolean[1];
        final Boolean[] isSelectedBrujula = new Boolean[1];
        final Boolean[] isSelectedHuella = new Boolean[1];
        final Boolean[] isSelectedRostro = new Boolean[1];

        btnContinuar.setEnabled(false);

        rbGravedad.setOnClickListener(v -> {
            isSelectedGravedad[0] = rbGravedad.isChecked();
            if (isSelectedGravedad[0]) {
                btnContinuar.setEnabled(true);
                rbBrujula.setChecked(false);
                rbHuella.setChecked(false);
                rbRostro.setChecked(false);
                isSelectedBrujula[0] = false;
                isSelectedHuella[0] = false;
                isSelectedRostro[0] = false;
            }
        });

        rbBrujula.setOnClickListener(v -> {
            isSelectedBrujula[0] = rbBrujula.isChecked();
            if (isSelectedBrujula[0]) {
                btnContinuar.setEnabled(true);
                rbGravedad.setChecked(false);
                rbHuella.setChecked(false);
                rbRostro.setChecked(false);
                isSelectedGravedad[0] = false;
                isSelectedHuella[0] = false;
                isSelectedRostro[0] = false;
            }
        });

        rbHuella.setOnClickListener(v -> {
            isSelectedHuella[0] = rbHuella.isChecked();
            if (isSelectedHuella[0]) {
                btnContinuar.setEnabled(true);
                rbGravedad.setChecked(false);
                rbBrujula.setChecked(false);
                rbRostro.setChecked(false);
                isSelectedGravedad[0] = false;
                isSelectedBrujula[0] = false;
                isSelectedRostro[0] = false;
            }
        });

        rbRostro.setOnClickListener(v -> {
            isSelectedRostro[0] = rbRostro.isChecked();
            if (isSelectedRostro[0]) {
                btnContinuar.setEnabled(true);
                rbGravedad.setChecked(false);
                rbBrujula.setChecked(false);
                rbHuella.setChecked(false);
                isSelectedGravedad[0] = false;
                isSelectedBrujula[0] = false;
                isSelectedHuella[0] = false;
            }
        });

        btnContinuar.setOnClickListener(v -> {
            if (isSelectedGravedad[0]) {
                Intent intentGravedad = new Intent(getApplicationContext(), Gravedad.class);
                startActivity(intentGravedad);
            }
            if (isSelectedHuella[0]) {
                Intent intentHuella = new Intent(getApplicationContext(), HuellaDactilar.class);
                startActivity(intentHuella);
            }
        });
    }
}