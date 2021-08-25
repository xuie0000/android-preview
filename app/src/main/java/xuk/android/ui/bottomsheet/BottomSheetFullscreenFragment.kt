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

package xuk.android.ui.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import xuk.android.R
import xuk.android.databinding.FragmentBottomSheetFullscreenBinding
import xuk.android.widget.FixedHeightBottomSheetDialog

class BottomSheetFullscreenFragment : BottomSheetDialogFragment() {

  private lateinit var binding: FragmentBottomSheetFullscreenBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NORMAL, R.style.TransBottomSheetDialogStyle)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
  ): View {
    binding = FragmentBottomSheetFullscreenBinding.inflate(inflater)
    return binding.root
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return FixedHeightBottomSheetDialog(
      requireContext(),
      theme, resources.displayMetrics.heightPixels
    )
  }

}