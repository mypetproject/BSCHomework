package com.example.bschomework

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.example.bschomework.databinding.ActivityMainBinding
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
            binding.photoIv.setImageURI(photoUri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        binding.presenter = MainActivityPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_options_menu, menu)
        this.menu = menu

        binding.presenter?.setButtonsVisibility()

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.share_menu_item -> {
                shareMenuItemClicked()
                true
            }
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

    private fun shareMenuItemClicked() {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "${binding.presenter?.header}\n${binding.presenter?.note}")
        })
    }

    override fun savedToast() {
        Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
    }

    override fun notSavedToast() {
        Toast.makeText(this, getString(R.string.not_saved), Toast.LENGTH_SHORT).show()
    }

    override fun showShareButton() {
        menu?.findItem(R.id.share_menu_item)?.isVisible = true
    }

    override fun hideShareButton() {
        menu?.findItem(R.id.share_menu_item)?.isVisible = false
    }

    override fun photoButtonClicked() {
        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }

    private fun setPhotoUri() {
        photoUri = FileProvider.getUriForFile(this, "com.example.bschomework.fileprovider", File(filesDir, "temp.jpg"))
    }
}