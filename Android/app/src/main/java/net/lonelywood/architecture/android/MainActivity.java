package net.lonelywood.architecture.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.lonelywood.architecture.android.components.photocomponent.IPhotoComponent;
import net.lonelywood.architecture.android.components.photocomponent.PhotoComponent;
import net.lonelywood.architecture.android.components.photocomponent.PhotoComponentListener;
import net.lonelywood.architecture.android.components.photocomponent.PhotoTakenArgs;

public class MainActivity extends AppCompatActivity implements PhotoComponentListener, View.OnClickListener {

    private IPhotoComponent _photoComponent;
    private Button _photoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        _photoComponent = new PhotoComponent();
        _photoComponent.addListener(this);

        _photoButton = (Button) findViewById(R.id.main_photo_button);
        _photoButton.setOnClickListener(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void onPhotoTaken(PhotoTakenArgs args) {
        Log.e("onPhotoTaken", "success: " + args.getSuccess() + ", path: " + args.getPath());
    }

    @Override
    public void onClick(View v) {
        _photoComponent.takePhoto();
    }
}