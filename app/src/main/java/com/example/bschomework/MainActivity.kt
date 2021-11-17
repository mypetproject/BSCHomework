package com.example.bschomework

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.bschomework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainActivityPresenter.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        val presenter = MainActivityPresenter(this)

        binding.saveButton.setOnClickListener {

            if (binding.headerText == null) binding.headerText = ""
            if (binding.notesText == null) binding.notesText = ""

            presenter.saveData(binding.headerText!!.trim(), binding.notesText!!.trim())
        }
    }

    override fun savedToast() {
        Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
    }

    override fun notSavedToast() {
        Toast.makeText(this, getString(R.string.not_saved), Toast.LENGTH_SHORT).show()
    }
}