package com.s0l.movies.adapters.holders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import com.google.android.material.textview.MaterialTextView
import com.s0l.movies.R
import com.s0l.movies.data.Actor

class ActorCardViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private val image = itemView.findViewById<AppCompatImageView>(R.id.ivActor)
    private val name = itemView.findViewById<MaterialTextView>(R.id.tvName)

    fun bind(actor: Actor) {
        image.apply {
            clear()
            load(actor.picture){
                crossfade(true)
                placeholder(R.drawable.ic_baseline_person_24)
            }
        }
        name.text = actor.name
    }
}