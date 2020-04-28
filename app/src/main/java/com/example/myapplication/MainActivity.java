package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.myapplication.adapter.AlunoAdapter;
import com.example.myapplication.dao.alunoDao;
import com.example.myapplication.model.Aluno;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView alunoLista;
    protected Aluno alunoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alunoLista = findViewById(R.id.lista_alunos);

        onLoadListaAluno();
        onLoadAddAluno();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onLoadListaAluno();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.novoAluno:
                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(intent);
                return false;
            default:
                Toast.makeText(
                        MainActivity.this,
                        "Funcionalidade ainda não foi implementada!",
                        Toast.LENGTH_LONG
                ).show();
        }

        onLoadListaAluno();
        return super.onOptionsItemSelected(item);
    }

    public void onLoadListaAluno()  {
        alunoDao dao = new alunoDao(this);
        final ArrayList<Aluno> alunos = (ArrayList<Aluno>) dao.onListaAluno();
        dao.close();

        AlunoAdapter adapter = new AlunoAdapter(this, alunos);

        alunoLista.setAdapter(adapter);
        registerForContextMenu(alunoLista);
    }

    private void onLoadAddAluno() {
        alunoLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu menu = new PopupMenu(MainActivity.this, view);
                menu.getMenu().add("Editar");
                menu.getMenu().add("Remover");
                menu.getMenu().add("Enviar Mensagem");
                menu.getMenu().add("Ligar");
                menu.getMenu().add("Ver no mapa");
                menu.getMenu().add("Abrir site");

                alunoSelecionado = (Aluno) alunoLista.getAdapter().getItem(position);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        popUpMenuActions(item);
                        onLoadListaAluno();
                        return true;
                    }
                });
                menu.show();
            }
        });
    }

    public void popUpMenuActions(MenuItem item) {
        switch (item.getTitle().toString()) {
            case "Editar":
                onUpdate();
                break;
            case "Remover":
                onDelete();
                break;
            case "Ligar":
                onCall();
                break;
            case "Enviar Mensagem":
               onSendMessage();
                break;
            case "Ver no mapa":
                onSeeInMap();
                break;
            case "Abrir site":
                openWebsite();
                break;
            default:
                Toast.makeText(MainActivity.this, "NOT IMPLEMENTED YET", Toast.LENGTH_LONG).show();
        }
    }

    private void openWebsite() {
        if (alunoSelecionado.getSite().isEmpty()) {
            Toast.makeText(
                    MainActivity.this,
                    "Esse aluno não possui um site cadastrado!",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + alunoSelecionado.getSite()));
            startActivity(browserIntent);
        }
    }

    private void onSeeInMap() {
        if (alunoSelecionado.getEndereco().isEmpty()) {
            Toast.makeText(
                    MainActivity.this,
                    "Esse aluno não possui um endereço cadastrado!",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + alunoSelecionado.getEndereco());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

    private void onCall() {
        if (alunoSelecionado.getTelefone().isEmpty()) {
            Toast.makeText(
                    MainActivity.this,
                    "Esse aluno não possui um número cadastrado!",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", alunoSelecionado.getTelefone(), null));
            startActivity(intent);
        }
    }

    private void onSendMessage() {
        if (alunoSelecionado.getTelefone().isEmpty()) {
            Toast.makeText(
                    MainActivity.this,
                    "Esse aluno não possui um número cadastrado!",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.setData(Uri.parse("smsto:" + Uri.encode(alunoSelecionado.getTelefone())));
            smsIntent.putExtra("sms_body","Mensagem");
            startActivity(smsIntent);
        }
    }

    private void onUpdate() {
        Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
        intent.putExtra("selected_student", alunoSelecionado);
        startActivity(intent);
    }

    private void onDelete() {
        alunoDao dao = new alunoDao(MainActivity.this);
        dao.onDeleteAluno(alunoSelecionado);
        dao.close();

        Toast.makeText(
                MainActivity.this,
                "Aluno removido com sucesso!",
                Toast.LENGTH_LONG
        ).show();
    }
}
