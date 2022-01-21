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

                    animate(customTextview, mainLayout)
                }
    }

    private fun animate(customTextView: View, mainLayout: View) {

        val rotateTextAnimation = ObjectAnimator.ofFloat(
            customTextView,
            View.ROTATION, ROTATION_FROM, ROTATION_TO
        ).apply {
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = REPEAT_COUNT
            repeatMode = REVERSE
        }

        val backgroundColorAnimation = ObjectAnimator.ofObject(
            mainLayout,
            "backgroundColor",
            ArgbEvaluator(),
            ContextCompat.getColor(this@AboutActivity, R.color.white),
            ContextCompat.getColor(this@AboutActivity, R.color.purple_200)
        ).apply {
            repeatCount = REPEAT_COUNT
            repeatMode = REVERSE
        }

        AnimatorSet().run {
            play(rotateTextAnimation).with(backgroundColorAnimation)
            duration = DURATION
            start()
        }
    }

    companion object {
        private const val ROTATION_FROM = 0f
        private const val ROTATION_TO = 3600f
        private const val REPEAT_COUNT = 1
        private const val DURATION = 5000L
    }
}