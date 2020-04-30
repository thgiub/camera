package net.sourceforge.opencamera.gallery.domain.interfaces.video;

public interface VideoInterface {
    interface View{
        void setListeners();
        void init();

    }
    interface Presenter{
        void setView(View view);
        void init();
    }
}
