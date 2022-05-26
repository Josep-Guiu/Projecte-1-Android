package com.example.registro_app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final Usuario[] users;
    private final Avatar[] avatars;
    private final String nombreBoton;
    static Context context;


    public MyAdapter(Context context, Usuario[] users, Avatar[] avatars, Button boto1) {
        this.users = users;
        this.avatars = avatars;
        nombreBoton = boto1.getText().toString();
        this.context = context;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView textNombre ;
        TextView textPuntos;
        ViewGroup parent;
        public ViewHolder(@NonNull @NotNull View elemento) {
            super(elemento);
            img = elemento.findViewById(R.id.Img_ImagenDelUsuario);
            textNombre = elemento.findViewById(R.id.Txt_NombreDelUsuario);
            textPuntos = elemento.findViewById(R.id.Txt_PuntuacionDelUsuario);
            parent = elemento.findViewById(R.id.LstRank);
        }

        void bindUser(Usuario user, Avatar[] avatars, String nombreBoton){


            textNombre.setText(user.getName());

            for (int i = 0; i < avatars.length; i++) {

                switch (nombreBoton){
                    case "Rock":
                        if (user.getGender1() >= avatars[i].getPuntuacionMin()
                                && user.getGender1() <= avatars[i].getPuntuacionMax() && avatars[i].getGender().equals("Rock")) {

                            lecturaPuntuacionAvatar(img, i, avatars);
                            textPuntos.setText(String.valueOf(user.getGender1()));
                        }
                        break;
                    case "Rap":
                        if (user.getGender2() >= avatars[i].getPuntuacionMin()
                                && user.getGender2() <= avatars[i].getPuntuacionMax() && avatars[i].getGender().equals("Heavy")) {

                            lecturaPuntuacionAvatar(img, i, avatars);
                            textPuntos.setText(String.valueOf(user.getGender2()));
                        }
                        break;
                    case "Heavy":
                        if (user.getGender3() >= avatars[i].getPuntuacionMin()
                                && user.getGender3() <= avatars[i].getPuntuacionMax() && avatars[i].getGender().equals("Rap")) {

                            lecturaPuntuacionAvatar(img, i, avatars);
                            textPuntos.setText(String.valueOf(user.getGender3()));
                        }
                        break;
                    case "Funk":
                        if (user.getGender4() >= avatars[i].getPuntuacionMin()
                                && user.getGender4() <= avatars[i].getPuntuacionMax() && avatars[i].getGender().equals("Funk")) {

                            lecturaPuntuacionAvatar(img, i, avatars);
                            textPuntos.setText(String.valueOf(user.getGender4()));
                        }
                        break;
                    case "Pop":
                        if (user.getGender5() >= avatars[i].getPuntuacionMin()
                                && user.getGender5() <= avatars[i].getPuntuacionMax() && avatars[i].getGender().equals("Pop")) {

                            lecturaPuntuacionAvatar(img, i, avatars);
                            textPuntos.setText(String.valueOf(user.getGender5()));
                        }
                        break;
                    case "General":
                        if (user.getTotal() >= avatars[i].getPuntuacionMin()
                                && user.getTotal() <= avatars[i].getPuntuacionMax() && avatars[i].getGender().equals("General")) {

                            lecturaPuntuacionAvatar(img, i, avatars);
                            textPuntos.setText(String.valueOf(user.getTotal()));
                        }
                        break;
                }

            }

        }


    }


    private static void lecturaPuntuacionAvatar(ImageView img, int i, Avatar[] avatars){
        String path = context.getFilesDir().getAbsolutePath();
        String pathImage = path + File.separator + "JSON/Resources/" + avatars[i].getNombre() + ".png";
        Bitmap bmp = BitmapFactory.decodeFile(pathImage);

        img.setImageBitmap(bmp);
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View elemento = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_layout, parent, false);
        return new ViewHolder(elemento);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.bindUser(users[position], avatars, nombreBoton);
    }

    public int getItemCount(){
        return users.length;
    }



}