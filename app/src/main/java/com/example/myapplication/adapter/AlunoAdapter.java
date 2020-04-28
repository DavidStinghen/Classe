package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.Aluno;

import java.io.File;
import java.util.ArrayList;

public class AlunoAdapter extends ArrayAdapter<Aluno> {
    public AlunoAdapter(Context context, ArrayList<Aluno> alunos) {
        super(context, 0, alunos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Aluno aluno = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        ImageView imagemPerfil = (ImageView) convertView.findViewById(R.id.foto);
        TextView nome = (TextView) convertView.findViewById(R.id.nome);

        try {
            File imageFile = new File(aluno.getFoto());
            Bitmap image = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            imagemPerfil.setImageBitmap(image);
        } catch (Exception e) {
            imagemPerfil.setImageResource(R.drawable.ic_no_image);
        }

        nome.setText(aluno.getNome());

        return convertView;

    }
}
