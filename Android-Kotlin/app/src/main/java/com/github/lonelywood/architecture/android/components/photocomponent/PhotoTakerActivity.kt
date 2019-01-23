package com.github.lonelywood.architecture.android.components.photocomponent

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity

interface PhotoTakerListener {
    fun onPhotoTaken(success: Boolean, path: String)
}

class PhotoTakerActivity: AppCompatActivity() {
    companion object {
        const val ExtraFileName: String = "fileName"

        private val listeners: ArrayList<PhotoTakerListener> = ArrayList()

        fun addListener(listener: PhotoTakerListener) { listeners.add(listener) }
        fun removeListener(listener: PhotoTakerListener) { listeners.remove(listener) }
    }

    private val REQUEST_IMAGE_CAPTURE = 1
    private var fileName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            fileName = savedInstanceState.getString(ExtraFileName)
        } else {
            fileName = intent?.extras?.getString(ExtraFileName)
        }

        val uri: Uri = Uri.parse(fileName)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        grantUriPermissionsForIntent(intent, uri)

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                for (listener in listeners) {
                    listener.onPhotoTaken(false, "")
                }
            } else if (resultCode == Activity.RESULT_OK) {
                for (listener in listeners) {
                    listener.onPhotoTaken(true, fileName!!)
                }
            }
        }

        finish()
    }

    private fun grantUriPermissionsForIntent(intent: Intent, uri: Uri) {
        val resInfoList = applicationContext.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)

        for (resInfo in resInfoList) {
            val packageName = resInfo.activityInfo.packageName
            applicationContext.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            applicationContext.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
}