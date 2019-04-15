package com.example.tony.ejemplocudfirebase;


import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.Toast;

import com.example.tony.ejemplocudfirebase.Modelo.Contactos;
import com.example.tony.ejemplocudfirebase.Modelo.Informe;
import com.example.tony.ejemplocudfirebase.Views.ZorrosAqui;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText telefono, nombre,correo;
    private String Base_de_Datos = "Contactos";
    private ListView list_contactos;
    private Button anunciar;

    private List<Contactos> listacontactos = new ArrayList<Contactos>();
    ArrayAdapter<Contactos> arrayAdapterPersona;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    Contactos contactoSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        anunciar = (Button)findViewById(R.id.bAnunciar);
        nombre = (EditText)findViewById(R.id.txt_nombre);
        correo = (EditText)findViewById(R.id.txt_correo);
        telefono = (EditText)findViewById(R.id.txt_telefono);
        list_contactos = (ListView)findViewById(R.id.lista);

        inicializarFirebase();
        solicitarDatosFirebase();



        list_contactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contactoSelected = (Contactos) parent.getItemAtPosition(position);
                telefono.setText(contactoSelected.getTelefono());
                nombre.setText(contactoSelected.getNombre());
                correo.setText(contactoSelected.getCorreo());

            }
        });

        anunciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.pantallaPrimcipal, new ZorrosAqui())
                        .addToBackStack(null).commit();
            }
        });

    }

    public void inicializarFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void solicitarDatosFirebase(){
        databaseReference.child(Base_de_Datos).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listacontactos.clear();   // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Contactos p = objSnaptshot.getValue(Contactos.class);
                    listacontactos.add(p);

                    arrayAdapterPersona = new ArrayAdapter<Contactos>(MainActivity.this,
                            android.R.layout.simple_list_item_1, listacontactos);
                    list_contactos.setAdapter(arrayAdapterPersona);
                }

                Toast.makeText(getApplicationContext(),
                        "Listado Actualizado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(null, "Failed to read value.", error.toException());
                Toast.makeText(getApplicationContext(),"ERROR", Toast.LENGTH_LONG).show();
            }
        });


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        
        
        switch (item.getItemId()){
            case R.id.icon_add:{
                String tel = telefono.getText().toString();
                String nom = nombre.getText().toString();
                String email = correo.getText().toString();
                if (nom.equals("")||email.equals("")||tel.equals("")){
                    validacion();
                }
                else {

                    Contactos p = new Contactos();
                    p.setTelefono(tel);
                    p.setNombre(nom);
                    p.setCorreo(email);

                    databaseReference.child(Base_de_Datos).child(tel).setValue(p);
                    Toast.makeText(getApplicationContext(), "Se agrego " + p + " a nuestra base de datos", Toast.LENGTH_LONG).show();
                    limpiarCampos();
                }
                break;
            }// este es el actualizar
            case R.id.icon_save:{
                String tel = contactoSelected.getTelefono();
                String nom = nombre.getText().toString();
                String email = correo.getText().toString();
                Contactos p = new Contactos();

                p.setTelefono(tel);
                p.setNombre(nom);
                p.setCorreo(email);

                databaseReference.child("Contactos").child(p.getTelefono()).setValue(p);

                Toast.makeText(getApplicationContext(),"Actualizado", Toast.LENGTH_LONG).show();
                limpiarCampos();

            }
            case R.id.icon_delete:{

                Contactos p = new Contactos();
                p.setTelefono(contactoSelected.getTelefono());
                databaseReference.child("Contactos").child(p.getTelefono()).removeValue();
                Toast.makeText(getApplicationContext(),"Eliminado", Toast.LENGTH_LONG).show();
                limpiarCampos();
                break;
            }
            default:break;
        }
        return true;
    }

    private void validacion() {
        String nom = nombre.getText().toString();
        String email = correo.getText().toString();
        String tel = telefono.getText().toString();

        if (nom.equals("")){
            nombre.setError("Required");
        }

        else if (email.equals("")){
            correo.setError("Required");
        }
        else if (tel.equals("")){
            telefono.setError("Required");
        }
    }



    public void limpiarCampos() {


        telefono.setText("");
        nombre.setText("");
        correo.setText("");
    }



}

