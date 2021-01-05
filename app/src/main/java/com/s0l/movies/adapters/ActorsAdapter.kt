package com.s0l.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s0l.movies.R
import com.s0l.movies.adapters.holders.ActorCardViewHolder
import com.s0l.movies.data.Actor
import com.s0l.movies.utils.getScreenWidth


class ActorsAdapter : RecyclerView.Adapter<ActorCardViewHolder>() {

    companion object {
        const val NUMBERS_OF_ITEM_TO_DISPLAY = 4
    }

    private var actors = mutableListOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorCardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_actor, parent, false)

        val offset = itemView.context.resources.getDimension(R.dimen.margin_16dp)
            .toInt() * 2//size and count of one Actor card

        val decorator = itemView.context.resources.getDimension(R.dimen.decorator_actor)
            .toInt() * 3//size and count of decorators

        itemView.layoutParams.width =
            (itemView.context.getScreenWidth() - offset - decorator) / NUMBERS_OF_ITEM_TO_DISPLAY

        return ActorCardViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: ActorCardViewHolder, position: Int) {
        holder.setIsRecyclable(true)
        holder.bind(actor = actors[position])
    }

    override fun getItemCount(): Int = actors.size

    fun setUpActors(list: List<Actor>) {
        actors = list as MutableList<Actor>
        notifyDataSetChanged()
    }

    fun addActor(movie: Actor) {
        actors.add(movie)
        notifyDataSetChanged()
    }

    fun addActor(movie: Actor, position: Int) {
        actors.add(position, movie)
        notifyItemInserted(position)
    }
}
