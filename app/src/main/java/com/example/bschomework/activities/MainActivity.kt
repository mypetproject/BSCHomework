package com.example.bschomework.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.bschomework.R
import com.example.bschomework.databinding.ActivityMainBinding
import com.example.bschomework.fragments.CreateNoteFragment
import com.example.bschomework.fragments.SaveAlertDialogFragment

class MainActivity : AppCompatActivity(), MainActivityView {

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).run {
            setSupportActionBar(toolbar)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_options_menu, menu)
        this.menu = menu

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.save_menu_item -> {
                saveMenuButtonClicked()
                true
            }
            R.id.add_menu_item -> {
                addMenuItemClicked()
                true
            }
            R.id.about_menu_item -> {
                aboutMenuItemClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveMenuButtonClicked() {
        SaveAlertDialogFragment().show(supportFragmentManager, SaveAlertDialogFragment.TAG)
    }

    override fun saveAlertDialogOKButtonClicked() {
        (supportFragmentManager.findFragmentById(R.id.fragment_container) as CreateNoteFragment).save()
    }

    private fun addMenuItemClicked() {
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragment_container, CreateNoteFragment())
            addToBackStack(null)
            commit()
        }
    }

    private fun aboutMenuItemClicked() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    override fun hideAddMenuItem() {
        menu?.findItem(R.id.add_menu_item)?.isVisible = false
    }

    override fun showAddMenuItem() {
        menu?.findItem(R.id.add_menu_item)?.isVisible = true
    }

    override fun hideSaveMenuItem() {
        menu?.findItem(R.id.save_menu_item)?.isVisible = false
    }

    override fun showSaveMenuItem() {
        menu?.findItem(R.id.save_menu_item)?.isVisible = true
    }
}