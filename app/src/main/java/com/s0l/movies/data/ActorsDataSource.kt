package com.s0l.movies.data

import com.s0l.movies.R
import com.s0l.movies.models.ActorInfo

class ActorsDataSource {
    fun getActors(): ArrayList<ActorInfo> = arrayListOf(
        ActorInfo(1, "Robert Downey Jr.", R.drawable.downey_jr),
        ActorInfo(2, "Chris Evans", R.drawable.evans_c),
        ActorInfo(3, "Mark Ruffalo", R.drawable.ruffalo_m),
        ActorInfo(4, "Chris Hemsworth", R.drawable.hemswoth_c),
        ActorInfo(5, "Robert Downey Jr.", R.drawable.downey_jr),
        ActorInfo(6, "Chris Evans", R.drawable.evans_c),
        ActorInfo(7, "Mark Ruffalo", R.drawable.ruffalo_m),
        ActorInfo(8, "Chris Hemsworth", R.drawable.hemswoth_c)
    )
}