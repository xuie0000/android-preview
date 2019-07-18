package xuk.android.ui.recycler.diffutil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import xuk.android.R

import java.util.ArrayList

/**
 * @author xuie
 */
class ActorAdapter(personList: List<Actor>) : RecyclerView.Adapter<ActorAdapter.ViewHolder>() {

  private val actors = ArrayList<Actor>()

  init {
    this.actors.addAll(personList)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.item_actor, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val actor = actors[position]
    holder.name.text = actor.name
  }

  fun swapItems(actors: List<Actor>) {
    val diffCallback = ActorDiffCallback(this.actors, actors)
    val diffResult = DiffUtil.calculateDiff(diffCallback)

    this.actors.clear()
    this.actors.addAll(actors)
    diffResult.dispatchUpdatesTo(this)
  }

  override fun getItemCount(): Int {
    return actors.size
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.actor_name)
  }
}
