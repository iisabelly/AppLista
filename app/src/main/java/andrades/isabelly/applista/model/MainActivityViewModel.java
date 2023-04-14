package andrades.isabelly.applista.model;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    List<MyItem> itens = new ArrayList<>();

    // retorna os itens da lista de itens
    public List<MyItem> getItens() {
        return itens;
    }
}
