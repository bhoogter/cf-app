package com.wincdspro.app.tools

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton

class Zoom(context: Context) : View(context) {
    private val image: Drawable
    var img: ImageButton? = null
    var img1: ImageButton? = null
    private var zoomControler = 20

    init {
        image = context.resources.getDrawable(R.drawable.btn_dropdown)
        // image=context.getResources().getDrawable(R.drawable.icon);
        isFocusable = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // here u can control the width and height of the images........ this line is very important
        image.setBounds(width / 2 - zoomControler, height / 2 - zoomControler, width / 2 + zoomControler, height / 2 + zoomControler)
        image.draw(canvas)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) zoomControler += 10 // zoom in
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) zoomControler -= 10 // zoom out
        if (zoomControler < 10) zoomControler = 10
        invalidate()
        return true
    }
}
