package net.sourceforge.opencamera.gallery.domain.interfaces.interactors;

public interface MainInteractorInterface  {
    interface MediaListener{
        void setEditMode(boolean enable);
        void shareSelectedItems();
        void deleteSelectedItems();
    }

}
