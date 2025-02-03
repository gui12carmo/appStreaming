package com.example.janpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MySQL extends SQLiteOpenHelper {
    public static final String DATATABLE = "filmes";
    public static final String ID = "id";
    public static final String TITULO = "titulo";
    public static final String GENERO = "genero";
    public static final String FOTO = "foto";
    Context ctx;
    public  static final String DATABASENAME ="cinema.db";
    public MySQL(@Nullable Context context,
                 int version) {
        super(context, DATABASENAME, null, version);
        ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql= "CREATE TABLE " + DATATABLE + " (\n" +
                "    " + ID + "     INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + TITULO + " TEXT,\n" +
                "    " + GENERO + " TEXT,\n" +
                "    " + FOTO + "   BLOB\n" +
                ");\n";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          String sql ="drop table if exists "+ DATATABLE +";";
          db.execSQL(sql);
          onCreate(db);
    }

    public  long inserirFilme(Filme f){
        long ok=0;
        SQLiteDatabase db = getWritableDatabase();
        try{
          db.beginTransaction();
          ContentValues cv = new ContentValues();
          cv.put(TITULO,f.get_titulo());
          cv.put(GENERO,f.get_genero());
          cv.put(FOTO,f.get_foto());
          ok = db.insert(DATATABLE,null,cv);
          db.setTransactionSuccessful();
        }catch(SQLiteException erro){
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            db.endTransaction();
            return  ok;
        }

    }

    public List<Filme> getFilmes(){
        List<Filme>filmes = new ArrayList<>();
        SQLiteDatabase db =getReadableDatabase();
        try{
            String sql ="select * from "+ DATATABLE +" ;";
            Cursor cur =db.rawQuery(sql,null);
            if(cur.getCount()>0){
                cur.moveToFirst();
                do{
                    filmes.add(new Filme(
                            cur.getInt(0),
                            cur.getString(1),
                            cur.getString(2),
                            cur.getBlob(3)
                    ));
                }while(cur.moveToNext());
            }

        }catch (SQLiteException erro){
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_SHORT).show();

        }finally {
            return  filmes;

        }


    }

    public  long updateFilme(Filme f){
        long ok=0;
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(TITULO,f.get_titulo());
            cv.put(GENERO,f.get_genero());
            cv.put(FOTO,f.get_foto());
            ok=db.update(DATATABLE,cv,ID +"=?", new String[]{String.valueOf(f.get_id())} );
            db.setTransactionSuccessful();
        }catch(SQLiteException erro){
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            db.endTransaction();
            return  ok;
        }

    }

    public  long deleteFilme(Filme f){
        long ok=0;
        SQLiteDatabase db= getWritableDatabase();
        try{
            db.beginTransaction();
            ok= db.delete(DATATABLE,ID+"=?", new String[]{String.valueOf(f.get_id())});
            db.setTransactionSuccessful();
        }catch(SQLiteException erro){
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            db.endTransaction();
            return  ok;
        }

    }


}
