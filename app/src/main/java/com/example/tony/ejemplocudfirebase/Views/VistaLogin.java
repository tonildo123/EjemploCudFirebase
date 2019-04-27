package com.example.tony.ejemplocudfirebase.Views;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tony.ejemplocudfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class VistaLogin extends Fragment {

    private EditText etUsuario, etPassword;
    private Button login, regitrarme;
    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_vista_login, container, false);
        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        etUsuario=(EditText)vista.findViewById(R.id.et_usuario);
        etPassword=(EditText)vista.findViewById(R.id.et_password);
        login=(Button) vista.findViewById(R.id.boton_login);
        regitrarme=(Button)vista.findViewById(R.id.boton_registrarme);
        progressDialog = new ProgressDialog(getContext());

        login.setOnClickListener(new View.OnClickListener() {@Override
            public void onClick(View v) {llamar_a_vista_profesor();}});
        regitrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamar_a_vista_registro();
            }
        });




        return vista;
    }
    public void llamar_a_vista_profesor(){


        //Obtenemos el email y la contraseña desde las cajas de texto
        final String email = etUsuario.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {//(precio.equals(""))
            Toast.makeText(getActivity(), "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();

        //loguear usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            boolean subcadena_alumno = email.contains("alumno");
                            boolean subcadena_profesor = email.contains("profesor");


                            if(subcadena_profesor==true){llamarVentanaProfesor();}
                            else if(subcadena_alumno==true){llamar_a_vista_alumno();}


                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(getActivity(), "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });






    }
    public void llamar_a_vista_alumno(){
        getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.pantallaPrimcipal,
                new VistaAlumnos()).addToBackStack(null).commit();}


    public void llamarVentanaProfesor() {
     getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.pantallaPrimcipal,
                new VistaProfesor()).addToBackStack(null).commit();
    }

    public void llamar_a_vista_registro(){
        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = etUsuario.getText().toString();
        String password = etPassword.getText().toString();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {//(precio.equals(""))
            Toast.makeText(getActivity(), "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        //registramos un nuevo usuario
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Se ha registrado el usuario con el email: " + etUsuario.getText(), Toast.LENGTH_LONG).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(getActivity(), "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }



}
