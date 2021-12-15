package com.example.bschomework.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.bschomework.R
import com.example.bschomework.adapters.NotesListViewPagerAdapter
import com.example.bschomework.databinding.ActivityEditNotesBinding
import com.example.bschomework.fragments.NoteDataFragment
import com.example.bschomework.presenters.EditNotesActivityPresenter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class EditNotesActivity : AppCompatActivity(), EditNotesActivityView {

    private var menu: Menu? = null

    private lateinit var binding: ActivityEditNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_notes)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val presenter = EditNotesActivityPresenter(this)

        binding = DataBindingUtil.setContentView<ActivityEditNotesBinding>(
            this,
            R.layout.activity_edit_notes
        ).apply {
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }

            setSupportActionBar(toolbar)

            lifecycleScope.launch {
                pager.adapter =
                    NotesListViewPagerAdapter(this@EditNotesActivity).also {
                        it.items = presenter.getNotes()
                    }

                presenter.setPagerCurrentItem(intent)
            }
        }
    }

    override fun setPagerCurrentItem(position: Int) {
        binding.pager.setCurrentItem(position, false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_edit_note_menu, menu)
        this.menu = menu

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.save_menu_item -> {
                saveMenuItemClicked()
                true
            }
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

    private fun saveMenuItemClicked() {

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.question_save_note))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->

                (supportFragmentManager.findFragmentByTag(VIEW_PAGER_DEFAULT_FRAGMENT_NAME + binding.pager.currentItem) as NoteDataFragment).run {
                    presenter.saveData()
                }
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            .show()
    }

    private fun aboutMenuItemClicked() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    private fun shareMenuItemClicked() {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                (supportFragmentManager.findFragmentByTag(VIEW_PAGER_DEFAULT_FRAGMENT_NAME + binding.pager.currentItem) as NoteDataFragment).presenter.run {
                    "$header\n$note"
                }
            )
        })
    }

    override fun showButtons() {
        menu?.run {
            findItem(R.id.share_menu_item)?.isVisible = true
            findItem(R.id.save_menu_item)?.isVisible = true
        }
    }

    override fun hideButtons() {
        menu?.run {
            findItem(R.id.share_menu_item)?.isVisible = false
            findItem(R.id.save_menu_item)?.isVisible = false
        }
    }

    override fun savedToast() {
        Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
    }

    override fun notSavedToast() {
        Toast.makeText(this, getString(R.string.not_saved), Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_LONG = "extra_long"
        const val VIEW_PAGER_DEFAULT_FRAGMENT_NAME = "f"
    }
}