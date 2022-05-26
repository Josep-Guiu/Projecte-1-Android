package com.example.registro_app;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile_layout);

        ArrayList<Usuario> usuariosExistentes = new ArrayList<>();

        final Usuario usuario;
        Usuario[] usuarioGuardados = new Usuario[0];

        final TextView username = findViewById(R.id.username_label);
        final TextView ageuser = findViewById(R.id.age_label);
        final EditText passworduser = findViewById(R.id.password_label);
        final EditText emailuser = findViewById(R.id.email_label);
        final Button changeprofile = findViewById(R.id.btn_changeprofile);
        final Button devuelta2 = findViewById(R.id.BtnDevuelta2);

        usuario = (Usuario) getIntent().getSerializableExtra("usuarioRecibido");
        Usuario[] usuarioGuardadosJson = (Usuario[]) getIntent().getSerializableExtra("usuariosRecibidos");

        devuelta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volverAMenuPrincipal = new Intent(ProfileActivity.this, MainMenuActivity.class);
                volverAMenuPrincipal.putExtra("usuarioRecibido", usuario);
                volverAMenuPrincipal.putExtra("usuariosRecibidos", usuarioGuardadosJson);
                startActivity(volverAMenuPrincipal);
                finish();

            }
        });

        usuarioGuardados = cargarArrayUsuarios(usuarioGuardados);
        for (int i = 0; i < usuarioGuardados.length; i++) {
            usuariosExistentes.add(usuarioGuardados[i]);
        }

        username.setText(usuario.getName());

        ageuser.setText(usuario.getAge());

        passworduser.setText(usuario.getPassword());

        emailuser.setText(usuario.getEmail());


        changeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email;
                String password;

                boolean control = false;
                boolean isMailOk = true;
                boolean isPassOk = true;
                boolean isSameUserName = false;
            /*email = emailuser.getText().toString();

            password = passworduser.getText().toString();*/

                if(isEmpty(passworduser)){
                    passworduser.setError("Password is Empty.");
                }else if(isEmpty(emailuser)){
                    emailuser.setError("Email is empty.");
                }else
                {
                    control = true;
                    isMailOk =  controlDeEmail(emailuser.getText().toString());
                    isPassOk = controlDeContrasenia(passworduser.getText().toString());

                    if (control && isMailOk && isPassOk) {
                        int identificadorArray = 0;



                        for (Usuario abuscar : usuariosExistentes)
                        {
                            if(usuario.getName().equals(abuscar.getName())){
                                Usuario usuario2 =  new Usuario(usuario.getName(),emailuser.getText().toString(),usuario.getAge(), passworduser.getText().toString()
                                );
                                usuariosExistentes.remove(abuscar);
                                usuariosExistentes.add(usuario2);
                                guardarJSON(usuariosExistentes,abuscar);


                            }
                        }


                        Toast.makeText(ProfileActivity.this, "Usuario modificado",
                                Toast.LENGTH_SHORT).show();


                    }else if(!control)
                    {
                        Toast.makeText(ProfileActivity.this, "Usuario no modificado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
            public boolean isEmpty(EditText text) {
                CharSequence str = text.getText().toString();
                return TextUtils.isEmpty(str);
            }

            private boolean controlDeEmail(String email ) {
                boolean control;

                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher mather = pattern.matcher(email);
                if (mather.find() == true) {
                    control = true;
                }else{
                    control = false;
                    emailuser.setText("");
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
                    passworduser.setText("");
                    passworduser.setText("");
                    Toast.makeText(ProfileActivity.this, "La contraseña debe tener una mayúscula" +
                                    ", un dígito y un carácter",
                            Toast.LENGTH_SHORT).show();
                }
                return controlx;
            }


        });

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
            Toast.makeText(ProfileActivity.this, x.toString(),
                    Toast.LENGTH_LONG).show();
        }
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
            Toast.makeText(ProfileActivity.this, x.toString(),
                    Toast.LENGTH_LONG).show();
        }
        return usuarioGuardados;
    }
}
