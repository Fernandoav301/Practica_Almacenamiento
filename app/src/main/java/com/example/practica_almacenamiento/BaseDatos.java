package com.example.practica_almacenamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BaseDatos extends AppCompatActivity {
    EditText nombre, materia;
    Button guardar, leerb, eliminar, update;
    TextView contenidob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_datos);
        nombre=(EditText)findViewById(R.id.nombre);
        materia=(EditText)findViewById(R.id.materia);
        guardar=(Button) findViewById(R.id.guardar);
        leerb=(Button) findViewById(R.id.leerb);
        eliminar=(Button) findViewById(R.id.eliminar);
        update=(Button) findViewById(R.id.update);
        contenidob=(TextView) findViewById(R.id.contenidob);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombre.getText().toString().equals("")&&!materia.getText().toString().equals(""))
                GuardarRegistro(nombre.getText().toString(),materia.getText().toString());

                else
                    Toast.makeText(getApplicationContext(), "Debes capturar en ambos campos", Toast.LENGTH_SHORT).show();

            }

        });
        leerb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeerRegistro();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarRegistro(nombre.getText().toString());
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarRegistro(nombre.getText().toString(),materia.getText().toString());
            }
        });
    }

    private void actualizarRegistro(String nombres, String materias) {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(this ,"UniversidadBD", null, 1);
        SQLiteDatabase basedatos= admin.getReadableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("materia", materias);
        basedatos.update("alumnos",registro,"nombre='"+nombres+"'", null);
        basedatos.close();
        nombre.setText("");
        materia.setText("");
    }

    private void eliminarRegistro(String nombres) {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(this, "UniversidadBD", null, 1);
        SQLiteDatabase basedatos = admin.getReadableDatabase();
        basedatos.delete( "alumnos", "nombre='"+nombres+"'", null);
        basedatos.close();
        Toast.makeText(getApplicationContext(), "Registro Eliminado Con Exito!! ", Toast.LENGTH_SHORT).show();
        nombre.setText("");

    }

    private void LeerRegistro() {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(this, "UniversidadBD", null, 1);
        SQLiteDatabase basedatos = admin.getReadableDatabase();
        try{
            Cursor cursor = basedatos.rawQuery("SELECT * FROM alumnos", null);
            String cont="";
            while (cursor.moveToNext()){
                cont+="Nombre :"+cursor.getString(1)+" Materia:"+cursor.getString(2)+"\n";
            }
            cursor.close();
            contenidob.setText(cont);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    private void GuardarRegistro(String nombres, String materias) {
        AdminSQLITEHelper admin = new AdminSQLITEHelper(getApplicationContext(), "UniversidadBD", null, 1);
        SQLiteDatabase basedatos= admin.getReadableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombres);
        registro.put("materia", materias);
        basedatos.insert("alumnos", null, registro);
        basedatos.close();
        Toast.makeText(getApplicationContext(), "Registro Insertado con exito", Toast.LENGTH_SHORT).show();
        nombre.setText("");
        materia.setText("");
    }

}