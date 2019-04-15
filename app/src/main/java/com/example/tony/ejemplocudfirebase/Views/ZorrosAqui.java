package com.example.tony.ejemplocudfirebase.Views;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tony.ejemplocudfirebase.MainActivity;
import com.example.tony.ejemplocudfirebase.Modelo.Informe;
import com.example.tony.ejemplocudfirebase.R;
import com.google.firebase.database.DatabaseReference;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZorrosAqui extends Fragment {

        private Button guardar, tomar_fotos;
        private EditText ubicacion, descripcion;
        private MainActivity activity;
        private DatabaseReference databaseReference;
        private ImageView foto;
        // http://www.tutorialesprogramacionya.com/javaya/androidya/androidstudioya/detalleconcepto.php?codigo=50&inicio=40
        // para poner foto en mi app

        private static final int PICK_IMAGE = 100;
        Uri imageUri;
        public ZorrosAqui() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_zorros_aqui, container, false);
        guardar = (Button)view.findViewById(R.id.bGuardar);
        tomar_fotos = (Button)view.findViewById(R.id.bFotos);
        ubicacion = (EditText) view.findViewById(R.id.txt_informe);
        descripcion = (EditText) view.findViewById(R.id.txt_Descripcion);
        foto = (ImageView)view.findViewById(R.id.imagenCaptura);

        activity.inicializarFirebase();
        tomar_fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gusrdarInforme();

            }
        });







        return view;
    }



    public void gusrdarInforme() {

        Informe i = new Informe();
        i.setNombre(ubicacion.getText().toString());
        i.setDescripcion(descripcion.getText().toString());
        i.setFoto(foto);
        databaseReference.child("Zorros").setValue(i);
        Toast.makeText(getContext(), "Se agrego " + i + " a nuestra base de datos", Toast.LENGTH_LONG).show();
        limpiarCamposInforme();


    }

    public void limpiarCamposInforme() {
            ubicacion.setText("");
            descripcion.setText("");


    }
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
           foto.setImageURI(imageUri);
        }
    }

}
