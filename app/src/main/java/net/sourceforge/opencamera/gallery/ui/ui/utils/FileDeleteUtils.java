package net.sourceforge.opencamera.gallery.ui.ui.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import net.sourceforge.opencamera.gallery.HelperUtils;
import net.sourceforge.opencamera.gallery.domain.interfaces.photo.MediaPhotoInterface;

import java.util.List;


public class FileDeleteUtils implements Runnable {
    List<String> filesList;
    MediaPhotoInterface.Presenter presenter;
    Context context;

    public FileDeleteUtils(List<String> filesList, MediaPhotoInterface.Presenter presenter, Context context) {
        this.filesList = filesList;
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public void run() {
        for (String filePath : filesList)
            HelperUtils.deleteFile(filePath, context);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                presenter.onFilesDeleted();
            }
        });
    }
}
