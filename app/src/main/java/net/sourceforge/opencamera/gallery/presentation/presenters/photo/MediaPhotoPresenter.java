package net.sourceforge.opencamera.gallery.presentation.presenters.photo;

import net.sourceforge.opencamera.gallery.domain.Photo;
import net.sourceforge.opencamera.gallery.domain.interactors.MainInteractor;
import net.sourceforge.opencamera.gallery.domain.interfaces.interactors.MainInteractorInterface;
import net.sourceforge.opencamera.gallery.domain.interfaces.photo.MediaPhotoInterface;

import java.io.IOException;
import java.util.List;


public class MediaPhotoPresenter implements MediaPhotoInterface.Presenter, MainInteractorInterface.MediaListener {
    private MediaPhotoInterface.View view;
    private MainInteractor interactor;
    private boolean isEditMode = false;

    @Override
    public void setView(MediaPhotoInterface.View view) {
        this.view = view;
    }

    @Override
    public void init() {
        view.initVars();
        view.initContent();
        view.setListeners();
        interactor= MainInteractor.getInstance();
    }

    @Override
    public void onPhotoClicked(int itemID) {
        if (!isEditMode)
            view.openPhotoInFullScreen(itemID);
    }

    @Override
    public void onPhotoLongClicked(int itemID) {
        interactor.setEditMode(true);
        //Intent shareIntent= new Intent();
        //shareIntent.setAction(Intent.ACTION_SEND);
        //shareIntent.putExtra(Intent.EXTRA_STREAM,HelperUtils.getUri(context,galleryList.get(i).getImagePath() ));
        //shareIntent.setType("image/*");
        //toAct.startActivity(Intent.createChooser(shareIntent, toAct.getResources().getText(R.string.send_to)));
    }

    @Override
    public void onResume() {
        interactor.addListener(this);
    }

    @Override
    public void onPause() {
        interactor.removeListener(this);
    }

    @Override
    public boolean isEditMode() {
        return isEditMode;
    }

    @Override
    public void enableEditMode(boolean enable) {
        interactor.setEditMode(enable);
    }

    @Override
    public void setEditMode(boolean enable) {
        isEditMode = enable;
        view.updatePhotoList();
    }

    @Override
    public void shareSelectedItems() {
        view.shareSelectedItems();
    }

    @Override
    public void deleteSelectedItems() {
        try {
            view.deleteSelectedItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPhotoCheckedChanged(int itemID, boolean isChecked) {
        view.onPhotoCheckedChanged(itemID, isChecked);
    }

    @Override
    public void onFileSuccessDeleted(String fileName) {
        //view.onFileDeleted(fileName);
        //view.updateGalleryList();
    }

    @Override
    public void onFilesDeleted() {
        view.updateGalleryList();
    }

    @Override
    public void onFileDeleteFailed(String fileName) {
        view.showToast("Error deleting files: " + fileName);
    }
}
