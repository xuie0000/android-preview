package com.xuie.android.ui.main

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.xuie.android.R
import com.xuie.android.util.BlurBitmap

/**
 * @author xuie
 */
class TransitionsObjectActivity : AppCompatActivity() {

  private var bluredImg: ImageView? = null
  private var originImg: ImageView? = null
  private var seekBar: SeekBar? = null
  private var tvProgress: TextView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_transitions_object)
    bluredImg = findViewById(R.id.blured_img)
    originImg = findViewById(R.id.origin_img)
    seekBar = findViewById(R.id.seek_bar)
    tvProgress = findViewById(R.id.progress)
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    val tempBitmap = BitmapFactory.decodeResource(resources, R.mipmap.image_big)
    val finalBitmap = BlurBitmap.blur(this, tempBitmap)

    bluredImg!!.setImageBitmap(finalBitmap)
    originImg!!.setImageBitmap(tempBitmap)

    setSeekBar()
  }

  /**
   * 处理seekBar滑动事件
   */
  private fun setSeekBar() {
    seekBar!!.max = 100
    seekBar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        originImg!!.imageAlpha = (255 - progress * 2.55).toInt()
        tvProgress!!.text = progress.toString()
      }

      override fun onStartTrackingTouch(seekBar: SeekBar) {

      }

      override fun onStopTrackingTouch(seekBar: SeekBar) {

      }
    })
  }

  override fun onResume() {
    super.onResume()
    val decorView = window.decorView
    // Hide the status bar.
    val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    // or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    // or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    // or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    // or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    decorView.systemUiVisibility = uiOptions
  }

}
