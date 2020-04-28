package com.example.myapplication.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.myapplication.FormularioActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Aluno;

public class Formulario {
    private EditText nome;
    private EditText telefone;
    private EditText endereco;
    private EditText site;
    private SeekBar grau;
    private ImageView foto;
    private Aluno aluno;

    public Formulario(FormularioActivity form) {
        nome = form.findViewById(R.id.nome);
        telefone = form.findViewById(R.id.telefone);
        endereco = form.findViewById(R.id.endereco);
        site = form.findViewById(R.id.site);
        grau = form.findViewById(R.id.nota);
        foto = form.findViewById(R.id.foto);

        aluno = new Aluno();
    }

    public Aluno getAlunoFormulario() {
        aluno.setNome(nome.getEditableText().toString());
        aluno.setTelefone(telefone.getEditableText().toString());
        aluno.setEndereco(endereco.getEditableText().toString());
        aluno.setSite(site.getEditableText().toString());
        aluno.setGrau((double) grau.getProgress());

        return aluno;
    }

    public void setAlunoFormulario(Aluno aluno) {
        this.aluno = aluno;

        nome.setText(aluno.getNome());
        endereco.setText(aluno.getEndereco());
        telefone.setText(aluno.getTelefone());
        site.setText(aluno.getSite());
        grau.setProgress((int) Math.round(aluno.getGrau()));

        if (aluno.getFoto() != null) {
            this.carregaFotoPerfil(aluno.getFoto());
        }
    }

    private void carregaFotoPerfil(String path) {
        Bitmap imagem = BitmapFactory.decodeFile(path);
        Bitmap imagemTratada = Bitmap.createScaledBitmap(imagem, 100,100, true);

        aluno.setFoto(path);
        foto.setImageBitmap(imagemTratada);
    }
}
