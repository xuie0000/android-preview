package com.xuie.android.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

/**
 * @author xuie
 */
class ClearableEditText : AppCompatEditText, View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

  private var mClearTextIcon: Drawable? = null
  private var mOnFocusChangeListener: View.OnFocusChangeListener? = null
  private var mOnTouchListener: View.OnTouchListener? = null

  constructor(context: Context) : super(context) {
    init(context)
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    init(context)
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    init(context)
  }

  override fun setOnFocusChangeListener(onFocusChangeListener: View.OnFocusChangeListener) {
    mOnFocusChangeListener = onFocusChangeListener
  }

  override fun setOnTouchListener(onTouchListener: View.OnTouchListener) {
    mOnTouchListener = onTouchListener
  }

  private fun init(context: Context) {
    val drawable = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_close_clear_cancel)
    //Wrap the drawable so that it can be tinted pre Lollipop
    val wrappedDrawable = DrawableCompat.wrap(drawable!!)
    DrawableCompat.setTint(wrappedDrawable, currentHintTextColor)
    mClearTextIcon = wrappedDrawable
    mClearTextIcon!!.setBounds(0, 0, mClearTextIcon!!.intrinsicHeight, mClearTextIcon!!.intrinsicHeight)
    setClearIconVisible(false)
    super.setOnTouchListener(this)
    super.setOnFocusChangeListener(this)
    addTextChangedListener(this)
  }


  override fun onFocusChange(view: View, hasFocus: Boolean) {
    if (hasFocus) {
      setClearIconVisible(text!!.length > 0)
    } else {
      setClearIconVisible(false)
    }
    if (mOnFocusChangeListener != null) {
      mOnFocusChangeListener!!.onFocusChange(view, hasFocus)
    }
  }

  override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
    val x = motionEvent.x.toInt()
    if (mClearTextIcon!!.isVisible && x > width - paddingRight - mClearTextIcon!!.intrinsicWidth) {
      if (motionEvent.action == MotionEvent.ACTION_UP) {
        error = null
        setText("")
      }
      return true
    }
    return mOnTouchListener != null && mOnTouchListener!!.onTouch(view, motionEvent)
  }

  override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    if (isFocused) {
      setClearIconVisible(s.length > 0)
    }
  }

  override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

  override fun afterTextChanged(s: Editable) {}


  private fun setClearIconVisible(visible: Boolean) {
    mClearTextIcon!!.setVisible(visible, false)
    val compoundDrawables = compoundDrawables
    setCompoundDrawables(
        compoundDrawables[0],
        compoundDrawables[1],
        if (visible) mClearTextIcon else null,
        compoundDrawables[3])
  }
}