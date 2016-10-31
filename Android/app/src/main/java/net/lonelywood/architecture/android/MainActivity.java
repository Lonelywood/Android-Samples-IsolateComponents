package net.lonelywood.architecture.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.lonelywood.architecture.android.components.photocomponent.DefaultPhotoTakerComponent;
import net.lonelywood.architecture.android.components.photocomponent.PhotoTakerListener;
import net.lonelywood.architecture.android.components.photocomponent.PhotoTakerComponent;

public class MainActivity extends AppCompatActivity implements PhotoTakerListener {

    private PhotoTakerComponent mPhotoTakerComponent;
    private Button mPhotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mPhotoTakerComponent = new DefaultPhotoTakerComponent();
        mPhotoTakerComponent.addListener(this);

        mPhotoButton = (Button) findViewById(R.id.main_photo_button);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoTakerComponent.takePhoto();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void onPhotoTaken(Boolean success, String path) {
        Log.e("onPhotoTaken", "success: " + success + ", path: " + path);
    }

    @Override
    protected void onDestroy() {
        mPhotoTakerComponent.removeListener(this);
        super.onDestroy();
    }
}