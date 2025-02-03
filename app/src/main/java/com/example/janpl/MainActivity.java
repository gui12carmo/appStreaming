package com.example.janpl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Filme current;
    RecyclerView rv;
    FilmeAdapter adpt;
    FloatingActionButton fab;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            adpt.notifyDataSetChanged();
        }
//            finish();
//            overridePendingTransition(0,0);
//            startActivity(getIntent());
//            overridePendingTransition(0,0);
        else {

            Uri uri = Uri.parse(data.getData().toString());
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                if (current != null) {
                    current.set_foto(Filme.BitmapToArray(bmp));
                    adpt.notifyDataSetChanged();
                    current = null;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, insert.class);
                startActivityForResult(it, 2);
            }
        });

        adpt = new FilmeAdapter(this, App.filmes);
        adpt.setOnchangeListener(new IOnchange_listener() {
            @Override
            public void OnChange(Filme f) {
                current = f;
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
        adpt.setOnSacaFotoListener(new IOnSacaFoto() {
            @Override
            public void OnSacaFoto(Filme f) {
                current = f;
                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                it.setType("image/*");
                startActivityForResult(it, 1);
            }
        });

        rv = findViewById(R.id.rv_filmes_main);
        rv.setAdapter(adpt);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }
}