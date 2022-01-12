package com.wincdspro.app.util

import android.text.Editable
import android.text.TextWatcher

class TextWatcherImpl(
    val beforeTextChanged: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit?)? = null,
    val onTextChanged: ((s: CharSequence?, start: Int, before: Int, count: Int) -> Unit?)? = null,
    val afterTextChanged: ((s: Editable) -> Unit?)? = null,
) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeTextChanged?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged?.invoke(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable) {
        afterTextChanged?.invoke(s)
    }
}
