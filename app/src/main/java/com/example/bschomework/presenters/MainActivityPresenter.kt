package com.example.bschomework.presenters

import com.example.bschomework.activities.MainActivityView

class MainActivityPresenter(private val view: MainActivityView) {

    fun photoButtonClicked() {
        view.photoButtonClicked()
    }
}