package net.lonelywood.architecture.android.components.photocomponent;

public interface IPhotoComponent {
    void takePhoto();
    void addListener(PhotoComponentListener listener);
    void removeListener(PhotoComponentListener listener);
}