package com.example.tony.ejemplocudfirebase.Views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tony.ejemplocudfirebase.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Lista_de_informes extends Fragment {


    public Lista_de_informes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista  =inflater.inflate(R.layout.fragment_lista_de_informes, container, false);



        return vista;
    }

}
