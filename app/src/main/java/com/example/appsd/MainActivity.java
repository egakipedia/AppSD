package com.example.appsd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.BufferOverflowException;

public class MainActivity extends AppCompatActivity {

    private EditText et_nombre, et_contenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_contenido = (EditText) findViewById(R.id.et_contenido);
    }

    // Métido para el botón Guardar
    public void Guardar(View view){
        String nombre = et_nombre.getText().toString();
        String contenido = et_contenido.getText().toString();

        try {
            // Indicamos donde se encuentra la tarjeta SD
            File tarjetaSD = Environment.getExternalStorageDirectory();
            Toast.makeText(this, tarjetaSD.getPath(), Toast.LENGTH_SHORT).show();
            // Creamos el archivo con el nombre que nos indica el usuario, y el PATH de la tarjeta SD
            File rutaArchivo = new File(tarjetaSD.getPath(), nombre);
            // Abrimos el archivo
            OutputStreamWriter crearArchivo = new OutputStreamWriter(openFileOutput(nombre, Activity.MODE_PRIVATE));

            //Guardamos los datos en el archivo
            crearArchivo.write(contenido);
            // Limpiamos el Buffer
            crearArchivo.flush();
            crearArchivo.close();

            Toast.makeText(this, "El archivo se ha guardado correctamente", Toast.LENGTH_SHORT).show();
            et_nombre.setText("");
            et_contenido.setText("");

        }catch (IOException e){
            Toast.makeText(this, "No se pudo guardar el archivo.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para el botón Consultar
    public void Consultar(View view){
        String nombre = et_nombre.getText().toString();

        try {
            File tarjetaSD = Environment.getExternalStorageDirectory();
            File rutaArchivo = new File(tarjetaSD.getPath(), nombre);
            InputStreamReader abrirArchivo = new InputStreamReader(openFileInput(nombre));

            BufferedReader leerArchivo = new BufferedReader(abrirArchivo);
            String linea = leerArchivo.readLine();
            String contenidoCompleto = "";

            while (linea != null){
                contenidoCompleto = contenidoCompleto + linea + "\n";
                linea = leerArchivo.readLine();
            }

            leerArchivo.close();
            abrirArchivo.close();
            et_contenido.setText(contenidoCompleto);

        }catch (IOException e){
            Toast.makeText(this, "Error al leer el archivo", Toast.LENGTH_SHORT).show();
        }

    }
}