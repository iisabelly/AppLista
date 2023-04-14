package andrades.isabelly.applista.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import andrades.isabelly.applista.R;
import andrades.isabelly.applista.activity.MainActivity;
import andrades.isabelly.applista.model.MyItem;

public class MyAdapter extends RecyclerView.Adapter {
    MainActivity mainActivity;
    List<MyItem> itens;

    public MyAdapter(MainActivity mainActivity, List<MyItem> itens) {
        this.mainActivity = mainActivity;
        this.itens = itens;
    }

    // cria os elementos de interface para o item
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // lê o arquivo xml
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        // cria o novo objeto
        View v = inflater.inflate(R.layout.item_list, parent, false);
        // retorna a view dentro de um holder
        return new MyViewHolder(v);
    }

    // recebe o viewHolder e preenche a lista na mainActivity
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyItem myItem = itens.get(position);
        View v = holder.itemView;

        // encontra os itens na view
        ImageView imvFoto = v.findViewById(R.id.imvPhoto);
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        TextView tvDesc = v.findViewById(R.id.tvDesc);

        // define o conteúdo dos itens
        imvFoto.setImageURI(myItem.photo);
        tvTitle.setText(myItem.title);
        tvDesc.setText(myItem.description);

    }

    @Override
    public int getItemCount(){
        return itens.size();
    }
}
