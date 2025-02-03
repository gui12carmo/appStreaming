package com.example.janpl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FilmeAdapter extends RecyclerView.Adapter<FilmeAdapter.ViewHolder> {
    Context ctx;
    List<Filme>filmes;

    public FilmeAdapter(Context ctx, List<Filme> filmes) {
        this.ctx = ctx;
        this.filmes = filmes;
    }

    @NonNull
    @Override
    public FilmeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(ctx).inflate(R.layout.item_filme,parent,false);
        return new ViewHolder(v);
    }

    IOnSacaFoto listener;

    public void setOnchangeListener(IOnchange_listener li){this.lst=li;}
    public void setOnSacaFotoListener(IOnSacaFoto lst){this.listener=lst;}

    IOnchange_listener lst;
    @Override
    public void onBindViewHolder(@NonNull FilmeAdapter.ViewHolder holder, int position) {
       Filme este = filmes.get(position);
       holder.txt_id_itemfoto.setText(String.valueOf(este.get_id()));
       holder.edit_titulo_itemfoto.setText(este.get_titulo());
        ArrayAdapter<String>adpt = new ArrayAdapter<>(
                ctx,
                R.layout.item_drop,
                ctx.getResources().getStringArray(R.array.generos)
        );
        adpt.setDropDownViewResource(R.layout.lista_drop);
        int pos = adpt.getPosition(este.get_genero());
        holder.spin_genero_itemfoto.setAdapter(adpt);
        holder.spin_genero_itemfoto.setSelection(pos);
        holder.img_id_itemfoto.setTag(este);
        holder.img_id_itemfoto.setImageBitmap(Filme.ArrayToBitmap(este.get_foto()));
        holder.img_id_itemfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filme este =(Filme) v.getTag();
                listener.OnSacaFoto(este);
            }
        });
        holder.bt_delete_itemfilme.setTag(este);
        holder.bt_delete_itemfilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filme este =(Filme)v.getTag();
                MySQL mySQL = new MySQL(ctx,1);
                mySQL.deleteFilme(este);
                App.filmes =mySQL.getFilmes();
                lst.OnChange(este);
            }
        });

        holder.bt_update_itemfilme.setTag(este);
        holder.bt_update_itemfilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filme este =(Filme)v.getTag();
                este.set_titulo(holder.edit_titulo_itemfoto.getText().toString());
                este.set_genero(holder.spin_genero_itemfoto.getSelectedItem().toString());
                BitmapDrawable dw = (BitmapDrawable) holder.img_id_itemfoto.getDrawable();
                Bitmap bmp = dw.getBitmap();
                if(bmp !=null)este.set_foto(Filme.BitmapToArray(bmp));
                else este.set_foto(new byte[]{});
                MySQL mySQL = new MySQL(v.getContext(),1);
                long ok= mySQL.updateFilme(este);
                if(ok !=0){
                    notifyDataSetChanged();
                    Toast.makeText(v.getContext(), "Gravado com sucesso", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(v.getContext(), "Erro", Toast.LENGTH_SHORT).show();
            }
        });

    }//fim onBindViewHolder

    @Override
    public int getItemCount() {
        return filmes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_id_itemfoto;
        TextView txt_id_itemfoto;
        EditText edit_titulo_itemfoto;
        Spinner spin_genero_itemfoto;
        Button bt_delete_itemfilme;

        Button  bt_update_itemfilme;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_id_itemfoto = itemView.findViewById(R.id.img_id_itemfoto);
            txt_id_itemfoto=itemView.findViewById(R.id.txt_id_itemfoto);
            edit_titulo_itemfoto=itemView.findViewById(R.id.edit_titulo_itemfoto);
            spin_genero_itemfoto=itemView.findViewById(R.id.spin_genero_itemfoto);
            bt_delete_itemfilme=itemView.findViewById(R.id.bt_delete_itemfilme);
            bt_update_itemfilme=itemView.findViewById(R.id.bt_update_itemfilme);
        }
    }
}
