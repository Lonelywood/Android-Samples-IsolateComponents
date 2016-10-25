package net.lonelywood.architecture.android.components.photocomponent;

public interface PhotoComponentListener {
    void onPhotoTaken(Boolean success, String path);
}