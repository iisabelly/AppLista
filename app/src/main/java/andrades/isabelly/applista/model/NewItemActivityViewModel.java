package andrades.isabelly.applista.model;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

public class NewItemActivityViewModel extends ViewModel {
    Uri selectPhotoLocation = null;

    // retorna o endereço da imagem
    public Uri getSelectPhotoLocation() {

        return selectPhotoLocation;
    }

    // define um novo endereço para a imagem
    public void setSelectPhotoLocation(Uri selectPhotoLocation) {
        this.selectPhotoLocation = selectPhotoLocation;
    }
}
