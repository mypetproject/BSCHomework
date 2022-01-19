package com.example.bschomework

import android.content.Context
import android.text.Html
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.HtmlCompat

class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var htmlText: String? = null
        set(value) {
            field = value
            text = value?.let { Html.fromHtml(value, HtmlCompat.FROM_HTML_MODE_LEGACY) }
        }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomTextView, defStyleAttr, 0)
            .also { typedArray ->
                htmlText = typedArray.getString(R.styleable.CustomTextView_htmlText)
            }.recycle()
    }
}