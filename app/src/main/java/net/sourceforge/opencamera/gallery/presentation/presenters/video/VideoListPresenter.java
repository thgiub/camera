package net.sourceforge.opencamera.gallery.presentation.presenters.video;

import net.sourceforge.opencamera.gallery.domain.interactors.MainInteractor;
import net.sourceforge.opencamera.gallery.domain.interfaces.video.VideoInterface;

public class VideoListPresenter implements VideoInterface.Presenter {
    private MainInteractor  interactors;
    private VideoInterface.View view;


    @Override
    public void setView(VideoInterface.View view) {
       this.view=view;
    }

    @Override
    public void init() {
        interactors= MainInteractor.getInstance();

    }
}
