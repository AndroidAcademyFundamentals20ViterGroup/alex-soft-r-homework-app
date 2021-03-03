package com.s0l.movies.ui.adapters.holders

import android.graphics.text.LineBreaker
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import com.google.android.material.textview.MaterialTextView
import com.s0l.movies.R
import com.s0l.movies.api.Api
import com.s0l.movies.data.model.entity.ActorEntity

class ActorCardViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private val image = itemView.findViewById<AppCompatImageView>(R.id.ivActor)
    private val name = itemView.findViewById<MaterialTextView>(R.id.tvName)
    private val job = itemView.findViewById<MaterialTextView>(R.id.tvJob)

    fun bind(person: ActorEntity) {
        person.profile_path?.let {
            image.apply {
                clear()
                load(Api.getProfilePath(it)) {
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_person_24)
                }
            }
        } ?: image.apply {
            clear()
            load(R.drawable.ic_baseline_person_24)
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            name.breakStrategy = LineBreaker.BREAK_STRATEGY_BALANCED
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            job.breakStrategy = LineBreaker.BREAK_STRATEGY_BALANCED
        }
        name.text = person.name
        if (person.character.isNotEmpty())
            job.text = person.character
    }
}