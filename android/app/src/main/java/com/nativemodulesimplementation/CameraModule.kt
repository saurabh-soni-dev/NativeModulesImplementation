package com.nativemodulesimplementation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.facebook.react.bridge.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private val CAMERA_REQUEST_CODE = 1888
    private var cameraPromise: Promise? = null
    private lateinit var photoURI: Uri

    init {
        reactContext.addActivityEventListener(object : ActivityEventListener {
            override fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
                if (requestCode == CAMERA_REQUEST_CODE) {
                    if (cameraPromise != null) {
                        when (resultCode) {
                            Activity.RESULT_OK -> {
                                // Image captured successfully
                                val resultMap = Arguments.createMap()
                                saveImageToExternalStorage(photoURI)
                                resultMap.putString("uri", photoURI.toString())
                                cameraPromise?.resolve(resultMap)
                            }
                            Activity.RESULT_CANCELED -> {
                                // User canceled the camera, handle this case
                                cameraPromise?.resolve("Camera operation was cancelled")
                            }
                            else -> {
                                // Some other error occurred
                                cameraPromise?.reject("ERROR", "Camera operation failed")
                            }
                        }
                        cameraPromise = null
                    }
                }
            }


            override fun onNewIntent(intent: Intent?) {
                // Not needed for this example
            }
        })
    }

    override fun getName(): String {
        return "CameraModule"
    }

    @ReactMethod
    fun openCamera(promise: Promise) {
        val currentActivity = currentActivity ?: run {
            promise.reject("Activity doesn't exist")
            return
        }

        cameraPromise = promise

        try {
            val photoFile: File? = createImageFile()
            photoURI = FileProvider.getUriForFile(reactApplicationContext, "${reactApplicationContext.packageName}.fileprovider", photoFile!!)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            currentActivity.startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        } catch (e: Exception) {
            cameraPromise?.reject("Failed to open camera", e)
            cameraPromise = null
        }
    }

    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp: String = System.currentTimeMillis().toString()
        return File.createTempFile(
            "JPEG_$timeStamp", /* prefix */
            ".jpg",         /* suffix */
            reactApplicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES) /* directory */
        )
    }

    private fun saveImageToExternalStorage(photoUri: Uri): Uri? {
        try {
            // Create a unique file name for the image
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "IMG_$timeStamp.jpg"

            // Get the external storage directory (e.g., Pictures)
            val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            if (!storageDir.exists()) {
                storageDir.mkdirs()  // Create directory if it doesn't exist
            }

            // Create the file
            val imageFile = File(storageDir, fileName)
            val fos = FileOutputStream(imageFile)

            // Access contentResolver via activity or context
            val inputStream = reactApplicationContext.contentResolver.openInputStream(photoUri)  // Use reactContext to get contentResolver
            inputStream?.use { it.copyTo(fos) }  // Copy data from inputStream to the file output stream

            fos.flush()
            fos.close()

            // Return the saved file URI
            return Uri.fromFile(imageFile)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}
