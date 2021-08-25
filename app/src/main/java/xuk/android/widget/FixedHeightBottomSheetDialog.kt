/*
 * Copyright (c) 2021 Jie Xu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xuk.android.widget

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * https://www.jianshu.com/p/c339dd2e9ef8
 */
class FixedHeightBottomSheetDialog(
  context: Context, theme: Int, private val fixedHeight: Int,
) : BottomSheetDialog(context, theme) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setPeekHeight(fixedHeight)
    setMaxHeight(fixedHeight)
  }

  private fun setPeekHeight(peekHeight: Int) {
    if (peekHeight <= 0) {
      return
    }
    val bottomSheetBehavior = getBottomSheetBehavior(peekHeight)
    bottomSheetBehavior?.peekHeight = peekHeight
  }

  private fun setMaxHeight(maxHeight: Int) {
    if (maxHeight <= 0) {
      return
    }
    window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, maxHeight)
    window?.setGravity(Gravity.BOTTOM)
  }

  private fun getBottomSheetBehavior(peekHeight: Int): BottomSheetBehavior<FrameLayout>? {
    val view: FrameLayout? =
      window?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
    return view?.let {
      it.getChildAt(0).layoutParams =
        FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, peekHeight)
      BottomSheetBehavior.from(it)
    }
  }
}