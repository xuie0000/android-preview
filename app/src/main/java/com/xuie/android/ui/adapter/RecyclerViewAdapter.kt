package com.xuie.android.ui.adapter

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import com.orhanobut.logger.Logger
import com.xuie.android.R

/**
 * @author xuie
 */
open class RecyclerViewAdapter(internal var textPictures: List<TextColor>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_text_row, viewGroup, false)
    return RecyclerViewHolder(v)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val textPicture = textPictures[position]
    val vh = holder as RecyclerViewHolder
    vh.card.setCardBackgroundColor(textPicture.color)
    vh.title.text = textPicture.text
    startAnimation(vh.itemView)
  }

  protected fun startAnimation(view: View) {
    val animators = arrayOf<Animator>(ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1), ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1))

    for (anim in animators) {
      anim.setDuration(300).start()
    }
  }

  override fun getItemCount(): Int {
    return textPictures.size
  }

  internal inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var card: CardView
    var title: TextView

    init {
      card = itemView as CardView
      title = itemView.findViewById(R.id.title)
      title.setOnClickListener { v -> Logger.d("Element $adapterPosition clicked.") }
    }
  }

}
