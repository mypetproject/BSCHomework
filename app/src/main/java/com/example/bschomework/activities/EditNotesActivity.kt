package com.example.bschomework.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.bschomework.R
import com.example.bschomework.adapters.NotesListViewPagerAdapter
import com.example.bschomework.databinding.ActivityEditNotesBinding
import com.example.bschomework.fragments.NoteDataFragment
import com.example.bschomework.room.NoteData
import com.example.bschomework.viewModels.NotesListViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class EditNotesActivity : AppCompatActivity(), EditNotesActivityView {

    private var menu: Menu? = null

    private lateinit var binding: ActivityEditNotesBinding

    private val adapter by lazy { NotesListViewPagerAdapter(this) }

    private val model : NotesListViewModel by viewModels()

    private var setCurrentPagerPosition = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_notes)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = DataBindingUtil.setContentView<ActivityEditNotesBinding>(
            this,
            R.layout.activity_edit_notes
        ).apply {
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }

            setSupportActionBar(toolbar)

            pager.adapter =
                adapter.also {
                    model.notes.observe(this@EditNotesActivity, { notes ->
                        it.items = notes
                        if (setCurrentPagerPosition) setPagerCurrentItem(notes)
                    })
                }
        }
    }

    private fun setPagerCurrentItem(notes: List<NoteData>) = lifecycleScope.launch {
        binding.pager.setCurrentItem(
            notes.indexOf(
                model.getNoteById(
                    intent.getLongExtra(EXTRA_LONG, 0L)
                )
            ), false
        )
        setCurrentPagerPosition = false
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
                (adapter.fragments[binding.pager.currentItem] as NoteDataFragment).save()
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
                (adapter.fragments[binding.pager.currentItem] as NoteDataFragment).getTextForShare()
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_LONG = "extra_long"
    }
}