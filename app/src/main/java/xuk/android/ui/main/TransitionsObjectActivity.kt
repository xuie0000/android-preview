package xuk.android.ui.main

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_transitions_object.*
import xuk.android.R
import xuk.android.util.BlurBitmap

class TransitionsObjectActivity : AppCompatActivity(R.layout.activity_transitions_object) {

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    val tempBitmap = BitmapFactory.decodeResource(resources, R.mipmap.image_big)
    val finalBitmap = BlurBitmap.blur(this, tempBitmap)

    blured_img.setImageBitmap(finalBitmap)
    origin_img.setImageBitmap(tempBitmap)

    setSeekBar()
  }

  /**
   * 处理seekBar滑动事件
   */
  private fun setSeekBar() {
    seek_bar.max = 100
    seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        origin_img.imageAlpha = (255 - progress * 2.55).toInt()
        tv_progress.text = progress.toString()
      }

      override fun onStartTrackingTouch(seekBar: SeekBar) {

      }

      override fun onStopTrackingTouch(seekBar: SeekBar) {

      }
    })
  }

  @Suppress("DEPRECATION")
  override fun onResume() {
    super.onResume()
    val decorView: View = window.decorView
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      decorView.windowInsetsController?.show(WindowInsets.Type.systemBars())
    } else {
      val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_VISIBLE
      decorView.systemUiVisibility = option
    }
  }

}
