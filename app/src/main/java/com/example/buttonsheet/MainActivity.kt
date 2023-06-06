package com.example.buttonsheet

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    lateinit var clickImageId: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clickImageId=findViewById(R.id.click_image)
        val idBtnDismiss=findViewById<Button>(R.id.idBtnDismiss)
                idBtnDismiss.setOnClickListener{
                    val dialog = BottomSheetDialog(this)
                    val view = layoutInflater.inflate(R.layout.bottom_sheet_design, null)
                    val btnClose = view.findViewById<ImageView>(R.id.cancel_button)
                    val camera= view.findViewById<ImageView>(R.id.camera)
                    val button_gallary= view.findViewById<Button>(R.id.buttonLoadPicture)

                    button_gallary.setOnClickListener {
                        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                        startActivityForResult(gallery, pickImage)
                    }
                    
                    btnClose.setOnClickListener {
                        dialog.dismiss()

                    }
                    camera.setOnClickListener {
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(cameraIntent, pic_id)
                    }
                    dialog.setCancelable(false)
                    dialog.setContentView(view)
                    dialog.show()
                }



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pic_id) {
            val photo = data!!.extras!!["data"] as Bitmap?
            clickImageId.setImageBitmap(photo)
        }
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            clickImageId.setImageURI(imageUri)
        }
    }
    companion object {
        private const val pic_id = 123
    }

}