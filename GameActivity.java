package com.example.registro_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    Pregunta[] preguntasQuiz;
    Respuesta[] respuestasQuiz;
    final boolean isrespuesta;
    final boolean istrue = true;
    Respuesta respuestasMostrar;
    int i = 0;
    int chao = 0;
    public static final int Score = 1;
    Pregunta pregunta;
    int puntuacion = 0;
    int respuestasCorrectas;
    public static final String GENERO = "genero";
    public static final String IDIOMA = "idioma";
    //String idioma = "catalan";
    public static final String DIFICULTAT = "dificultat";

    public GameActivity() {
        isrespuesta = false;
    }

    MediaPlayer mpCorrect;
    MediaPlayer mpIncorrect;
    MediaPlayer mpJuego;
    private long pressedTime;
    CountDownTimer countDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        {
            setContentView(R.layout.layout_game);
            //Recogemos de el menú Activity el array de usuarios, el array de avatares y el usuario.

            Intent i = new Intent(GameActivity.this, MainMenuActivity.class);

            Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuarioRecibido");

            Usuario[] usuarioGuardadosJson = (Usuario[]) getIntent().getSerializableExtra("usuariosRecibidos");

            Avatar[] arrayAvatares = (Avatar[]) getIntent().getSerializableExtra("arrayAvatares");


            Intent intent = getIntent();

            // --- Music --- //


            /*mpJuego = MediaPlayer.create(GameActivity.this, R.raw.preguntas);
            mpJuego.seekTo(0);
            mpJuego.start();*/

            // --- TIMER ---- //
            TextView timer;
            TextView nombreusuario = (TextView) findViewById(R.id.user);
            timer = findViewById(R.id.timer);

            nombreusuario.setText("Jugador : " + usuario.getName());

            //Inicializar tiempo de duración.

//            Button devuelta = findViewById(R.id.BtnDevuelta1);
//            devuelta.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent volverAMenuPrincipal = new Intent(GameActivity.this, MainMenuActivity.class);
//                    volverAMenuPrincipal.putExtra("usuarioRecibido", usuario);
//                    volverAMenuPrincipal.putExtra("usuariosRecibidos", usuarioGuardadosJson);
//                    startActivity(volverAMenuPrincipal);
//                    mpJuego.pause();
//
//                    finish();
//
//                }
//            });

            long duration = TimeUnit.MINUTES.toMillis(1);

            //Inicializar contador

            countDown = new CountDownTimer(duration, 1000) {
                @Override
                public void onTick(long l) {
                    //Tick
                    //Convertir milisegundos a minutos y segundos.
                    String sDuration = String.format(Locale.ENGLISH, "%02d: %02d"
                            , TimeUnit.MILLISECONDS.toMinutes(l)
                            , TimeUnit.MILLISECONDS.toSeconds(l),
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                    /*int a = (int) TimeUnit.MILLISECONDS.toMinutes(l);
                    if( a == 50000){
                        MediaPlayer tictic = MediaPlayer.create(GameActivity.this, R.raw.tictac);
                        tictic.seekTo(0);
                        mpJuego.pause();
                        tictic.start();
                    }*/
                    //Setear String en el tex View

                    timer.setText(sDuration);

                }


                @Override
                public void onFinish() {


                    //Cuando finaliza el tiempo.

                    timer.setVisibility(View.GONE);


                    Toast.makeText(getApplicationContext()
                            , "Se acabo el tiempo!", Toast.LENGTH_LONG).show();

                    Intent a = new Intent(GameActivity.this, ScoreActivity.class);
                    a.putExtra("usuariosScore", usuarioGuardadosJson);
                    a.putExtra("arrayAvatares", arrayAvatares);
                    a.putExtra("usuarioScore", usuario);
                    a.putExtra("usuariosScore", usuarioGuardadosJson);
                    a.putExtra("arrayAvatares", arrayAvatares);
                    a.putExtra(ScoreActivity.GENERO_PASADO, IDIOMA);
                    a.putExtra(ScoreActivity.DIFICULTAD_PASADO, DIFICULTAT);
                    a.putExtra(ScoreActivity.TIEMPO_PASADO, "0");
                    countDown.cancel();
                    startActivity(a);
                    finish();
                    mpJuego.pause();

                }




            };

            countDown.start();




            // --- TIMER ---- //

            //--Animacion Background -- //

            RelativeLayout relativeLayout = findViewById(R.id.fifthLayout);

            AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();

            animationDrawable.setEnterFadeDuration(2000);

            animationDrawable.setExitFadeDuration(4000);

            animationDrawable.start();


            /*Recogemos el genero el idioma y la dificultad, que habra seleccionado el usuario para cargar el
            juego personalizado.*/
            String genero = intent.getStringExtra(GENERO);

            String idioma = intent.getStringExtra(IDIOMA);

            String dificultat = intent.getStringExtra(DIFICULTAT);
            /*Una vez recogido el genero el idioma y la dificultad, accedemos a este método que irá actualizando
            las preguntas a medida que son respondidas.
             */
            int cancion = numAleatori();
            cancionASonar(cancion, genero);

            updateQuestion(idioma, genero, dificultat, usuario, usuarioGuardadosJson, arrayAvatares, countDown);
            /*
            TextView que seteara la puntuación, si acierta, suma 1, si falla, suma 0, parseandola a String.
             */
            TextView puntua = findViewById(R.id.Txt_Puntuacion);

            puntua.setText(Integer.toString(puntuacion));
        }
    }

    public void cancionASonar(int num, String gender) {

        if (gender.equals("Rock")) {
            switch (num) {
                case 1:

                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.rock1);

                    break;
                case 2:
                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.rock2);
                    break;
                case 3:
                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.rock3);
                    break;
            }
        }else if(gender.equals("Heavy")){
            switch (num) {
                case 1:

                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.heavy1);

                    break;
                case 2:
                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.heavy2);
                    break;
                case 3:
                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.heavy3);
                    break;
            }
        }else if(gender.equals("Funk")){
            switch (num) {
                case 1:

                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.funk1);

                    break;
                case 2:
                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.funk2);
                    break;
                case 3:
                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.funk3);
                    break;
            }
        }else if(gender.equals("Rap")){
            switch (num) {
                case 1:

                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.rap1);

                    break;
                case 2:
                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.rap2);
                    break;
                case 3:
                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.rap3);
                    break;
            }
        }else if(gender.equals("Pop")){
            switch (num) {
                case 1:

                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.pop1);

                    break;
                case 2:
                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.pop2);
                    break;
                case 3:
                    mpJuego = MediaPlayer.create(GameActivity.this, R.raw.pop3);
                    break;
            }
        }


        mpJuego.seekTo(0);
        mpJuego.start();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);


        return true;

    }

    public int numAleatori() {
        int numeroAleatori = (int) (Math.random() * 3 + 1);
        System.out.println("El número aleatorio entre 1 y 2 es: " + numeroAleatori);
        return numeroAleatori;
    }

    /*
    En este método modificaremos los puntos que obtiene el usuario en cada género.
    (Los puntos se actualizan en el JSON).
     */
    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
            countDown.cancel();
            mpJuego.pause();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();

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
        // Se setean todos los puntos obtenidos del usuario.
        user.setTotal(user.getGender1() + user.getGender2() + user.getGender3() + user.getGender4() + user.getGender5());

        for (Usuario abuscar : usuarioGuardadosJson) {
            if (user.getName().equals(abuscar.getName())) {
                usuarioGuardadosJson[identificadorArray] = user;
            } else {
                identificadorArray++;
            }


        }
        //Seleccionamos el JSON usuarios y sobreescribimos el usuario con los puntos obtenidos.
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
            Toast.makeText(GameActivity.this, x.toString(),
                    Toast.LENGTH_LONG).show();
        }

    }

    //Seteamos los botones que no han sido seleccionados a otro color para diferenciar del acertado.
    public void setearAGris() {
        GridLayout layout = findViewById(R.id.grupoBotones);
        for (int k = 0; k < layout.getChildCount(); k++) {
            View child = layout.getChildAt(k);
            if (child instanceof Button) {
                Button boton = (Button) child;
            }
        }
    }

    //Personalizamos el setOnClick para cuando pase de pregunta.
    public void setOnClickSiguiente(Button NextQuestion, String genero, String idioma,
                                    String dificultat, int chao, int respuestaEsCorrecta, Usuario usuario,
                                    Usuario[] usuarioGuardadosJson, Avatar[] arrayAvatares, CountDownTimer countDown) {
        NextQuestion.setOnClickListener(new View.OnClickListener() {
            int atras = chao;

            @Override
            public void onClick(View v) {
                //De 0 a 9 para cargar 10 preguntas del Array.
                if (i < 9) {
                    //Cuando clique en siguiente pregunta, reiniciamos la configuración de los botones,
                    //y cargamos la siguiente pregunta.
                    Button NextQuestion = findViewById(R.id.respuestasiguiente);
                    setearAGris();
                    i++;
                    updateQuestion(genero, idioma, dificultat, usuario, usuarioGuardadosJson, arrayAvatares, countDown);
                    desbloquearBoton();


                } else {
                    //Volvemos a identificar el texto de tiempo pero ahora tiene un valor diferente al llegar a la diez preguntas
                    TextView timerToPass = findViewById(R.id.timer);
                    TextView score = findViewById(R.id.Txt_Puntuacion);

                    String tiempo = timerToPass.getText().toString();
                    //ejemplo de tiempo 00: 50
                    //usamos esta String para llevarla a ScoreActivity y trabajar con ella
                    String subTiempo = tiempo.substring(4, 6);

                    //Si hemos pasado de 10 preguntas(9 posiciones), pasamos de actividad mandando las
                    // respuestas correctas que ha hecho el usuario, su puntuación, y el array de avatares.
                    Intent segundo = new Intent(GameActivity.this, ScoreActivity.class);
                    segundo.putExtra("respuestaEsCorrecta", respuestaEsCorrecta);
                    segundo.putExtra("usuarioScore", usuario);
                    segundo.putExtra("usuariosScore", usuarioGuardadosJson);
                    segundo.putExtra("arrayAvatares", arrayAvatares);
                    segundo.putExtra("score", String.valueOf(score));
                    segundo.putExtra(ScoreActivity.GENERO_PASADO, idioma);
                    segundo.putExtra(ScoreActivity.DIFICULTAD_PASADO, dificultat);
                    segundo.putExtra(ScoreActivity.TIEMPO_PASADO, subTiempo);
                    //startActivityForResult(segundo, Score);
                    startActivity(segundo);
                    //segundo.putExtra(); para hacer la puntuacion segun el tiempo.
                    mpJuego.pause();
                    countDown.cancel();
                    finish();
                }
            }

        });

    }

    //Aqui configuraremos los botones donde se mostrarán las respuestas.
    public void setOnClick(Button boton1, Respuesta respuestasQuiz, boolean isrespuesta,
                           String genero, String idioma, String dificultat, Usuario usuario,
                           Usuario[] usuarioGuardadosJson, Button NextQuestion,
                           Avatar[] arrayAvatares, CountDownTimer countDown) {
        boton1.setOnClickListener(new View.OnClickListener() {
            int respuestaEsCorrecta;

            @Override
            public void onClick(View v) {

                //Dependiendo de la respuesta que seleccione, se le modificará el botón de una manera o otra,
                //y también se añadirá un punto o no dependiendo de si acierta o no.
                boolean isrespuesta = respuestasQuiz.isTrue();

                if (isrespuesta) {
                    boton1.setBackground(getResources().getDrawable(R.drawable.custom_correctanswer));
                    puntuacion++;
                    respuestasCorrectas++;
                    respuestaEsCorrecta += respuestasCorrectas;
                    modificadorPuntosDePantalla(puntuacion);
                    bloquearBoton();
                    NextQuestion.setClickable(true);
                    if (mpCorrect != null) {
                        mpCorrect.release();
                    }
                    mpCorrect = MediaPlayer.create(GameActivity.this, R.raw.correctding);
                    mpCorrect.seekTo(0);
                    mpCorrect.start();
                } else {
                    boton1.setBackground(getResources().getDrawable(R.drawable.custom_incorrectanswer));
                    respuestaEsCorrecta += respuestasCorrectas;
                    bloquearBoton();
                    NextQuestion.setClickable(true);
                    if (mpIncorrect != null) {
                        mpIncorrect.release();
                    }
                    mpIncorrect = MediaPlayer.create(GameActivity.this, R.raw.correctcbtsound);
                    mpIncorrect.seekTo(0);
                    mpIncorrect.start();
                }

                setOnClickSiguiente(NextQuestion, genero, idioma, dificultat, chao,
                        respuestaEsCorrecta, usuario, usuarioGuardadosJson, arrayAvatares, countDown);
                chao++;
            }
        });
    }

    //Cuando el usuario consiga puntuar, se mostrará en el textView de sus puntos durante el juego.
    public void modificadorPuntosDePantalla(int puntos) {
        TextView puntua = findViewById(R.id.Txt_Puntuacion);
        puntua.setText(Integer.toString(puntos));
    }

    //Cuando el usuario presione un botón de respuesta, se bloquearán todos.
    public void bloquearBoton() {
        GridLayout layout = findViewById(R.id.grupoBotones);
        for (int k = 0; k < layout.getChildCount(); k++) {
            View child = layout.getChildAt(k);
            if (child instanceof Button) {
                Button boton = (Button) child;
                boton.setEnabled(false);
            }
        }
    }

    //Método que resetea la configuración de los botones(respuestas) para cuando avanzemos de pregunta.
    public void desbloquearBoton() {
        GridLayout layout = findViewById(R.id.grupoBotones);
        for (int k = 0; k < layout.getChildCount(); k++) {
            View child = layout.getChildAt(k);
            if (child instanceof Button) {
                Button boton = (Button) child;
                boton.setEnabled(true);
                boton.setBackground(getResources().getDrawable(R.drawable.custom_butonquiz));
            }
        }
    }

    //Cargamos el JSON de preguntas y lo agregamos al array, también sacamos el idioma la dificultad y el genero del propio.
    public Pregunta[] cargarArrayPreguntas(Pregunta[] preguntasQuiz, String idioma, String genero,
                                           String dificultat) {
        String direccionPregunta = getFilesDir().getAbsolutePath();
        String filePath = direccionPregunta + File.separator + "JSON//" + idioma + genero + ".json";
        Pregunta[] preguntasfilt = new Pregunta[10];
        Pregunta pregunta2;
        int control = 0;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Gson gson = new Gson();
            //Leemos Json y guardamos en lyst
            preguntasQuiz = gson.fromJson(bufferedReader, Pregunta[].class);
            for (int i = 0; i < preguntasQuiz.length; i++) {
                //pregunta2 = preguntasQuiz[i];
                if (preguntasQuiz[i].getDificult().equals(dificultat)) {
                    preguntasfilt[control] = preguntasQuiz[i];
                    control++;
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException x) {
            Toast.makeText(GameActivity.this, x.toString(),
                    Toast.LENGTH_LONG).show();
        }
        return preguntasfilt;
    }

    //Cargamos en otro método las respuestas y lo guardamos en un array distinto.(Misma función)
    public Respuesta[] cargarArrayRespuestas(Respuesta[] respuestasQuiz, String idioma,
                                             String genero) {
        String direccionRespuesta = getFilesDir().getAbsolutePath();
        String filePath = direccionRespuesta + File.separator + "JSON//" + idioma + genero + ".json";
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Gson gson = new Gson();
            //Leemos Json y guardamos en lyst
            respuestasQuiz = gson.fromJson(bufferedReader, Respuesta[].class);
            bufferedReader.close();
            fileReader.close();
        } catch (IOException x) {
            Toast.makeText(GameActivity.this, x.toString(),
                    Toast.LENGTH_LONG).show();
        }
        return respuestasQuiz;
    }

    //En este método cargaremos las respuestas del array de preguntas, y las setearemos a los botones.
    public void updateQuestion(String genero, String idioma, String dificultat, Usuario usuario,
                               Usuario[] usuarioGuardadosJson, Avatar[] arrayAvatares, CountDownTimer countDown) {

        Button NextQuestion = findViewById(R.id.respuestasiguiente);
        NextQuestion.setClickable(false);
        preguntasQuiz = cargarArrayPreguntas(preguntasQuiz, genero, idioma, dificultat);
        respuestasQuiz = cargarArrayRespuestas(respuestasQuiz, genero, idioma);
        GridLayout layout = findViewById(R.id.grupoBotones);
        TextView preguntas_quiz = findViewById(R.id.jsonpreguntas);


        preguntas_quiz.setText(preguntasQuiz[i].getPreguntaTexto());

        for (int k = 0; k < layout.getChildCount(); k++) {

            View child = layout.getChildAt(k);
            if (child instanceof Button) {
                respuestasMostrar = preguntasQuiz[i].Respuestas.get(k);
                respuestasQuiz[k] = respuestasMostrar;
                Button boton = (Button) child;
                boton.setText(respuestasQuiz[k].getTexto());
                setOnClick(boton, respuestasQuiz[k], isrespuesta, genero, idioma, dificultat,
                        usuario, usuarioGuardadosJson, NextQuestion, arrayAvatares, countDown);
            }
        }
    }
}