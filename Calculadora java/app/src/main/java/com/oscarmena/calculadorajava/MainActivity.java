package com.oscarmena.calculadorajava;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {

    private EditText etValorA, etValorB;

    private TextView tvNumero;

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

        etValorA = findViewById(R.id.etValorA);
        etValorB = findViewById(R.id.etValorB);
        tvNumero = findViewById(R.id.tvNumero);

        Button btSuma = findViewById(R.id.btSuma);
        btSuma.setOnClickListener(v->fnSuma());

        Button btResta = findViewById(R.id.btResta);
        btResta.setOnClickListener(v->fnResta());

        Button btMultiplicacion = findViewById(R.id.btMultiplicacion);
        btMultiplicacion.setOnClickListener(v->fnMultiplicacion());

        Button btDivision = findViewById(R.id.btDivision);
        btDivision.setOnClickListener(v->fnDivision());
    }

    private void fnSuma()
    {
        String strValorA = etValorA.getText().toString();
        String strValorB = etValorB.getText().toString();

        double dblValorA = Double.parseDouble(strValorA);
        double dblValorB = Double.parseDouble(strValorB);
        double dblNumero = 0;

        dblNumero = dblValorA + dblValorB;

        if(strValorA.isEmpty() || strValorB.isEmpty())
        {
            tvNumero.setText("Valores vacios");
        }
        else
        {
            tvNumero.setText(String.valueOf((int) dblNumero));
        }

    }

    private void fnResta()
    {
        String strValorA = etValorA.getText().toString();
        String strValorB = etValorB.getText().toString();

        double dblValorA = Double.parseDouble(strValorA);
        double dblValorB = Double.parseDouble(strValorB);
        double dblNumero = 0;

        dblNumero = dblValorA - dblValorB;

        if(strValorA.isEmpty() || strValorB.isEmpty())
        {
            tvNumero.setText("Valores vacios");
        }
        else
        {
            tvNumero.setText(String.valueOf((int) dblNumero));
        }

    }

    private void fnMultiplicacion()
    {
        String strValorA = etValorA.getText().toString();
        String strValorB = etValorB.getText().toString();

        double dblValorA = Double.parseDouble(strValorA);
        double dblValorB = Double.parseDouble(strValorB);
        double dblNumero = 0;

        dblNumero = dblValorA * dblValorB;

        if(strValorA.isEmpty() || strValorB.isEmpty())
        {
            tvNumero.setText("Valores vacios");
        }
        else
        {
            tvNumero.setText(String.valueOf((int) dblNumero));
        }

    }

    private void fnDivision() {
        String strValorA = etValorA.getText().toString();
        String strValorB = etValorB.getText().toString();

        if (strValorA.isEmpty() || strValorB.isEmpty()) {
            tvNumero.setText("Valores vacÃ­os");
            return;
        }

        double dblValorA = Double.parseDouble(strValorA);
        double dblValorB = Double.parseDouble(strValorB);

        if (dblValorB == 0) {
            tvNumero.setText("No se puede dividir por 0");
            return;
        }

        double dblNumero = dblValorA / dblValorB;
        tvNumero.setText(String.valueOf((int) dblNumero));
    }


}