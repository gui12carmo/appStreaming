package com.example.janpl;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    public  static final String TAG="xpto";
    Context ctx;
    public static List<Filme> filmes;
    @Override
    public void onCreate() {
        super.onCreate();
//        criaLista();
        MySQL bd = new MySQL(getApplicationContext(),1);
     filmes =bd.getFilmes();
//        for(Filme f : filmes) {
//        bd.inserirFilme(f);
//        Log.i(TAG, f.toString());
//    }
}

    void criaLista(){
        filmes = new ArrayList<>();
        filmes.add(new Filme(1,"Rei Leão","terror",new byte[]{}));
        filmes.add(new Filme(2,"Gladiador","policial",new byte[]{}));
        filmes.add(new Filme(3,"Vikings","terror",new byte[]{}));
        filmes.add(new Filme(4,"O Padrinho","terror",new byte[]{}));
        filmes.add(new Filme(5,"A Sereia","terror",new byte[]{}));
    }
}
