package andrades.isabelly.applista.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import andrades.isabelly.applista.R;
import andrades.isabelly.applista.adapter.MyAdapter;
import andrades.isabelly.applista.model.MainActivityViewModel;
import andrades.isabelly.applista.model.MyItem;
import andrades.isabelly.applista.model.Util;

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

        // cria a view model para a main activity que vai guardar os itens da lista
        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        List<MyItem> itens = vm.getItens();

        // cria myAdapter para salvar conteúdo da lista da mainActivity
        myAdapter  = new MyAdapter(this, itens);
        // define o myAdapter como o adapter da lista da mainActivity
        rvItens.setAdapter(myAdapter);
        // define que a lista tem itens com o tamanho fixo
        rvItens.setHasFixedSize(true);

        // cria o layoutManager para a lista, que é responsável por dispor os itens na activity
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        // define o layoutManager como o manager da lista da mainActivity
        rvItens.setLayoutManager(layoutManager);

        // cria o dividerItemDecoration para a lista, que organiza os intens verticalmente
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(),
                DividerItemDecoration.VERTICAL);
        // define a decoration dos itens como o dividerItemDecoration, que é vertical
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

                // atribui o endereço da foto escolhida a variável
                Uri selectedPhotoUri = data.getData();

                try {
                    // carrega a foto no bitmap
                    Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoUri,
                            100, 100);
                    // define o conteúdo do item da foto como o bitmap
                    myItem.photo = photo;
                } catch (FileNotFoundException e){
                    // caso o arquivo não for encontrado, imprime o erro
                    e.printStackTrace();
                }

                // cria a view model para a main activity que vai guardar os itens da lista
                MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
                List<MyItem> itens = vm.getItens();

                // adiciona o novo item a lista de itens
                itens.add(myItem);

                // insere o item ao display
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }

}