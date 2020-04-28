package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dao.alunoDao;
import com.example.myapplication.helper.Formulario;
import com.example.myapplication.model.Aluno;


public class FormularioActivity extends AppCompatActivity {

    private Button btnSave;
    private Aluno altararAluno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Aluno aluno = (Aluno) getIntent().getSerializableExtra("alunoSelecionado");
            if (aluno != null) {
                this.altararAluno = aluno;
                (new Formulario(FormularioActivity.this)).setAlunoFormulario(aluno);
            }
        }

        btnSave = findViewById(R.id.botao);

        addListeners();
    }

    private void addListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (altararAluno == null) {
                    salvarAluno();
                } else {
                    atualizaAluno();
                }

                finish();
            }
        });
    }

    private void salvarAluno(){
        Formulario fh = new Formulario(this);
        Aluno aluno = fh.getAlunoFormulario();

        (new alunoDao(this)).onInsertAluno(aluno);
        Toast.makeText(
                FormularioActivity.this,
                "Aluno inserido com sucesso",
                Toast.LENGTH_LONG
        ).show();
    }

    private void atualizaAluno()
    {
        Aluno aluno = (new Formulario(this)).getAlunoFormulario();
        aluno.setId(altararAluno.getId());

        (new alunoDao(this)).onUpdateAluno(aluno);

        Toast.makeText(
                FormularioActivity.this,
                "Aluno atualizado com sucesso!",
                Toast.LENGTH_LONG
        ).show();
    }
}
