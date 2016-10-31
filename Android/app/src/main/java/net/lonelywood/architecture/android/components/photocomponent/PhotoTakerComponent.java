package net.lonelywood.architecture.android.components.photocomponent;

public interface PhotoTakerComponent {
    void takePhoto();
    void addListener(PhotoTakerListener listener);
    void removeListener(PhotoTakerListener listener);
}