package net.lonelywood.architecture.android.components.photocomponent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import net.lonelywood.architecture.android.App;

import java.io.File;
import java.io.IOException;

public class DefaultPhotoTakerComponent implements PhotoTakerComponent {

    private Context mApplicationContext;

    public DefaultPhotoTakerComponent() {
        mApplicationContext = App.getInstance().getApplicationContext();
    }

    @Override
    public void takePhoto() {
        if (mApplicationContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // Generate file
            try {
                File folder = new File(mApplicationContext.getExternalFilesDir(null) + "/Images");

                if (!folder.exists()) {
                    folder.mkdirs();
                }

                Long name = System.currentTimeMillis() / 1000;
                File image = File.createTempFile(name.toString(), ".jpg", folder);

                // Take photo by intent
                Intent intent = new Intent(mApplicationContext, PhotoComponentActivity.class);
                intent.putExtra(PhotoComponentActivity.ExtraFileName, Uri.fromFile(image).toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mApplicationContext.startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addListener(PhotoComponentListener listener){
        PhotoComponentActivity.addListener(listener);
    }

    public void removeListener(PhotoComponentListener listener){
        PhotoComponentActivity.removeListener(listener);
    }
}