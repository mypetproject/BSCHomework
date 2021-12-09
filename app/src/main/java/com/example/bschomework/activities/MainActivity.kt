package com.example.bschomework.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.example.bschomework.R
import com.example.bschomework.databinding.ActivityMainBinding
import com.example.bschomework.presenters.MainActivityPresenter
import java.io.File

class MainActivity : AppCompatActivity(), MainActivityView {

    private var menu: Menu? = null
    private lateinit var binding: ActivityMainBinding

    private lateinit var photoUri: Uri

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {

            setPhotoUri()

            getCameraContent.launch(photoUri)
        }
    }

    private val getCameraContent = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { result ->

        if (result) {
            binding.photoIv.visibility = View.VISIBLE
            binding.photoIv.setImageURI(photoUri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.presenter = MainActivityPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_options_menu, menu)
        this.menu = menu

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.about_menu_item -> {
                aboutMenuItemClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun aboutMenuItemClicked() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    private fun setPhotoUri() {
        photoUri = FileProvider.getUriForFile(
            this,
            "com.example.bschomework.fileprovider",
            File(filesDir, "temp.jpg")
        )
    }

    override fun photoButtonClicked() {
        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }
}