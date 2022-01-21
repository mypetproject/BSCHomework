package com.example.bschomework.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.example.bschomework.R
import com.example.bschomework.databinding.ActivityMainBinding
import com.example.bschomework.fragments.NoteFragment
import com.example.bschomework.fragments.NotesListFragment
import com.example.bschomework.fragments.SaveAlertDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        setSearchViewListener(menu)

        return super.onCreateOptionsMenu(menu)
    }

    private fun setSearchViewListener(menu: Menu) {
        menu.findItem(R.id.search_menu_item).run {

            (actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    (supportFragmentManager.findFragmentById(R.id.fragment_container) as NotesListFragment)
                        .filter(newText as String)
                    return false
                }
            })

            setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                    invalidateOptionsMenu()
                    return true
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.save_menu_item -> {
                saveMenuButtonClicked()
                true
            }
            R.id.add_menu_item -> {
                hideAddMenuItem()
                hideSearchMenuItem()
                hideLocationMenuItem()
                addMenuItemClicked()
                true
            }
            R.id.about_menu_item -> {
                aboutMenuItemClicked()
                true
            }
            R.id.search_menu_item -> {
                hideAddMenuItem()
                hideSearchMenuItem()
                true
            }
            R.id.location_menu_item -> {
                locationMenuItemClicked()
                true
            }
            R.id.web_menu_item -> {
                webMenuItemClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun locationMenuItemClicked() {
        (supportFragmentManager.findFragmentById(R.id.fragment_container) as NotesListFragment).getLocation()
    }

    private fun saveMenuButtonClicked() {
        SaveAlertDialogFragment().show(supportFragmentManager, SaveAlertDialogFragment.TAG)
    }

    override fun saveAlertDialogOKButtonClicked() {
        (supportFragmentManager.findFragmentById(R.id.fragment_container) as NoteFragment).save()
    }

    private fun addMenuItemClicked() {
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragment_container, NoteFragment())
            addToBackStack(null)
            commit()
        }
    }

    private fun aboutMenuItemClicked() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    private fun webMenuItemClicked() {
        startActivity(Intent(this, WebViewActivity::class.java))
    }

    private fun hideAddMenuItem() {
        menu?.findItem(R.id.add_menu_item)?.isVisible = false
    }

    private fun hideSearchMenuItem() {
        menu?.findItem(R.id.search_menu_item)?.isVisible = false
    }

    private fun hideLocationMenuItem() {
        menu?.findItem(R.id.location_menu_item)?.isVisible = false
    }

    override fun hideSaveMenuItem() {
        menu?.findItem(R.id.save_menu_item)?.isVisible = false
    }

    override fun showSaveMenuItem() {
        menu?.findItem(R.id.save_menu_item)?.isVisible = true
    }
}