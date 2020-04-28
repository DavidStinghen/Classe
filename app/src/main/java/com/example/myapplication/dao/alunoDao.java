package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class alunoDao extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String TABLE = "ALunos";
    private static final String DATABASE = "alunos";
    private static final String[] COLUMNS = {
            "id",
            "nome",
            "telefone",
            "endereco",
            "site",
            "foto",
            "grau",
    };

    public alunoDao(@Nullable Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE + "("
                + "id INTEGER PRIMARY KEY,"
                + "nome TEXT UNIQUE NOT NULL,"
                + "telefone TEXT,"
                + "endereco TEXT,"
                + "site TEXT,"
                + "foto TEXT,"
                + "grau REAL);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE;
        db.execSQL(sql);
        onCreate(db);
    }

    public void onInsertAluno(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("foto", aluno.getFoto());
        values.put("grau", aluno.getGrau());

        getWritableDatabase().insert(TABLE, null, values);
    }

    public List<Aluno> onListaAluno() {
        List<Aluno> alunos = new ArrayList<>();

        Cursor c = getWritableDatabase().query(
                TABLE,
                COLUMNS,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (c.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(0));
            aluno.setNome(c.getString(1));
            aluno.setTelefone(c.getString(2));
            aluno.setEndereco(c.getString(3));
            aluno.setSite(c.getString(4));
            aluno.setFoto(c.getString(5));
            aluno.setGrau(c.getDouble(6));

            alunos.add(aluno);
        }

        c.close();

        return alunos;
    }

    public void onDeleteAluno(Aluno aluno) {
        String[] where = {aluno.getId().toString()};

        getWritableDatabase().delete(TABLE, "id=?", where);
    }

    public void onUpdateAluno(Aluno aluno) {
        String[] where = {aluno.getId().toString()};

        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("foto", aluno.getFoto());
        values.put("grau", aluno.getGrau());

        getWritableDatabase().update(TABLE, values, "id=?", where);
    }

    public boolean isAluno(String id) {
        Cursor c = getWritableDatabase().rawQuery(
            "select id from " + TABLE + " where id = ?",
                new String[] {id}
        );

        int total = c.getCount();
        c.close();

        return total > 0;
    }
}

