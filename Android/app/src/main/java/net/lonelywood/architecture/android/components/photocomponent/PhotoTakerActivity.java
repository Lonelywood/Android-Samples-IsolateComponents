package net.lonelywood.architecture.android.components.photocomponent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PhotoTakerActivity extends AppCompatActivity {

    public static final String ExtraFileName = "fileName";

    private static List<PhotoTakerListener> listeners = new ArrayList<>();

    private String mFileName;
    private final int RequestImageCapture = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mFileName = savedInstanceState.getString(ExtraFileName);
        } else {
            mFileName = getIntent().getExtras().getString(ExtraFileName);
        }

        Uri uri = Uri.parse(mFileName);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, RequestImageCapture);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestImageCapture) {
            if (resultCode == RESULT_CANCELED){
                for (PhotoTakerListener listener: listeners) {
                    listener.onPhotoTaken(false, null);
                }
            } else if (resultCode == RESULT_OK){
                for (PhotoTakerListener listener: listeners){
                    listener.onPhotoTaken(true, mFileName);
                }
            }
        }

        finish();
    }

    public static void addListener(PhotoTakerListener listener){
        listeners.add(listener);
    }

    public static void removeListener(PhotoTakerListener listener) {
        listeners.remove(listener);
    }
}