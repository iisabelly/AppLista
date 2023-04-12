package andrades.isabelly.applista.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import andrades.isabelly.applista.R;
import andrades.isabelly.applista.adapter.MyAdapter;
import andrades.isabelly.applista.model.MyItem;

public class MainActivity extends AppCompatActivity {

    static int NEW_ITEM_REQUEST = 1;
    MyAdapter myAdapter;
    List<MyItem> itens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // cria a mainActivity com a capacidade de restaurar o conteúdo do estado anterior da tela
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // encontra os itens da Activity
        RecyclerView rvItens = findViewById(R.id.rvItens);
        FloatingActionButton favAddItem = findViewById(R.id.fabAddNewItem);


        myAdapter  = new MyAdapter(this, itens);
        rvItens.setAdapter(myAdapter);

        rvItens.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(),
                DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);

        // define a ação que será feita quando o botão for acionado
        favAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cria a Intent
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                // define a ação da Intent e a executa
                startActivityForResult(i, NEW_ITEM_REQUEST);
            }
        });
    }

    // ação que recebe o valor inserido pelo usuário na NewItemActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // condição para verificar se a resposta recebida é referente ao NEW_ITEM_REQUEST
        if(requestCode == NEW_ITEM_REQUEST) {
            // condição para verificar se a request foi realmente realizada
            if(resultCode == Activity.RESULT_OK) {
                // cria um novo item
                MyItem myItem = new MyItem();
                // adiciona os valores inseridos pelo usuário na NewItemActivity ao item
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                myItem.photo = data.getData();

                // adiciona o novo item a lista de itens
                itens.add(myItem);

                // insere o item ao display
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }

}