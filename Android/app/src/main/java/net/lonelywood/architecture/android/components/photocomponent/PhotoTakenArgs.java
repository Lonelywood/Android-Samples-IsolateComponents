package net.lonelywood.architecture.android.components.photocomponent;

public class PhotoTakenArgs {
    private Boolean _success = false;
    private String _path = null;

    public PhotoTakenArgs(Boolean success, String path){
        _success = success;
        _path = path;
    }

    public Boolean getSuccess() {
        return _success;
    }

    public String getPath() {
        return _path;
    }
}