package net.sourceforge.opencamera.gallery.domain.interfaces.photo;

import android.view.View;

import net.sourceforge.opencamera.gallery.domain.Photo;

import java.io.IOException;
import java.util.List;

public interface MediaPhotoInterface {
    interface View{
        void setListeners();
        void initVars();
        void initContent();
        void openPhotoInFullScreen(int itemID);
        void updatePhotoList();
        void onPhotoCheckedChanged(int itemID, boolean isChecked);
        void shareSelectedItems();
        void deleteSelectedItems() throws IOException;
        void updateGalleryList();
        void showToast(String message);
        void onFileDeleted(String filePath);
    }
    interface Presenter{
        void setView(View view);
        void init();
        void onPhotoClicked(int itemID);
        void onPhotoLongClicked(int itemID);
        void onResume();
        void onPause();
        boolean isEditMode();
        void enableEditMode(boolean enable);
        void onPhotoCheckedChanged(int itemID, boolean isChecked);
        void onFileSuccessDeleted(String fileName);
        void onFilesDeleted();
        void onFileDeleteFailed(String fileName);
    }
}
