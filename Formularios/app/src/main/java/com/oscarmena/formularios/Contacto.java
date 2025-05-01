package com.oscarmena.formularios;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.TextView;


public class Contacto extends AppCompatActivity {
    private TextView Tvdatoscontacto;
    private Button btnvolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnvolver=findViewById(R.id.Btnvolver);
        Tvdatoscontacto=findViewById(R.id.tvdatoscontacto);
        Tvdatoscontacto.setText("Nombre: Oscar Andres \n Documento: 1078456414 \n Telefono: 3222385048 \n Direccion: Calle 57dd#23a16 ");
        btnvolver.setOnClickListener(v -> volver());

    }
    private void volver(){

        finish();
    }
}