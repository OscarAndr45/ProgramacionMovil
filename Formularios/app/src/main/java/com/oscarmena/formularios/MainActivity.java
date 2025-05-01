package com.oscarmena.formularios;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btningresar, btnConsultar, btnModificar, btnEliminar, btncontacto;

    private ListView lvItem;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btningresar = findViewById(R.id.btnIngresar);
        btnConsultar = findViewById(R.id.btnconsultar);
        btnModificar = findViewById(R.id.btnmodificar);
        btnEliminar = findViewById(R.id.btnRetirar);
        btncontacto= findViewById(R.id.Btncontacto);
        lvItem = findViewById(R.id.lvitem);
        dbHandler = new DBHandler(this);

        btningresar.setOnClickListener(v -> irAIngresar());
        btnConsultar.setOnClickListener(v -> irAConsultar());
        btnModificar.setOnClickListener(v -> irAModificar());
        btnEliminar.setOnClickListener(v -> irAEliminar());
        btncontacto.setOnClickListener(v -> irAContacto());
        cargarRegistrosEnListView();
    }

    private void irAIngresar() {
        Intent intent =new Intent(MainActivity.this, ingresar.class);
        startActivity(intent);
    }

    private void irAConsultar() {
        startActivity(new Intent(MainActivity.this, consultar.class));
    }

    private void irAModificar() {
        startActivity(new Intent(MainActivity.this, modificar.class));
    }

    private void irAEliminar() {
        startActivity(new Intent(MainActivity.this, eliminar.class));
    }
    private void irAContacto() {
        startActivity(new Intent(MainActivity.this, Contacto.class));
    }
    private void cargarRegistrosEnListView() {
        List<String> registros = dbHandler.obtenerTodosLosRegistros();

        if (!registros.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, registros);
            lvItem.setAdapter(adapter);
        }
    }
}