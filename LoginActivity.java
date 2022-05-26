package com.example.registro_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    /*ImageButton registeractivity;

    Button log_btn;

    Usuario[] usuarioGuardadosJson;*/

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        ImageButton registeractivity;
        Button log_btn;

        Toolbar toolbar = findViewById(R.id.ToolBar);

        setSupportActionBar(toolbar);

        final EditText log_user = findViewById(R.id.login_email);
        final EditText log_password = findViewById(R.id.login_password);
        log_btn = (Button) findViewById(R.id.btn_login);
        registeractivity = findViewById(R.id.reg_activity);

        //--Animacion Background-- //
        RelativeLayout relativeLayout = findViewById(R.id.loginLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2000);

        animationDrawable.setExitFadeDuration(4000);

        animationDrawable.start();


        //--Animacion Background-- //

        Button btnInfo = findViewById(R.id.Info);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(LoginActivity.this, R.style.BootomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.presentacion_layout, (LinearLayout)findViewById(R.id.Presentacion));
                bottomSheetView.findViewById(R.id.BotonChao).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(LoginActivity.this, "Entendido", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        registeractivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

                finish();
            }
        });

//Configuramos el LOG-IN a la aplicación comparando si el usuario existe en el JSON,
        //si es así, accede a la aplicación.
        log_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Usuario[] usuarioGuardadosJson = new Usuario[0];
                Usuario usuarioActual;

                usuarioGuardadosJson = cargarArrayUsuarios(usuarioGuardadosJson);
                /*
                Cogemos el nombre y la password que introduce el usuario y las comparamos:

                -Recorremos el Array de Usuarios con un For Each.

                -Verificación comparando el usuario Actual(el que introduce el usuario)
                con el que está en el Array.

                -Idem para la password.

                -Si el usuario falla en el Log-in se le muestra un mensaje de que :
                Las credenciales son incorrectas.
                El usuario no se ha encontrado.
                El usuario es incorrecto.


                Si el login es correcto, avanzaremos a la pantalla principal del juego.

                 */
                String nombreObtenido = log_user.getText().toString();

                String passObtenido = log_password.getText().toString();

                String comparativaName;

                String comparativaPass;

                for (Usuario userCompara: usuarioGuardadosJson)
                {
                    comparativaName = userCompara.getName();
                    if(nombreObtenido.equals(comparativaName))
                    {
                        comparativaName = userCompara.getName();
                       comparativaPass = userCompara.getPassword();

                        if (comparativaName.equals(/*log_user.getText().toString()*/nombreObtenido))
                        {
                            if(comparativaName.equals(/*log_user.getText().toString()*/nombreObtenido)
                                    && comparativaPass.equals(/*log_password.getText().toString()*/passObtenido))
                            {
                                usuarioActual = userCompara;
                                Toast.makeText(LoginActivity.this, "Iniciando sesión...",
                                        Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainMenuActivity.class);
                                i.putExtra("usuarioRecibido", usuarioActual);
                                i.putExtra("usuariosRecibidos", usuarioGuardadosJson);
                                startActivity(i);

                            }else
                                {
                                Toast.makeText(LoginActivity.this, "Credenciales incorrectas",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }else
                            {
                            Toast.makeText(LoginActivity.this, "Usuario no encontrado",
                                    Toast.LENGTH_SHORT).show();

                    }
                    }/*else
                        {
                        Toast.makeText(LoginActivity.this, "Usuario incorrecto",
                                Toast.LENGTH_SHORT).show();
                    }
                    */

                }

            }
/*Cargamos el Array de Usuarios que habremos generado en el Registro.

Se almacenan los usuarios en un Array de Usuarios.

 */
            public Usuario [] cargarArrayUsuarios (Usuario[] usuarioGuardadosJson)
            {
                String direccionArchivo = getFilesDir().getAbsolutePath();

                String filePath = direccionArchivo + File.separator + "JSON//Usuarios.json";

                try
                {
                    FileReader fileReader = new FileReader(filePath);

                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    Gson gson = new Gson();

                    //Leemos Json y guardamos en lyst
                    usuarioGuardadosJson = gson.fromJson(bufferedReader, Usuario[].class);
                    bufferedReader.close();
                    fileReader.close();
                } catch (IOException x)
                {
                    Toast.makeText(LoginActivity.this, x.toString(),
                            Toast.LENGTH_LONG).show();
                }
                return usuarioGuardadosJson;
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.item1);
        MenuItem item1 = menu.findItem(R.id.item2);
        MenuItem item2 = menu.findItem(R.id.item3);





        return super.onCreateOptionsMenu(menu);

    }


    private void setAppLocale(String localeCode) {
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(new Locale(localeCode.toLowerCase()));
        resources.updateConfiguration(configuration, displayMetrics);
        configuration.locale = new Locale(localeCode.toLowerCase());
        resources.updateConfiguration(configuration, displayMetrics);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Configuration config = null;
        Resources res = getResources();
        config = new Configuration(res.getConfiguration());
        switch (item.getItemId()) {
            case R.id.item1:

                String languageToLoad  = "es";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config1 = new Configuration();
                config.locale = locale;
                this.getBaseContext().getResources().updateConfiguration(config1,getBaseContext().getResources().getDisplayMetrics());

                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.item2:

                String languageToLoad2  = "en";
                Locale locale2 = new Locale(languageToLoad2);
                Locale.setDefault(locale2);
                Configuration config2 = new Configuration();
                config.locale = locale2;
                this.getBaseContext().getResources().updateConfiguration(config2,getBaseContext().getResources().getDisplayMetrics());

                Intent intent2 = new Intent(LoginActivity.this, LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);

                break;
            case R.id.item3:
                String languageToLoad3  = "ca";
                Locale locale3 = new Locale(languageToLoad3);
                Locale.setDefault(locale3);
                Configuration config3 = new Configuration();
                config.locale = locale3;
                this.getBaseContext().getResources().updateConfiguration(config3,getBaseContext().getResources().getDisplayMetrics());

                Intent intent3 = new Intent(LoginActivity.this, LoginActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent3);
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    public void cambiarIdioma()
    {
      Locale localizacion = new Locale("es_ES");
      Locale.setDefault(localizacion);
      Configuration config = new Configuration();

      config.locale = localizacion;

      this.getResources().updateConfiguration(config,null);
    }
    public void cambiarIdiomaEN()
    {
        Locale localizacion = new Locale("en","US");
        Locale.setDefault(localizacion);
        Configuration config = new Configuration();

        config.locale = localizacion;

        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
    }

    private void restartActivity()
    {

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){

            configuration.setLocale(locale);
        }else
        {
            configuration.locale = locale;
        }


    }




}


