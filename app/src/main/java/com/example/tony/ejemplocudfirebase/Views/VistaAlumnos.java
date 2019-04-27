package com.example.tony.ejemplocudfirebase.Views;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tony.ejemplocudfirebase.Modelo.Profesor;
import com.example.tony.ejemplocudfirebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VistaAlumnos extends Fragment {

    private ListView list_alumnos;
    private String Base_de_Datos="Profesor";

    private List<Profesor> listaAlumnos = new ArrayList<Profesor>();
    ArrayAdapter<Profesor> arrayAdapterPersona;

    DatabaseReference databaseReference;
    private Button consulta;
    private EditText campoConsulta;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista  =inflater.inflate(R.layout.fragment_vista_alumnos, container, false);
        campoConsulta = (EditText)vista.findViewById(R.id.et_consulta_dni);
        consulta = (Button) vista.findViewById(R.id.boton_consulta);
        list_alumnos = (ListView)vista.findViewById(R.id.listaVistaAlumnos);
        inicializarFirebase();
        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_consulta=campoConsulta.getText().toString();
                buscarAlumno(txt_consulta);


            }
        });



        return vista;
    }

    public void buscarAlumno(String txt_consulta) {

        Toast.makeText(getActivity().getApplicationContext(),
                "Cargando .....! ", Toast.LENGTH_LONG).show();

        final Query consulta = databaseReference.child(Base_de_Datos).orderByChild("dni_alumno").equalTo(txt_consulta);
        consulta.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaAlumnos.clear();
                for (DataSnapshot nodo : dataSnapshot.getChildren()){

                    Profesor p = nodo.getValue(Profesor.class);
                    //Profesor p = new Profesor();

                    listaAlumnos.add(p);

                    arrayAdapterPersona = new ArrayAdapter<Profesor>(getActivity(),
                            android.R.layout.simple_list_item_1, listaAlumnos);
                    list_alumnos.setAdapter(arrayAdapterPersona);


                }
                Toast.makeText(getActivity().getApplicationContext(),
                        "ESTADO ACADEMICO CARGADO", Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "ERROR AL CARGAR ESTADO ACADEMICO", Toast.LENGTH_LONG).show();
            }
        });


            }




    public void inicializarFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

}


/*databaseReference.child(Base_de_Datos).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaAlumnos.clear();   // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){

                        Profesor p = objSnaptshot.getValue(Profesor.class);
                        listaAlumnos.add(p);

                        arrayAdapterPersona = new ArrayAdapter<Profesor>(getActivity(),
                                android.R.layout.simple_list_item_1, listaAlumnos);
                        list_alumnos.setAdapter(arrayAdapterPersona);


                    }
                Toast.makeText(getActivity().getApplicationContext(),
                        "Listado Actualizado", Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(null, "Failed to read value.", error.toException());
                Toast.makeText(getContext(),"ERROR", Toast.LENGTH_LONG).show();
            }
        });
*/