/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xuk.android.ui.renderscript

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.renderscript.*
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import xuk.android.R
import kotlin.math.cos
import kotlin.math.sin

/**
 * https://github.com/android/renderscript-samples/blob/main/RenderScriptIntrinsic/README.md
 */
@Suppress("DEPRECATION")
class RenderScriptActivity : AppCompatActivity() {

  companion object {
    /**
     * Number of bitmaps that is used for renderScript thread and UI thread synchronization.
     */
    private const val NUM_BITMAPS = 2

    private const val MODE_BLUR = 0
    private const val MODE_CONVOLVE = 1
    private const val MODE_COLORMATRIX = 2
  }

  private var mCurrentBitmap = 0
  private var mBitmapIn: Bitmap? = null
  private lateinit var mBitmapsOut: Array<Bitmap?>
  private lateinit var mImageView: ImageView

  private var mRS: RenderScript? = null
  private var mInAllocation: Allocation? = null
  private lateinit var mOutAllocations: Array<Allocation?>

  private lateinit var mScriptBlur: ScriptIntrinsicBlur
  private lateinit var mScriptConvolve: ScriptIntrinsicConvolve5x5
  private lateinit var mScriptMatrix: ScriptIntrinsicColorMatrix
  private var mFilterMode = MODE_BLUR

  private var mLatestTask: RenderScriptTask? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_render_script)

    // Set up main image view
    mBitmapIn = loadBitmap(R.mipmap.ic_share_image)
    mBitmapsOut = arrayOfNulls(NUM_BITMAPS)
    for (i in 0 until NUM_BITMAPS) {
      mBitmapsOut[i] = Bitmap.createBitmap(mBitmapIn!!.width,
          mBitmapIn!!.height, mBitmapIn!!.config)
    }
    mImageView = findViewById(R.id.imageView)
    mImageView.setImageBitmap(mBitmapsOut[mCurrentBitmap])
    mCurrentBitmap += (mCurrentBitmap + 1) % NUM_BITMAPS

    //Set up seekbar
    val seekbar = findViewById<SeekBar>(R.id.seekBar1)
    seekbar.progress = 50
    seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar, progress: Int,
                                     fromUser: Boolean) {
        updateImage(progress)
      }

      override fun onStartTrackingTouch(seekBar: SeekBar) {}
      override fun onStopTrackingTouch(seekBar: SeekBar) {}
    })

    //Setup effect selector
    val radio0 = findViewById<RadioButton>(R.id.radio0)
    radio0.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
      if (isChecked) {
        mFilterMode = MODE_BLUR
        updateImage(seekbar.progress)
      }
    }
    val radio1 = findViewById<RadioButton>(R.id.radio1)
    radio1.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
      if (isChecked) {
        mFilterMode = MODE_CONVOLVE
        updateImage(seekbar.progress)
      }
    }
    val radio2 = findViewById<RadioButton>(R.id.radio2)
    radio2.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
      if (isChecked) {
        mFilterMode = MODE_COLORMATRIX
        updateImage(seekbar.progress)
      }
    }

    // Create renderScript
    createScript()

    // Create thumbnails
    createThumbnail()

    // Invoke renderScript kernel and update imageView
    mFilterMode = MODE_BLUR
    updateImage(50)
  }

  private fun createScript() {
    mRS = RenderScript.create(this)
    mInAllocation = Allocation.createFromBitmap(mRS, mBitmapIn)
    mOutAllocations = arrayOfNulls(NUM_BITMAPS)
    for (i in 0 until NUM_BITMAPS) {
      mOutAllocations[i] = Allocation.createFromBitmap(mRS, mBitmapsOut[i])
    }

    // Create intrinsics.
    // RenderScript has built-in features such as blur, convolve filter etc.
    // These intrinsics are handy for specific operations without writing RenderScript kernel.
    // In the sample, it's creating blur, convolve and matrix intrinsics.
    mScriptBlur = ScriptIntrinsicBlur.create(mRS, Element.U8_4(mRS))
    mScriptConvolve = ScriptIntrinsicConvolve5x5.create(mRS,
        Element.U8_4(mRS))
    mScriptMatrix = ScriptIntrinsicColorMatrix.create(mRS,
        Element.U8_4(mRS))
  }

  private fun performFilter(inAllocation: Allocation?,
                            outAllocation: Allocation?, bitmapOut: Bitmap?, value: Float) {
    when (mFilterMode) {
      MODE_BLUR -> {
        // Set blur kernel size
        mScriptBlur.setRadius(value)

        // Invoke filter kernel
        mScriptBlur.setInput(inAllocation)
        mScriptBlur.forEach(outAllocation)
      }
      MODE_CONVOLVE -> {
        val f2 = 1.0f - value

        // Emboss filter kernel
        val coefficients = floatArrayOf(-value * 2, 0f, -value, 0f, 0f, 0f, -f2 * 2, -f2, 0f, 0f, -value, -f2, 1f, f2, value, 0f, 0f, f2, f2 * 2, 0f, 0f, 0f, value, 0f,
            value * 2)
        // Set kernel parameter
        mScriptConvolve.setCoefficients(coefficients)

        // Invoke filter kernel
        mScriptConvolve.setInput(inAllocation)
        mScriptConvolve.forEach(outAllocation)
      }
      MODE_COLORMATRIX -> {

        // Set HUE rotation matrix
        // The matrix below performs a combined operation of,
        // RGB->HSV transform * HUE rotation * HSV->RGB transform
        val cos = cos(value.toDouble()).toFloat()
        val sin = sin(value.toDouble()).toFloat()
        val mat = Matrix3f()
        mat[0, 0] = (.299 + .701 * cos + .168 * sin).toFloat()
        mat[1, 0] = (.587 - .587 * cos + .330 * sin).toFloat()
        mat[2, 0] = (.114 - .114 * cos - .497 * sin).toFloat()
        mat[0, 1] = (.299 - .299 * cos - .328 * sin).toFloat()
        mat[1, 1] = (.587 + .413 * cos + .035 * sin).toFloat()
        mat[2, 1] = (.114 - .114 * cos + .292 * sin).toFloat()
        mat[0, 2] = (.299 - .3 * cos + 1.25 * sin).toFloat()
        mat[1, 2] = (.587 - .588 * cos - 1.05 * sin).toFloat()
        mat[2, 2] = (.114 + .886 * cos - .203 * sin).toFloat()
        mScriptMatrix.setColorMatrix(mat)

        // Invoke filter kernel
        mScriptMatrix.forEach(inAllocation, outAllocation)
      }
    }

    // Copy to bitmap and invalidate image view
    outAllocation!!.copyTo(bitmapOut)
  }

  /**
   * Convert seekBar progress parameter (0-100 in range) to parameter for each intrinsic filter.
   * (e.g. 1.0-25.0 in Blur filter)
   */
  private fun getFilterParameter(i: Int): Float {
    var f = 0f
    when (mFilterMode) {
      MODE_BLUR -> {
        val max = 25.0f
        val min = 1f
        f = ((max - min) * (i / 100.0) + min).toFloat()
      }
      MODE_CONVOLVE -> {
        val max = 2f
        val min = 0f
        f = ((max - min) * (i / 100.0) + min).toFloat()
      }
      MODE_COLORMATRIX -> {
        val max = Math.PI.toFloat()
        val min = (-Math.PI).toFloat()
        f = ((max - min) * (i / 100.0) + min).toFloat()
      }
    }
    return f
  }

  /**
   * In the AsyncTask, it invokes RenderScript intrinsics to do a filtering.
   *
   *
   * After the filtering is done, an operation blocks at Allocation.copyTo() in AsyncTask
   * thread. Once all operation is finished at onPostExecute() in UI thread, it can invalidate
   * and
   * update ImageView UI.
   */
  @SuppressLint("StaticFieldLeak")
  private inner class RenderScriptTask : AsyncTask<Float?, Int?, Int?>() {
    private var mIssued = false

    override fun doInBackground(vararg params: Float?): Int {
      var index = -1
      if (!isCancelled) {
        mIssued = true
        index = mCurrentBitmap
        performFilter(mInAllocation, mOutAllocations[index], mBitmapsOut[index], params[0]!!)
        mCurrentBitmap = (mCurrentBitmap + 1) % NUM_BITMAPS
      }
      return index
    }

    fun updateView(result: Int?) {
      if (result != -1) {
        // Request UI update
        mImageView.setImageBitmap(mBitmapsOut[result!!])
        mImageView.invalidate()
      }
    }

    override fun onPostExecute(result: Int?) {
      updateView(result)
    }

    override fun onCancelled(result: Int?) {
      if (mIssued) {
        updateView(result)
      }
    }
  }

  /**
   * Invoke AsyncTask and cancel previous task.
   *
   *
   * When AsyncTasks are piled up (typically in slow device with heavy kernel),
   * Only the latest (and already started) task invokes RenderScript operation.
   */
  private fun updateImage(progress: Int) {
    val f = getFilterParameter(progress)
    if (mLatestTask != null) mLatestTask!!.cancel(false)
    mLatestTask = RenderScriptTask()
    mLatestTask?.execute(f)
  }

  /**
   * Helper to load Bitmap from resource
   */
  private fun loadBitmap(resource: Int): Bitmap {
    val options = BitmapFactory.Options()
    options.inPreferredConfig = Bitmap.Config.ARGB_8888
    return BitmapFactory.decodeResource(resources, resource, options)
  }

  /**
   * Create thumbNail for UI. It invokes RenderScript kernel synchronously in UI-thread,
   * which is OK for small thumbnail (but not ideal).
   */
  private fun createThumbnail() {
    val width = 72
    val height = 96
    val scale = resources.displayMetrics.density
    val pixelsWidth = (width * scale + 0.5f).toInt()
    val pixelsHeight = (height * scale + 0.5f).toInt()

    // Temporary image
    val tempBitmap = Bitmap.createScaledBitmap(mBitmapIn!!, pixelsWidth, pixelsHeight, false)
    val inAllocation = Allocation.createFromBitmap(mRS, tempBitmap)

    // Create thumbnail with each RS intrinsic and set it to radio buttons
    val modes = intArrayOf(MODE_BLUR, MODE_CONVOLVE, MODE_COLORMATRIX)
    val ids = intArrayOf(R.id.radio0, R.id.radio1, R.id.radio2)
    val parameter = intArrayOf(50, 100, 25)
    for (mode in modes) {
      mFilterMode = mode
      val f = getFilterParameter(parameter[mode])
      val destBitmap = Bitmap.createBitmap(tempBitmap.width,
          tempBitmap.height, tempBitmap.config)
      val outAllocation = Allocation.createFromBitmap(mRS, destBitmap)
      performFilter(inAllocation, outAllocation, destBitmap, f)
      val button = findViewById<ThumbnailRadioButton>(ids[mode])
      button.setThumbnail(destBitmap)
    }
  }
}