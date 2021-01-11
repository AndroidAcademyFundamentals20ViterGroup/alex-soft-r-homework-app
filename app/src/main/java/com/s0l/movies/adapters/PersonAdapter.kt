package com.s0l.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s0l.movies.R
import com.s0l.movies.adapters.holders.PersonCardViewHolder
import com.s0l.movies.models.entity.Person
import com.s0l.movies.utils.getScreenWidth


class PersonAdapter : RecyclerView.Adapter<PersonCardViewHolder>() {

    companion object {
        const val NUMBERS_OF_ITEM_TO_DISPLAY = 4
    }

    private var persons = mutableListOf<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonCardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_person, parent, false)

        val offset = itemView.context.resources.getDimension(R.dimen.margin_16dp)
            .toInt() * 2//size and count of one Actor card

        val decorator = itemView.context.resources.getDimension(R.dimen.decorator_actor)
            .toInt() * 3//size and count of decorators

        itemView.layoutParams.width =
            (itemView.context.getScreenWidth() - offset - decorator) / NUMBERS_OF_ITEM_TO_DISPLAY

        return PersonCardViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: PersonCardViewHolder, position: Int) {
        holder.setIsRecyclable(true)
        holder.bind(person = persons[position])
    }

    override fun getItemCount(): Int = persons.size

    fun setUpPerson(list: List<Person>) {
        persons.addAll(list)
        notifyDataSetChanged()
    }

    fun addPerson(movie: Person) {
        persons.add(movie)
        notifyDataSetChanged()
    }

    fun addPerson(movie: Person, position: Int) {
        persons.add(position, movie)
        notifyItemInserted(position)
    }
}
