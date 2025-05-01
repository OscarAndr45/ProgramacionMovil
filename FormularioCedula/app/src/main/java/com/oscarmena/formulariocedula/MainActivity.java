package com.oscarmena.formulariocedula;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private TextView Resultado;
    private EditText Documento;
    private Button Guardar,Mostrar,contacto;
    private DBHandler dbhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Documento = findViewById(R.id.Etdocumento);
        Resultado = findViewById(R.id.tvResultado);
        Guardar = findViewById(R.id.Btnguardar);
        Mostrar = findViewById(R.id.Btnmostrar);
        dbhandler = new DBHandler(this);
        contacto = findViewById(R.id.Btncontacto);
        Guardar.setOnClickListener(v -> Guardar());
        Mostrar.setOnClickListener(v -> Mostrar());
        contacto.setOnClickListener(v -> Contacto());
    }
    private void Contacto(){
        Intent intent=new Intent(MainActivity.this,ContactActivity.class);
                startActivity(intent);
    }

    private void Guardar(){
        String documento = Documento.getText().toString();

        if (documento.isEmpty())
        {
            Message.message(this, "Campo Vacio");
            return;
        }
        dbhandler.ingresar(documento);

        Message.message(this, "Ingreso Exitoso");
    }

    private void Mostrar(){
        String resultado = dbhandler.consultar();
        Resultado.setText(resultado);
        Message.message(this,resultado);
    }

}