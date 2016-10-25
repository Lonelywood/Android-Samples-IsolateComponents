package net.lonelywood.architecture.android.components.photocomponent;

public interface PhotoTakerComponent {
    void takePhoto();
    void addListener(PhotoComponentListener listener);
    void removeListener(PhotoComponentListener listener);
}