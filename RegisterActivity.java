package com.example.registro_app;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {
    //List Usuarios = Collections.synchronizedList(new ArrayList());



    /*ArrayList<Usuario> usuariosExistentes = new ArrayList<>();
    Usuario[] usuarioGuardados;
    Usuario usuario;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Usuario> usuariosExistentes = new ArrayList<>();
        Usuario[] usuarioGuardados = new Usuario[0];
        final Usuario usuario;

        setContentView(R.layout.register_layout);

        TextView visualText = (TextView) findViewById(R.id.Marquee);
        visualText.setSelected(true);
        Animation animacion = AnimationUtils.loadAnimation(this, R.anim.move_text);
        visualText.startAnimation(animacion);


        final Button reg_user = findViewById(R.id.btn_register);
        final ImageButton log_activity = findViewById(R.id.log_btn);
        final EditText register_name = findViewById(R.id.register_name);
        final EditText register_email = findViewById(R.id.register_email);
        final EditText register_age = findViewById(R.id.register_date);
        final EditText register_password = findViewById(R.id.register_password);
        final EditText register_repassword = findViewById(R.id.register_repassword);

        // ----- //
        /*final String user_name = register_name.getText().toString();
        final String user_email = register_email.getText().toString();
        final String user_age = register_age.getText().toString();
        final String user_password = register_password.getText().toString();
        final String user_repassword = register_repassword.getText().toString();*/

        //--Animacion Background-- //
        LinearLayout relativeLayout = findViewById(R.id.registerLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2000);

        animationDrawable.setExitFadeDuration(4000);

        animationDrawable.start();
        //--Animacion Background-- //
        log_activity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);

                startActivity(i);
                finish();
            }
        });

        usuarioGuardados = cargarArrayUsuarios(usuarioGuardados);
        for (int i = 0; i < usuarioGuardados.length; i++) {
            usuariosExistentes.add(usuarioGuardados[i]);
        }

//
        //Función para verificar que introduce los datos en el registro. //
        Usuario[] finalUsuarioGuardados = usuarioGuardados;
        reg_user.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                boolean controlpass = false;
                boolean control = true;
                boolean isSameUserName = false;
                boolean isMailOk = true;
                boolean isAgeOk = true;
                boolean isPassOk = true;


                if (isEmpty(register_name)) {
                    register_name.setError("Name is empty.");
                } else if (isEmpty(register_email)) {
                    register_email.setError("Email is empty");
                } else if (isEmpty(register_age)) {
                    register_age.setError("Age is empty");
                } else if (isEmpty(register_password)) {
                    register_password.setError("Password is empty.");
                } else if (isEmpty(register_repassword)) {
                    register_repassword.setError("Re password is empty");
                } else {


                    control = true;
                    if (register_password.getText().toString().equals(register_repassword.getText().toString())) {
                        controlpass = true;
                        isMailOk = controlDeEmail(register_email.getText().toString());
                        isAgeOk = controlDeEdad(register_age.getText().toString());
                        isPassOk = controlDeContrasenia(register_password.getText().toString());
                        isSameUserName = controlRepetirUsuarios(register_name.getText().toString(), finalUsuarioGuardados);
                        if (!isSameUserName && control && isMailOk && isAgeOk && isPassOk ) {
                            //usuario = new Usuario(user_name, user_age, user_password, user_email);
                            Usuario usuario = new Usuario(register_name.getText().toString(),
                                    register_email.getText().toString(),
                                    register_age.getText().toString(),
                                    register_password.getText().toString());
                            Toast.makeText(RegisterActivity.this, "Usuario generado",
                                    Toast.LENGTH_SHORT).show();

                            usuariosExistentes.add(usuario);
                            guardarJSON(usuariosExistentes, usuario);

                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();

                        } else if (isSameUserName && !control) {
                            Toast.makeText(RegisterActivity.this, "Usuario no generado",
                                    Toast.LENGTH_SHORT).show();

                        }
                          }else{
                        Toast.makeText(RegisterActivity.this, "No es la misma contraseña",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }


            public void guardarJSON(ArrayList<Usuario> usuariosExistentes, Usuario usuario) {
                String direccionArchivo2 = getFilesDir().getAbsolutePath();
                String filePath2 = direccionArchivo2 + File.separator + "JSON//Usuarios.json";

                //usuarioGuardados= cargarArrayUsuarios(usuarioGuardados);//DE PRUEBAS
                //usuariosExistentes.add(usuario);


                try {
                    FileWriter fw = new FileWriter(filePath2);
                    BufferedWriter bw = new BufferedWriter(fw);

                    Gson gson = new Gson();
                    //JSONArray users = new JSONArray();
                    String userString = gson.toJson(usuariosExistentes);
                   /*for (Usuario item: usuariosExistentes) {
                       String userString = gson.toJson(item);
                       users.put(userString);

                       //bw.write(userString);
                       //bw.write(String.valueOf(users));

                   }*/
                    bw.write(userString);
                    //bw.write(String.valueOf(users));
                    bw.close();
                    fw.close();
                } catch (IOException x) {
                    Toast.makeText(RegisterActivity.this, x.toString(),
                            Toast.LENGTH_LONG).show();
                }
            }





            public boolean isSame(String pass1, String pass2, boolean controlpass) {
                if (!pass1.equals(pass2)) {
                    controlpass = true;
                } else {
                    controlpass = false;
                }

                return controlpass;
            }

            public boolean controladorVacio(String varibleComprobar, boolean controlador) {
                if (varibleComprobar.equals("")) {
                    controlador = false;
                    Toast.makeText(RegisterActivity.this, "Rellena los campos vacíos",
                            Toast.LENGTH_SHORT).show();
                } else {
                    controlador = true;
                }
                return controlador;
            }

            public boolean controlRepetirUsuarios(String nombrePuesto, Usuario[] usuarioGuardados) {
                boolean isSameUserName = false;
                String nombreCargado;
                for (int i = 0; i < usuarioGuardados.length; i++) {
                    nombreCargado = usuarioGuardados[i].getName();
                    if (nombrePuesto.equalsIgnoreCase(nombreCargado)) {
                        isSameUserName = true;
                        register_name.setText("");
                        Toast.makeText(RegisterActivity.this, "Nombre de usuario ocupado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                return isSameUserName;
            }

            public boolean isEmpty(EditText text) {
                CharSequence str = text.getText().toString();
                return TextUtils.isEmpty(str);
            }

            private boolean controlDeEmail(String email) {
                boolean control;

                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher mather = pattern.matcher(email);
                if (mather.find() == true) {
                    control = true;
                }else{
                    control = false;
                    register_email.setText("");
                }
                return control;
            }

            private boolean controlDeContrasenia(String contra) {
                boolean controlx;


                Pattern pattern = Pattern.compile("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$");
                Matcher mather = pattern.matcher(contra);
                if (mather.find()) {
                    controlx = true;
                } else{
                    controlx = false;
                    register_password.setText("");
                    register_repassword.setText("");
                    Toast.makeText(RegisterActivity.this, "La contraseña debe tener una mayúscula" +
                                    ", un dígito y un carácter",
                            Toast.LENGTH_SHORT).show();
                }
                return controlx;
            }

            private boolean controlDeEdad(String edad) {
                boolean control;
                int edadParse = Integer.parseInt(edad);
                if (edadParse > 0 && edadParse < 100) {
                    control = true;
                } else {
                    register_age.setText("");
                    control = false;
                }
                return control;
            }

        });
    }

    public Usuario[] cargarArrayUsuarios(Usuario[] usuarioGuardados) {
        String direccionArchivo = getFilesDir().getAbsolutePath();
        String filePath = direccionArchivo + File.separator + "JSON//Usuarios.json";

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            Gson gson = new Gson();
            //Leemos Json y guardamos en lyst
            usuarioGuardados = gson.fromJson(bufferedReader, Usuario[].class);

            bufferedReader.close();
            fileReader.close();
        } catch (IOException x) {
            Toast.makeText(RegisterActivity.this, x.toString(),
                    Toast.LENGTH_LONG).show();
        }
        return usuarioGuardados;
    }
}