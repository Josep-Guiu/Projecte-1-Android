package com.example.registro_app;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RankActivity extends AppCompatActivity {

    /*private TabLayout tabLayout;
    private ViewPager viewPager;
    Avatar [] avatares;*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ranking_layout);

        Avatar[] avatares = new Avatar[0];
        //Cargar todos los avatares totales
        avatares = cargarArrayAvatares(avatares);
        String comparativo = "Total";
        ArrayList<Avatar> avataresTotales = new ArrayList<>();
//Recorremos la lista de avatares para setearle al usuario dependiendo de la puntuación
        for (Avatar item : avatares) {
            if (comparativo.equalsIgnoreCase(item.getGender())) {
                avataresTotales.add(item);
            }

        }
//Mostramos los puntos totales que lleva el usuario incrementandole los que ha generado en el ultimo juego.
        Intent intent = getIntent();
        Usuario[] usuariosConPuntuaciones = (Usuario[]) getIntent().getSerializableExtra("usuariosParaRank");

        Avatar[] arrayAvatares = (Avatar[]) getIntent().getSerializableExtra("arrayAvatares");

        Usuario[] usuariosGuardadosJson = (Usuario[]) getIntent().getSerializableExtra("usuariosParaRank");

        Usuario user = (Usuario) getIntent().getSerializableExtra("usuarioParaRank");
////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button general = findViewById(R.id.BtnAGeneral);
        Button rock = findViewById(R.id.BtnARock);
        Button rap = findViewById(R.id.BtnARap);
        Button pop = findViewById(R.id.BtnAPop);
        Button funk = findViewById(R.id.BtnAFunk);
        Button heavy = findViewById(R.id.BtnAHeavy);


        FragmentManager mgr = getSupportFragmentManager();
        ListFragment listFragment1 = (ListFragment) mgr.findFragmentById(R.id.FrgList);
/*
        usuariosConPuntuaciones = ordenarArrayUsuariosPuntosRap(usuariosConPuntuaciones);*/

        usuariosConPuntuaciones = ordenarArrayUsuariosPuntos(usuariosConPuntuaciones, general);
        listFragment1.addListDataGeneral(usuariosConPuntuaciones, arrayAvatares, general);



        boton1(general, usuariosConPuntuaciones, arrayAvatares, listFragment1);
        boton2(rock, usuariosConPuntuaciones, arrayAvatares, listFragment1);
        boton3(rap, usuariosConPuntuaciones, arrayAvatares, listFragment1);
        boton4(heavy, usuariosConPuntuaciones, arrayAvatares, listFragment1);
        boton5(funk, usuariosConPuntuaciones, arrayAvatares, listFragment1);
        boton6(pop, usuariosConPuntuaciones, arrayAvatares, listFragment1);

        Button devuelta = findViewById(R.id.BtnDevuelta3);
        devuelta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volverAMenuPrincipal = new Intent(RankActivity.this, MainMenuActivity.class);
                volverAMenuPrincipal.putExtra("usuariosRecibidos", usuariosGuardadosJson);
                volverAMenuPrincipal.putExtra("usuarioRecibido", user);

                startActivity(volverAMenuPrincipal);
                finish();
            }
        });

    }

    public void boton1(Button general, Usuario[] usuariosConPuntuaciones, Avatar[] arrayAvatares, ListFragment listFragment1) {
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario[] usuariosConPuntuaciones1;
                usuariosConPuntuaciones1 = ordenarArrayUsuariosPuntos(usuariosConPuntuaciones, general);
                listFragment1.addListDataGeneral(usuariosConPuntuaciones1, arrayAvatares, general);
            }
        });
    }

    public void boton2(Button rock, Usuario[] usuariosConPuntuaciones, Avatar[] arrayAvatares, ListFragment listFragment2) {
        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario[] usuariosConPuntuaciones1;
                usuariosConPuntuaciones1 = ordenarArrayUsuariosPuntos(usuariosConPuntuaciones, rock);
                listFragment2.addListDataGeneral(usuariosConPuntuaciones, arrayAvatares, rock);
            }
        });
    }

    public void boton3(Button rap, Usuario[] usuariosConPuntuaciones, Avatar[] arrayAvatares, ListFragment listFragment3) {
        rap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario[] usuariosConPuntuaciones1;
                usuariosConPuntuaciones1 = ordenarArrayUsuariosPuntos(usuariosConPuntuaciones, rap);
                listFragment3.addListDataGeneral(usuariosConPuntuaciones1, arrayAvatares, rap);
            }
        });
    }

    public void boton4(Button heavy, Usuario[] usuariosConPuntuaciones, Avatar[] arrayAvatares, ListFragment listFragment2) {
        heavy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario[] usuariosConPuntuaciones1;
                usuariosConPuntuaciones1 = ordenarArrayUsuariosPuntos(usuariosConPuntuaciones, heavy);
                listFragment2.addListDataGeneral(usuariosConPuntuaciones, arrayAvatares, heavy);
            }
        });
    }

    public void boton5(Button funk, Usuario[] usuariosConPuntuaciones, Avatar[] arrayAvatares, ListFragment listFragment5) {
        funk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario[] usuariosConPuntuaciones1;
                usuariosConPuntuaciones1 = ordenarArrayUsuariosPuntos(usuariosConPuntuaciones, funk);
                listFragment5.addListDataGeneral(usuariosConPuntuaciones, arrayAvatares, funk);
            }
        });
    }

    public void boton6(Button pop, Usuario[] usuariosConPuntuaciones, Avatar[] arrayAvatares, ListFragment listFragment6) {
        pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario[] usuariosConPuntuaciones1;
                usuariosConPuntuaciones1 = ordenarArrayUsuariosPuntos(usuariosConPuntuaciones, pop);
                listFragment6.addListDataGeneral(usuariosConPuntuaciones, arrayAvatares, pop);
            }
        });
    }


    //Ordenamos por puntuación máxima los usuarios mostrados.
    public Usuario[] ordenarArrayUsuariosPuntos(Usuario[] usuarios, Button boton) {


        for (int a = 1; a < usuarios.length; a++) {
            for (int b = 0; b < usuarios.length - a; b++) {
                switch (boton.getText().toString()) {
                    case "General":
                        if (usuarios[b].getTotal() < usuarios[b + 1].getTotal()) {
                            //swap movies[b] with movies[b+1]
                            Usuario temporalAuxiliar = usuarios[b];
                            usuarios[b] = usuarios[b + 1];
                            usuarios[b + 1] = temporalAuxiliar;
                        }
                        break;
                    case "Rock":
                        if (usuarios[b].getGender1() < usuarios[b + 1].getGender1()) {
                            //swap movies[b] with movies[b+1]
                            Usuario temporalAuxiliar = usuarios[b];
                            usuarios[b] = usuarios[b + 1];
                            usuarios[b + 1] = temporalAuxiliar;
                        }
                        break;
                    case "Rap":
                        if (usuarios[b].getGender2() < usuarios[b + 1].getGender2()) {
                            //swap movies[b] with movies[b+1]
                            Usuario temporalAuxiliar = usuarios[b];
                            usuarios[b] = usuarios[b + 1];
                            usuarios[b + 1] = temporalAuxiliar;
                        }
                        break;
                    case "Heavy":
                        if (usuarios[b].getGender3() < usuarios[b + 1].getGender3()) {
                            //swap movies[b] with movies[b+1]
                            Usuario temporalAuxiliar = usuarios[b];
                            usuarios[b] = usuarios[b + 1];
                            usuarios[b + 1] = temporalAuxiliar;
                        }
                        break;
                    case "Funk":
                        if (usuarios[b].getGender4() < usuarios[b + 1].getGender4()) {
                            //swap movies[b] with movies[b+1]
                            Usuario temporalAuxiliar = usuarios[b];
                            usuarios[b] = usuarios[b + 1];
                            usuarios[b + 1] = temporalAuxiliar;
                        }
                        break;
                    case "Pop":
                        if (usuarios[b].getGender5() < usuarios[b + 1].getGender5()) {
                            //swap movies[b] with movies[b+1]
                            Usuario temporalAuxiliar = usuarios[b];
                            usuarios[b] = usuarios[b + 1];
                            usuarios[b + 1] = temporalAuxiliar;
                        }
                        break;
                }
            }
        }


        return usuarios;
    }

    //Cargamos los avatares que se le mostrarán en el ranking.
    public Avatar[] cargarArrayAvatares(Avatar[] cargarAvatares) {
        String direccionArchivo = getFilesDir().getAbsolutePath();
        String filePath = direccionArchivo + File.separator + "JSON//avatares.json";

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            Gson gson = new Gson();
            //Leemos Json y guardamos en lyst
            cargarAvatares = gson.fromJson(bufferedReader, Avatar[].class);

            bufferedReader.close();
            fileReader.close();
        } catch (IOException x) {
            Toast.makeText(RankActivity.this, x.toString(),
                    Toast.LENGTH_LONG).show();
        }
        return cargarAvatares;
    }


}