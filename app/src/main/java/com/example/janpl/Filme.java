package com.example.janpl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;

public class Filme {
    //region Campos & Getters e Setters
    private int _id;
    private  String _titulo;
    private  String _genero;

    private  byte[] _foto;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_titulo() {
        return _titulo;
    }

    public void set_titulo(String _titulo) {
        this._titulo = _titulo;
    }

    public String get_genero() {
        return _genero;
    }

    public void set_genero(String _genero) {
        this._genero = _genero;
    }

    public byte[] get_foto() {
        return _foto;
    }

    public void set_foto(byte[] _foto) {
        this._foto = _foto;
    }
    //endregion

    public static Bitmap ArrayToBitmap(byte[] foto){
      return   BitmapFactory.decodeByteArray(foto,0,foto.length);
    }

    public  static  byte[] BitmapToArray(Bitmap bmp){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,out);
        return  out.toByteArray();
    }

    public Filme(int _id, String _titulo, String _genero, byte[] _foto) {
        this._id = _id;
        this._titulo = _titulo;
        this._genero = _genero;
        this._foto = _foto;
    }

    public Filme(int _id, String _titulo, String _genero, Bitmap bmp) {
        this._id = _id;
        this._titulo = _titulo;
        this._genero = _genero;
        this._foto =BitmapToArray(bmp);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%d %s %s", _id,_titulo,_genero);
    }
}
