package com.example.registro_app;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class MainMenuActivity extends AppCompatActivity {


    String idiomaJuego = "";


    MediaPlayer mpaCorrect;

    Usuario usuariosToolbar;
    Avatar[] avatarsToolbar;
    Usuario[] usuariosJson;
    private long pressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



    /*
    Al acceder a la pantalla principal del Juego cargaremos los avatares que se le setearán al usuario
    dependiendo de la puntuación que realize en el juego.
     */
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_mainscreen);

        int cancion = numAleatori();
          cancionASonar(cancion);
        Avatar[] arrayAvatares = new Avatar[0];

        arrayAvatares = cargarArrayAvatares(arrayAvatares);

        Toolbar toolbar = findViewById(R.id.ToolBar);

        setSupportActionBar(toolbar);

        TextView puntuacionTotal = findViewById(R.id.totalScore);

        //--Animacion Background-- //
        RelativeLayout relativeLayout = findViewById(R.id.mainscreenLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2000);

        animationDrawable.setExitFadeDuration(4000);

        animationDrawable.start();

        //--Animacion Background-- //


        /*
        Recogemos en un Array de usuarios, el Array que hemos realizado en la Actividad anterior
        (el log-in) para comparar los usuarios, aprovechamos ese mismo Array para así poder pasarlo
        al ránking desde esta actividad y poder mostrar la puntuacion de todos los usuarios.

        También recogemos el usuario para mostrar quién esta jugando actualmente( en esta sesión ).

         */
        Usuario[] usuarioGuardadosJson = (Usuario[]) getIntent().getSerializableExtra("usuariosRecibidos");

         Usuario usuarioActual = (Usuario) getIntent().getSerializableExtra("usuarioRecibido");
        usuariosToolbar = usuarioActual;
        avatarsToolbar = arrayAvatares;
        usuariosJson = usuarioGuardadosJson;
        /*
        Como hemos recogido el usuario que se logea, seteamos su nombre y los puntos que lleva.
        (Nota: La puntuación mostrada es la total,es decir, no la de la sesión registrada,
        si no la total que almacenaremos en el JSON mas adelante.)
         */
        TextView nombreDelUsuario = findViewById(R.id.Nom_Usuari);

        TextView puntosTotales = findViewById(R.id.Puntos_Totales);

        puntosTotales.setText(Integer.toString(usuarioActual.getTotal()));

        final Button dificultadeasy = findViewById(R.id.dificultadeasy);

        final Button dificultadmid = findViewById(R.id.dificultadmid);

        final Button dificultadhard = findViewById(R.id.dificultadhard);

        final TextView nombre_usuario = findViewById(R.id.Nom_Usuari);

        final ImageView avatarGeneral = findViewById(R.id.userAvatar);

        ImageButton imagen1 = findViewById(R.id.ImgFunk);

        ImageButton imagen2 = findViewById(R.id.ImgHeavy);

        ImageButton imagen3 = findViewById(R.id.ImgPop);

        ImageButton imagen4 = findViewById(R.id.ImgRap);

        ImageButton imagen5 = findViewById(R.id.ImgRock);

        final String dificultadseleccionada;

        //Se setea el nombre del usuario al TextView.
        nombre_usuario.setText(usuarioActual.getName());

        ponerAvatares(usuarioActual, arrayAvatares, avatarGeneral);

        imagen1.setClickable(true);

        imagen1.setEnabled(true);

        imagen2.setClickable(true);

        imagen2.setEnabled(true);

        imagen3.setClickable(true);

        imagen3.setEnabled(true);


        imagen4.setClickable(true);

        imagen5.setEnabled(true);

        imagen5.setClickable(true);
        imagen5.setEnabled(true);


        dificultadeasy.setEnabled(false);
        dificultadmid.setEnabled(false);
        dificultadhard.setEnabled(false);

        dificultadeasy.setClickable(false);
        dificultadmid.setClickable(false);
        dificultadhard.setClickable(false);


        clicarImagen(imagen1, imagen1, imagen2, imagen3, imagen4, imagen5, usuarioActual, usuarioGuardadosJson, arrayAvatares);
        clicarImagen(imagen2, imagen1, imagen2, imagen3, imagen4, imagen5, usuarioActual, usuarioGuardadosJson, arrayAvatares);
        clicarImagen(imagen3, imagen1, imagen2, imagen3, imagen4, imagen5, usuarioActual, usuarioGuardadosJson, arrayAvatares);
        clicarImagen(imagen4, imagen1, imagen2, imagen3, imagen4, imagen5, usuarioActual, usuarioGuardadosJson, arrayAvatares);
        clicarImagen(imagen5, imagen1, imagen2, imagen3, imagen4, imagen5, usuarioActual, usuarioGuardadosJson, arrayAvatares);













//Cuando selecciones el modo de juego (FACIL) los demás modos de juego no serán clickables, ya que accederemos directamente al juego.
        dificultadeasy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String dificultad = dificultadeasy.getText().toString().toLowerCase();

                dificultadmid.setClickable(false);
                dificultadhard.setClickable(false);


                dificultadmid.setBackgroundColor(Color.GRAY);
                dificultadhard.setBackgroundColor(Color.GRAY);
            }
        });



//Cuando selecciones el modo de juego (MEDIO) los demás modos de juego no serán clickables, ya que accederemos directamente al juego.
        dificultadmid.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String dificultad = dificultadmid.getText().toString().toLowerCase();

                dificultadeasy.setClickable(false);
                dificultadhard.setClickable(false);


                dificultadeasy.setBackgroundColor(Color.GRAY);
                dificultadhard.setBackgroundColor(Color.GRAY);

            }
        });
//Cuando selecciones el modo de juego (DIFICIL) los demás modos de juego no serán clickables, ya que accederemos directamente al juego.
        dificultadhard.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String dificultad = dificultadhard.getText().toString().toLowerCase();
                System.out.println(dificultad);

                dificultadeasy.setClickable(false);
                dificultadmid.setClickable(false);


                dificultadeasy.setBackgroundColor(Color.GRAY);
                dificultadmid.setBackgroundColor(Color.GRAY);

            }
        });


        /*
        Cuando el usuario clicke en el boton Ranking, accederemos a otra actividad, donde mandaremos el
        array de usuarios sacados del JSON.
         */
        Avatar[] finalArrayAvatares = arrayAvatares;


        /*
        Cuando clicke en una bandera de idioma, recogemos un JSON dependiendo del idioma,
        es decir, si quiere jugar en inglés, por ejemplo, si clica en la bandera el juego cargara en
        inglés, 3 idiomas(Ingles,Castellano,Catalan)
         */

    }


    /*
    Personalizamos los botones dificultad, para acceder al juego
    con el género seleccionado, el idioma y la dificultad.
     */
    public void entrarAlJuego(Button dificultad, String genero, String dificultat,
                              Usuario usuarioJugando, Usuario[] usuarioGuardadosJson,
                              Avatar[] arrayAvatares) {
        dificultad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                Pasamos por intent, la dificultad, el genero, el idioma en el que juega
                y también el usuario, el array de usuarios y el array de avatares.
                 */
                Intent i = new Intent(MainMenuActivity.this, GameActivity.class);
                i.putExtra(GameActivity.DIFICULTAT, dificultat);
                i.putExtra(GameActivity.GENERO, genero);
                i.putExtra(GameActivity.IDIOMA, idiomaJuego);

                System.out.println(idiomaJuego);
                i.putExtra("usuarioRecibido", usuarioJugando);
                i.putExtra("usuariosRecibidos", usuarioGuardadosJson);
                i.putExtra("arrayAvatares", arrayAvatares);
                startActivity(i);
                finish();
            }
        });
    }

    /*
    Personalizamos las imágenes para notificar al usuario el modo de juego que ha seleccionado.
     */
    public void clicarImagen(ImageButton imagenm, ImageButton imagen1, ImageButton imagen2,
                             ImageButton imagen3,
                             ImageButton imagen4, ImageButton imagen5, Usuario usuarioJugando,
                             Usuario[] usuarioGuardadosJson, Avatar[] arrayAvatares) {

        final Button dificultadeasy = findViewById(R.id.dificultadeasy);
        final Button dificultadmid = findViewById(R.id.dificultadmid);
        final Button dificultadhard = findViewById(R.id.dificultadhard);
        imagenm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String genero;
                int i = 0;

                if (imagenm.equals(imagen1)) {
                    imagen2.setEnabled(true);
                    imagen3.setEnabled(true);
                    imagen4.setEnabled(true);
                    imagen5.setEnabled(true);
                    i = 1;
                    imagen1.setBackground(getResources().getDrawable(R.drawable.circularbackground_sel));

                    imagen2.setBackgroundDrawable(null);
                    imagen3.setBackgroundDrawable(null);
                    imagen4.setBackgroundDrawable(null);
                    imagen5.setBackgroundDrawable(null);

                    dificultadeasy.setVisibility(View.VISIBLE);
                    dificultadmid.setVisibility(View.VISIBLE);
                    dificultadhard.setVisibility(View.VISIBLE);

                    dificultadeasy.setBackground(getResources().getDrawable(R.drawable.spriteasy));
                    dificultadmid.setBackground(getResources().getDrawable(R.drawable.spritemid));
                    dificultadhard.setBackground(getResources().getDrawable(R.drawable.spritehard));

                } else if (imagenm.equals(imagen2)) {
                    imagen1.setEnabled(true);
                    imagen3.setEnabled(true);
                    imagen4.setEnabled(true);
                    imagen5.setEnabled(true);
                    i = 2;
                    imagen2.setBackground(getResources().getDrawable(R.drawable.circularbackground_sel));
                    imagen4.setBackgroundDrawable(null);
                    imagen3.setBackgroundDrawable(null);
                    imagen1.setBackgroundDrawable(null);
                    imagen5.setBackgroundDrawable(null);
                    dificultadeasy.setVisibility(View.VISIBLE);
                    dificultadmid.setVisibility(View.VISIBLE);
                    dificultadhard.setVisibility(View.VISIBLE);
                    dificultadeasy.setBackground(getResources().getDrawable(R.drawable.spriteasy));
                    dificultadmid.setBackground(getResources().getDrawable(R.drawable.spritemid));
                    dificultadhard.setBackground(getResources().getDrawable(R.drawable.spritehard));

                } else if (imagenm.equals(imagen3)) {
                    imagen1.setEnabled(true);
                    imagen2.setEnabled(true);
                    imagen4.setEnabled(true);
                    imagen5.setEnabled(true);
                    i = 3;
                    imagen3.setBackground(getResources().getDrawable(R.drawable.circularbackground_sel));
                    imagen1.setBackgroundDrawable(null);
                    imagen2.setBackgroundDrawable(null);
                    imagen4.setBackgroundDrawable(null);
                    imagen5.setBackgroundDrawable(null);
                    dificultadeasy.setVisibility(View.VISIBLE);
                    dificultadmid.setVisibility(View.VISIBLE);
                    dificultadhard.setVisibility(View.VISIBLE);

                    dificultadeasy.setBackground(getResources().getDrawable(R.drawable.spriteasy));
                    dificultadmid.setBackground(getResources().getDrawable(R.drawable.spritemid));
                    dificultadhard.setBackground(getResources().getDrawable(R.drawable.spritehard));
                } else if (imagenm.equals(imagen4)) {

                    imagen1.setBackgroundDrawable(null);
                    imagen2.setBackgroundDrawable(null);
                    imagen3.setBackgroundDrawable(null);
                    imagen5.setBackgroundDrawable(null);

                    imagen1.setEnabled(true);
                    imagen2.setEnabled(true);
                    imagen3.setEnabled(true);
                    imagen5.setEnabled(true);
                    i = 4;
                    imagen4.setBackground(getResources().getDrawable(R.drawable.circularbackground_sel));

                    imagen1.setBackgroundDrawable(null);
                    imagen2.setBackgroundDrawable(null);
                    imagen3.setBackgroundDrawable(null);
                    imagen5.setBackgroundDrawable(null);

                    dificultadeasy.setVisibility(View.VISIBLE);
                    dificultadmid.setVisibility(View.VISIBLE);
                    dificultadhard.setVisibility(View.VISIBLE);
                    dificultadeasy.setBackground(getResources().getDrawable(R.drawable.spriteasy));
                    dificultadmid.setBackground(getResources().getDrawable(R.drawable.spritemid));
                    dificultadhard.setBackground(getResources().getDrawable(R.drawable.spritehard));

                } else if (imagenm.equals(imagen5)) {
                    imagen1.setEnabled(true);
                    imagen2.setEnabled(true);
                    imagen3.setEnabled(true);
                    imagen4.setEnabled(true);
                    i = 5;
                    imagen5.setBackground(getResources().getDrawable(R.drawable.circularbackground_sel));

                    imagen1.setBackgroundDrawable(null);
                    imagen2.setBackgroundDrawable(null);
                    imagen3.setBackgroundDrawable(null);
                    imagen4.setBackgroundDrawable(null);

                    dificultadeasy.setVisibility(View.VISIBLE);
                    dificultadmid.setVisibility(View.VISIBLE);
                    dificultadhard.setVisibility(View.VISIBLE);
                    dificultadeasy.setBackground(getResources().getDrawable(R.drawable.spriteasy));
                    dificultadmid.setBackground(getResources().getDrawable(R.drawable.spritemid));
                    dificultadhard.setBackground(getResources().getDrawable(R.drawable.spritehard));
                }

                String dificultad;

                if (i > 0) {
                    genero = menuStrings(i);
                    dificultadeasy.setEnabled(true);
                    dificultadmid.setEnabled(true);
                    dificultadhard.setEnabled(true);

                    dificultadeasy.setClickable(true);
                    dificultadmid.setClickable(true);
                    dificultadhard.setClickable(true);


                    if (dificultadhard.isClickable() == true) {
                        dificultad = "Dificil";
                        entrarAlJuego(dificultadhard, genero, dificultad, usuarioJugando, usuarioGuardadosJson, arrayAvatares);
                        mpaCorrect.pause();
                    }

                    if (dificultadeasy.isClickable() == true) {

                        dificultad = "Facil";
                        entrarAlJuego(dificultadeasy, genero, dificultad, usuarioJugando, usuarioGuardadosJson, arrayAvatares);
                        mpaCorrect.pause();
                    }
                    if (dificultadmid.isClickable() == true) {

                        dificultad = "Medio";
                        entrarAlJuego(dificultadmid, genero, dificultad, usuarioJugando, usuarioGuardadosJson, arrayAvatares);
                        mpaCorrect.pause();
                    }


                } else {
                    imagen1.setClickable(true);
                    imagen1.setEnabled(true);
                    imagen2.setClickable(true);
                    imagen2.setEnabled(true);
                    imagen3.setClickable(true);
                    imagen3.setEnabled(true);
                }

            }
        });

    }
    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
            mpaCorrect.pause();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    public String menuStrings(int x) {
        String string2 = null;
        //String z = (String.valueOf(x));
        //z.substring(9);
        //Integer c = (Integer.valueOf(z.substring(9)));

        switch (x) {
            case 1:
                string2 = "Funk";
                break;
            case 2:
                string2 = "Heavy";
                break;
            case 3:
                string2 = "Pop";
                break;
            case 4:
                string2 = "Rap";
                break;
            case 5:
                string2 = "Rock";
                break;

        }
        return string2;
    }

    /*
    no, se almacenan y se muestran en loop
    Cargamos el array de avatares para mostrar el que le pertenece dependiendo de la puntuación.
     */
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
            Toast.makeText(MainMenuActivity.this, x.toString(),
                    Toast.LENGTH_LONG).show();
        }
        return cargarAvatares;
    }

    public void ponerAvatares(Usuario usuario, Avatar[] avatares, ImageView img) {


        for (int i = 0; i < avatares.length; i++) {

            if (usuario.getTotal() >= avatares[i].getPuntuacionMin() && usuario.getTotal() <= avatares[i].getPuntuacionMax() &&
                    avatares[i].getGender().equals("General")) {
                String nombre = avatares[i].getNombre();

                String path = getFilesDir().getAbsolutePath();
                String pathImage = path + File.separator + "JSON/Resources/" + nombre + ".png";
                Bitmap bmp = BitmapFactory.decodeFile(pathImage);

                img.setImageBitmap(bmp);
            }

        }


    }
    public void cancionASonar(int num){
        switch (num){
            case 1:
                mpaCorrect = MediaPlayer.create(MainMenuActivity.this, R.raw.rank);
                break;
            case 2:
                mpaCorrect = MediaPlayer.create(MainMenuActivity.this, R.raw.holamundo);
                break;
            case 3:
                mpaCorrect = MediaPlayer.create(MainMenuActivity.this, R.raw.rock);
                break;
            case 4:
                mpaCorrect = MediaPlayer.create(MainMenuActivity.this, R.raw.funk);
                break;
        }
        mpaCorrect.seekTo(0);
        mpaCorrect.start();
        mpaCorrect.setLooping(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);



        return true;

    }
    public int numAleatori () {
        int numeroAleatori = (int) (Math.random()*4+1);
        System.out.println("El número aleatorio entre 1 y 2 es: " + numeroAleatori);
        return numeroAleatori;
    }





    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Configuration config = null;
        Resources res = getResources();
        config = new Configuration(res.getConfiguration());

        if (id == R.id.item1) {
            String languageToLoad  = "es";
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config1 = new Configuration();
            config.locale = locale;
            this.getBaseContext().getResources().updateConfiguration(config1,getBaseContext().getResources().getDisplayMetrics());
            idiomaJuego = "";



            restartActivity();





        }

        if (id == R.id.item2) {
            String languageToLoad2  = "en";
            Locale locale2 = new Locale(languageToLoad2);
            Locale.setDefault(locale2);
            Configuration config2 = new Configuration();
            config.locale = locale2;
            this.getBaseContext().getResources().updateConfiguration(config2,getBaseContext().getResources().getDisplayMetrics());
            idiomaJuego = "ingles";
            restartActivity();




        }

        if (id == R.id.item3) {
            String languageToLoad3  = "ca";
            Locale locale3 = new Locale(languageToLoad3);
            Locale.setDefault(locale3);
            Configuration config3 = new Configuration();
            config.locale = locale3;
            this.getBaseContext().getResources().updateConfiguration(config3,getBaseContext().getResources().getDisplayMetrics());
            idiomaJuego = "catalan";
            restartActivity();




        }
        if (id == R.id.item4) {
            Intent intent2 = new Intent(MainMenuActivity.this, RankActivity.class);
            intent2.putExtra("usuariosParaRank", usuariosJson);
            intent2.putExtra("arrayAvatares", avatarsToolbar);
            intent2.putExtra("usuarioParaRank", usuariosToolbar);
            //intent.putExtra("usuarioParaRank", usuarioActual);
            startActivity(intent2);

            res.updateConfiguration(config, res.getDisplayMetrics());

        }

        if (id == R.id.item5)
        {
            Intent intent = new Intent(MainMenuActivity.this, ProfileActivity.class);
            intent.putExtra("usuarioRecibido", usuariosToolbar);
            intent.putExtra("usuariosRecibidos", usuariosJson);
            startActivity(intent);
            res.updateConfiguration(config, res.getDisplayMetrics());
            mpaCorrect.pause();

        }

        return super.onOptionsItemSelected(item);
    }

    private void restartActivity()
    {

        Intent intent = getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}