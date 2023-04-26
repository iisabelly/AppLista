package andrades.isabelly.applista.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import andrades.isabelly.applista.R;
import andrades.isabelly.applista.model.MainActivityViewModel;
import andrades.isabelly.applista.model.MyItem;
import andrades.isabelly.applista.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {
    static int PHOTO_PICKER_REQUEST = 1;
    Uri photoSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // cria a mainActivity com a capacidade de restaurar o conteúdo do estado anterior da tela
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        // cria a view model para a new item activity que vai guardar os itens
        NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);

        // "pega" o endereço da imagem na view model da tela
        Uri selectPhotoLocation = vm.getSelectPhotoLocation();

        // se nenhuma foto tinha sido selecionada, vai verificar o endereço da foto selecionada na Image View
        if(selectPhotoLocation != null) {
            ImageView imvPhotoPreview = findViewById(R.id.imvPhotoPreview);
            imvPhotoPreview.setImageURI(selectPhotoLocation);
        }

        // encontra os itens da Activity
        Button btnAddItem = findViewById(R.id.btnAddItem);
        ImageButton imgCI = findViewById(R.id.imbCl);

        // define a ação que será feita quando o botão for acionado
        imgCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cria a Intent
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                // define o tipo da intent
                photoPickerIntent.setType("image/*");
                // define a ação da Intent e a executa
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);
            }
        });
        // define a ação que será feita quando o botão for acionado
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // condição que verifica se alguma foto foi selecionada
                if (photoSelected == null) {
                    // caso nenhuma foto foi selecionada, o usuário deve ser avisado para adicionar
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                // encontra a caixa de texto do título e pega o conteúdo inserido
                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();
                // condição que verifica se algum titulo foi inserido
                if (title.isEmpty()) {
                    // caso nenhum titulo foi escrito, o usuário deve ser avisado para adicionar
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                // encontra a caixa de texto da descrição e pega o conteúdo inserido
                EditText etDesc = findViewById(R.id.etDesc);
                String description = etDesc.getText().toString();
                // condição que verifica se alguma descrição foi inserida
                if (description.isEmpty()) {
                    // caso nenhuma descrição foi escrito, o usuário deve ser avisado para adicionar
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // cria a intent
                Intent i = new Intent();
                // define o conteúdo da intent
                i.setData(photoSelected);
                i.putExtra("title", title);
                i.putExtra("description", description);
                // retorna o resultado para a MainActivity
                setResult(Activity.RESULT_OK, i);
                finish();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // condição que verifica se a resposta se refere ao PHOTO_PICKER_REQUEST
        if(requestCode == PHOTO_PICKER_REQUEST) {
            // condição que vferifica se a resposta retornou com sucesso
            if(resultCode == Activity.RESULT_OK) {

                // "pega" o endereço da imagem
                Uri photoSelected = data.getData();

                // encontra a Image View na tela
                ImageView imvPhotoPreview = findViewById(R.id.imvPhotoPreview);
                // define o a imagem no Image View por meio do endereço
                imvPhotoPreview.setImageURI(photoSelected);

                // cria a view model para a new item activity que vai guardar os itens
                NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);
                // define o endereço da foto selecionada na view model
                vm.setSelectPhotoLocation(photoSelected);

                // "pega" a imagem selecionada pelo usuário
                photoSelected = data.getData();
                // encontra a imageView na activity
                ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview);
                // define o conteúdo da imageView como a foto escolhida
                imvfotoPreview.setImageURI(photoSelected);

            }
        }
    }
}