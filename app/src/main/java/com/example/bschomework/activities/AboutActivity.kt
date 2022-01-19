package com.example.bschomework.activities

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.REVERSE
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.bschomework.R
import com.example.bschomework.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        binding =
            DataBindingUtil.setContentView<ActivityAboutBinding>(this, R.layout.activity_about)
                .apply {
                    toolbar.setNavigationOnClickListener {
                        onBackPressed()
                    }
                }

        animate()
    }

    private fun animate() {

        val rotateTextAnimation = ObjectAnimator.ofFloat(
            binding.customTextview,
            View.ROTATION, 0f, 3600f
        ).apply {
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = 1
            repeatMode = REVERSE
        }

        val backgroundColorAnimation = ObjectAnimator.ofObject(
            binding.mainLayout,
            "backgroundColor",
            ArgbEvaluator(),
            ContextCompat.getColor(this@AboutActivity, R.color.white),
            ContextCompat.getColor(this@AboutActivity, R.color.purple_200)
        ).apply {
            repeatCount = 1
            repeatMode = REVERSE
        }

        AnimatorSet().run {
            play(rotateTextAnimation).with(backgroundColorAnimation)
            duration = 5000
            start()
        }
    }
}