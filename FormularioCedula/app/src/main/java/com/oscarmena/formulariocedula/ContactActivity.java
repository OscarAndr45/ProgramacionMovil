package com.oscarmena.formulariocedula;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.TextView;


public class ContactActivity extends AppCompatActivity {
    private TextView Tvdatoscontacto;
    private Button btnvolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnvolver=findViewById(R.id.Btnvolver);
        Tvdatoscontacto=findViewById(R.id.tvdatoscontacto);
        Tvdatoscontacto.setText("Nombre: Oscar Andres \n Documento: 1078456414");
        btnvolver.setOnClickListener(v -> volver());

    }
    private void volver(){

        finish();
    }
}