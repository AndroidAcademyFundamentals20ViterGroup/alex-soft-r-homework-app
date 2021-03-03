package com.s0l.movies.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s0l.movies.R
import com.s0l.movies.data.model.entity.CrewEntity
import com.s0l.movies.ui.adapters.holders.CrewCardViewHolder
import com.s0l.movies.utils.getScreenWidth


class CrewAdapter : RecyclerView.Adapter<CrewCardViewHolder>() {

    companion object {
        const val NUMBERS_OF_ITEM_TO_DISPLAY = 4
    }

    private var persons = mutableListOf<CrewEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewCardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_person, parent, false)

        val offset = itemView.context.resources.getDimension(R.dimen.margin_16dp)
            .toInt() * 2//size and count of one Actor card

        val decorator = itemView.context.resources.getDimension(R.dimen.decorator_actor)
            .toInt() * 3//size and count of decorators

        itemView.layoutParams.width =
            (itemView.context.getScreenWidth() - offset - decorator) / NUMBERS_OF_ITEM_TO_DISPLAY

        return CrewCardViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: CrewCardViewHolder, position: Int) {
        holder.setIsRecyclable(true)
        holder.bind(person = persons[position])
    }

    override fun getItemCount(): Int = persons.size

    fun setUpPerson(list: List<CrewEntity>) {
        persons.addAll(list)
        notifyDataSetChanged()
    }

    fun addPerson(movie: CrewEntity) {
        persons.add(movie)
        notifyDataSetChanged()
    }

    fun addPerson(movie: CrewEntity, position: Int) {
        persons.add(position, movie)
        notifyItemInserted(position)
    }
}
