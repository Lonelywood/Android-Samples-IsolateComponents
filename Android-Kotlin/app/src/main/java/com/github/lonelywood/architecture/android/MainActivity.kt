package com.github.lonelywood.architecture.android

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.lonelywood.architecture.android.components.photocomponent.DefaultPhotoTakerComponent
import com.github.lonelywood.architecture.android.components.photocomponent.PhotoTakerComponent
import com.github.lonelywood.architecture.android.components.photocomponent.PhotoTakerListener
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity(), PhotoTakerListener {
    private lateinit var photoTakerComponent: PhotoTakerComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        photoTakerComponent = DefaultPhotoTakerComponent()
        photoTakerComponent.addListener(this)

        mainPhotoButton.setOnClickListener {
            photoTakerComponent.takePhoto()
        }
    }

    override fun onPhotoTaken(success: Boolean, path: String) {
        Log.e("onPhotoTaken", "success: $success, path: $path")
    }

    override fun onDestroy() {
        photoTakerComponent.removeListener(this)
        super.onDestroy()
    }
}