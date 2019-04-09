package com.xuie.android.ui.recycler.diffutil

import java.util.ArrayList
import java.util.Collections

/**
 * @author xuie
 */
object ActorRepository {

  private val originalActorList: List<Actor>
    get() {
      val actors = ArrayList<Actor>()

      actors.add(Actor(1, "Jack Nicholson", 10, 1937))
      actors.add(Actor(2, "Marlon Brando", 9, 1924))
      actors.add(Actor(3, "Robert De Niro", 8, 1943))
      actors.add(Actor(4, "Al Pacino", 7, 1940))
      actors.add(Actor(5, "Daniel Day-Lewis", 6, 1957))
      actors.add(Actor(6, "Dustin Hoffman", 5, 1937))
      actors.add(Actor(7, "Tom Hanks", 4, 1956))
      actors.add(Actor(8, "Anthony Hopkins", 3, 1937))
      actors.add(Actor(9, "Paul Newman", 2, 1925))
      actors.add(Actor(10, "Denzel Washington", 1, 1954))

      return actors
    }

  // Descending order
  val actorListSortedByRating: List<Actor>
    get() {
      val actorList = originalActorList

      Collections.sort(actorList) { a1, a2 -> a2.rating - a1.rating }

      return actorList
    }

  val actorListSortedByName: List<Actor>
    get() {
      val actorList = originalActorList

      Collections.sort(actorList) { a1, a2 -> a1.name!!.compareTo(a2.name!!) }
      return actorList
    }

  val actorListSortedByYearOfBirth: List<Actor>
    get() {
      val actorList = originalActorList

      Collections.sort(actorList) { a1, a2 -> a1.yearOfBirth - a2.yearOfBirth }
      return actorList
    }
}// nop
