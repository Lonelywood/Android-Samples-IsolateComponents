package com.github.lonelywood.architecture.android.components.photocomponent

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import androidx.core.content.FileProvider
import com.github.lonelywood.architecture.android.App
import com.github.lonelywood.architecture.android.BuildConfig
import java.io.File
import java.io.IOException

interface PhotoTakerComponent {
    fun takePhoto()
    fun addListener(listener: PhotoTakerListener)
    fun removeListener(listener: PhotoTakerListener)
}

class DefaultPhotoTakerComponent: PhotoTakerComponent {
    private val applicationContext: Context = App.instance.applicationContext

    override fun takePhoto() {
        if (applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // Generate file
            try {
                val folder = applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

                val name = System.currentTimeMillis() / 1000
                val image = File.createTempFile(name.toString(), ".jpg", folder)
                val uri = FileProvider.getUriForFile(applicationContext, BuildConfig.APPLICATION_ID + ".provider", image).toString()

                // Take photo by intent
                val intent = Intent(applicationContext, PhotoTakerActivity::class.java)

                intent.putExtra(PhotoTakerActivity.ExtraFileName, uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                applicationContext.startActivity(intent)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun addListener(listener: PhotoTakerListener)
            = PhotoTakerActivity.addListener(listener)

    override fun removeListener(listener: PhotoTakerListener)
            = PhotoTakerActivity.removeListener(listener)
}