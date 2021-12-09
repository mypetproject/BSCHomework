package com.example.bschomework.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bschomework.NoteData
import com.example.bschomework.R
import com.example.bschomework.fragments.NoteDataFragment

class EditNoteActivity : AppCompatActivity(), EditNoteActivityView {

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (supportFragmentManager.findFragmentById(R.id.fragment_note_data) as NoteDataFragment).presenter.setData(
            intent.let { it.getParcelableExtra<NoteData>(REQUEST_KEY) as NoteData })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_edit_note_menu, menu)
        this.menu = menu

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
            putExtra(
                Intent.EXTRA_TEXT,
                (supportFragmentManager.findFragmentById(R.id.fragment_note_data) as NoteDataFragment).presenter.run {
                    "$header\n$note"
                }
            )
        })
    }

    override fun showShareButton() {
        menu?.findItem(R.id.share_menu_item)?.isVisible = true
    }

    override fun hideShareButton() {
        menu?.findItem(R.id.share_menu_item)?.isVisible = false
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
        const val REQUEST_KEY = "noteData"
    }
}