package com.example.registro_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {
    /*public static final String SCORE = "puntuacion";

    public static final String USUARIO = "Usuario";
    Avatar[] arrayAvatares;*/
    /*public int respuestaEsCorrecta = 0;*/
    public static final String GENERO_PASADO = "tipo";
    public static final String DIFICULTAD_PASADO = "tipo1";
    public static final String TIEMPO_PASADO = "tipo2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizresult_layout);

        int respuestaEsCorrecta = 0;

        Avatar[] arrayAvatares;
        ArrayList<Avatar> arrayAvataresGeneroPasado = new ArrayList<>();

        //--Animacion Background-- //
        LinearLayout linearLayout = findViewById(R.id.scoreLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2000);

        animationDrawable.setExitFadeDuration(4000);

        animationDrawable.start();
        //--Animacion Background-- //

        MediaPlayer mpScore = MediaPlayer.create(ScoreActivity.this, R.raw.score);
        mpScore.seekTo(0);
        mpScore.start();
        mpScore.setLooping(true);

        Intent i = new Intent(ScoreActivity.this, GameActivity.class);

        Intent intent = getIntent();
        String text;
        Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuarioScore");
        text = getIntent().getStringExtra("score");
        Usuario[] usuarioGuardadosJson = (Usuario[]) getIntent().getSerializableExtra("usuariosScore");
        arrayAvatares = (Avatar[]) getIntent().getSerializableExtra("arrayAvatares");
        String generoPasado = intent.getStringExtra(GENERO_PASADO);
        String dificultatPasada = intent.getStringExtra(DIFICULTAD_PASADO);
        String tiempoSobrante = intent.getStringExtra(TIEMPO_PASADO);

        text = String.valueOf(usuario.getTotal());

        TextView resultLabel = (TextView) findViewById(R.id.resultLabel);
        TextView totalScore = (TextView) findViewById(R.id.totalScore);
        ImageView imgAvatar = findViewById(R.id.avatarImg);

        Button botonReturn = findViewById(R.id.botonReturn);

        respuestaEsCorrecta = intent.getExtras().getInt("respuestaEsCorrecta");

        resultLabel.setText("Has acertado " + String.valueOf(respuestaEsCorrecta) + "/10");

        int puntosObtenidosEnPartida = respuestaEsCorrecta;
        double tiempo = tiempoAInt(tiempoSobrante);

        int puntuacionParaGuardar = crearPuntos(respuestaEsCorrecta, dificultatPasada, tiempo);

        modificacionPuntosUsuario(usuario, /*respuestaEsCorrecta*/ puntuacionParaGuardar, generoPasado, usuarioGuardadosJson);






        for (Avatar item : arrayAvatares) {
            if (generoPasado.equals(item.getGender())) {
                arrayAvataresGeneroPasado.add(item);
            }

        }

        int buscador = 0;
        int indice = 0;
        for (Avatar itemACargar : arrayAvataresGeneroPasado) {
            if (/*puntosObtenidosEnPartida*/puntuacionParaGuardar <= itemACargar.getPuntuacionMax()
                    && /*puntosObtenidosEnPartida*/puntuacionParaGuardar >= itemACargar.getPuntuacionMin()) {
                indice = buscador;
            } else {
                buscador++;
            }
        }

        //porGeneros(generoPasado, indice, imgAvatar);
        ponerAvatares(puntuacionParaGuardar, generoPasado, arrayAvatares, imgAvatar);
        totalScore.setText("Puntuación obtenida: "+String.valueOf(puntuacionParaGuardar));

        botonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volverAMenuPrincipal = new Intent(ScoreActivity.this, MainMenuActivity.class);
                //volverAMenuPrincipal.putExtra(USUARIO,text);
                volverAMenuPrincipal.putExtra("usuarioRecibido", usuario);
                volverAMenuPrincipal.putExtra("usuariosRecibidos", usuarioGuardadosJson);
                setResult(RESULT_OK);
                startActivity(volverAMenuPrincipal);
                finish();

            }
        });
    }

    public int crearPuntos(int respuestaEsCorrecta, String dificultatPasada, double tiempo) {
        int puntos = respuestaEsCorrecta;
        switch (dificultatPasada) {
            case "Facil":
                puntos = (int) ((puntos * 5) + tiempo);
                break;
            case "Medio":
                tiempo = tiempo * 2;
                puntos = (int) ((puntos * 10) + tiempo);
                break;
            case "Dificil":
                tiempo = tiempo * 3;
                puntos = (int) ((puntos * 15) + tiempo);
                break;
        }
        return puntos;
    }

    public double tiempoAInt(String tiempo){
        return Float.parseFloat(tiempo) * 0.1;
    }

    public void modificacionPuntosUsuario(Usuario user, int puntuacionObtenida, String genero, Usuario[] usuarioGuardadosJson) {
        int identificadorArray = 0;
        switch (genero) {
            case "Rock":
                if (user.getGender1() < puntuacionObtenida) {
                    user.setGender1(puntuacionObtenida);
                }
                break;
            case "Heavy":
                if (user.getGender2() < puntuacionObtenida) {
                    user.setGender2(puntuacionObtenida);
                }
                break;
            case "Rap":
                if (user.getGender3() < puntuacionObtenida) {
                    user.setGender3(puntuacionObtenida);
                }
                break;
            case "Funk":
                if (user.getGender4() < puntuacionObtenida) {
                    user.setGender4(puntuacionObtenida);
                }
                break;
            case "Pop":
                if (user.getGender5() < puntuacionObtenida) {
                    user.setGender5(puntuacionObtenida);
                }
                break;
        }
        user.setTotal(user.getGender1() + user.getGender2() + user.getGender3() + user.getGender4() + user.getGender5());

        for (Usuario abuscar : usuarioGuardadosJson) {
            if (user.getName().equals(abuscar.getName())) {
                usuarioGuardadosJson[identificadorArray] = user;
            } else {
                identificadorArray++;
            }

        }
        //Guardar en JSON
        String direccionArchivo2 = getFilesDir().getAbsolutePath();
        String filePath2 = direccionArchivo2 + File.separator + "JSON//Usuarios.json";
        try {
            FileWriter fw = new FileWriter(filePath2);
            BufferedWriter bw = new BufferedWriter(fw);

            Gson gson = new Gson();
            String userStringGuardado = gson.toJson(usuarioGuardadosJson);
            bw.write(userStringGuardado);

            bw.close();
            fw.close();
        } catch (IOException x) {
            Toast.makeText(ScoreActivity.this, x.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }


    public void ponerAvatares(int puntosParaGuardar, String generoPasado, Avatar[] avatares, ImageView img) {
        String nombre = null;
        for(int i=0; i<avatares.length;i++){

            if(puntosParaGuardar > avatares[i].getPuntuacionMin() && puntosParaGuardar < avatares[i].getPuntuacionMax() &&
                    avatares[i].getGender().equals(generoPasado)){
                nombre =  avatares[i].getNombre();
            }
        }
        String path = getFilesDir().getAbsolutePath();
        String pathImage = path + File.separator + "JSON/Resources/" + nombre + ".png";
        Bitmap bmp = BitmapFactory.decodeFile(pathImage);

        img.setImageBitmap(bmp);

    }

}