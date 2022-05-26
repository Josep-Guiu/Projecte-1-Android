package com.example.registro_app;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LaunchQuizlight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       setContentView(R.layout.launch_quizlight);


       //Animacion background


    ConstraintLayout constraintLayout = findViewById(R.id.scoreLayout);

    AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();

    animationDrawable.setEnterFadeDuration(2000);

    animationDrawable.setExitFadeDuration(4000);

    animationDrawable.start();

    String dirPath = getFilesDir().getAbsolutePath();


       //Animaciones

        Animation animacion1 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_abajo);

        TextView launchText = findViewById(R.id.launch_title);
        ImageView launchImage = findViewById(R.id.imageLaunch);
        TextView launchCep = findViewById(R.id.cepSoft);
        ImageView logoCep = findViewById(R.id.logocep);
        TextView deadQuaver = findViewById(R.id.DeadQuaver);
        ImageView logoQuaver = findViewById(R.id.logodeadquaver);
        launchText.setAnimation(animacion2);
        launchImage.setAnimation(animacion1);
        launchCep.setAnimation(animacion2);
        logoCep.setAnimation(animacion1);
        deadQuaver.setAnimation(animacion2);
        logoQuaver.setAnimation(animacion1);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
             Intent intent = new Intent(LaunchQuizlight.this,LoginActivity.class);
             startActivity(intent);
             finish();
            }
        },4000);
    }
}
