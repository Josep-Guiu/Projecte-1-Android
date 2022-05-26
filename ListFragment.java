package com.example.registro_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListFragment extends Fragment {

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        return view;
    }

    public void addListDataGeneral(Usuario [] usuariosConPuntuaciones, Avatar[] avatares, Button boton){

        View view = getView();

        assert view != null;
        RecyclerView lstPuntosTotales = (RecyclerView) view.findViewById(R.id.LstRank);

        MyAdapter adaptador = new MyAdapter(getActivity(), usuariosConPuntuaciones, avatares, boton);
        lstPuntosTotales.setHasFixedSize(true);
        lstPuntosTotales.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        lstPuntosTotales.setAdapter(adaptador);

    }
}