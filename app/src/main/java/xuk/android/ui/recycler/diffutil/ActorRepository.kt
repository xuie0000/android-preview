package xuk.android.ui.recycler.diffutil

import java.util.*

/**
 * @author Jie Xu
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
      return originalActorList.sortedBy { it.rating }
    }

  val actorListSortedByName: List<Actor>
    get() {
      return originalActorList.sortedBy { it.name }
    }

  val actorListSortedByYearOfBirth: List<Actor>
    get() {
      return originalActorList.sortedBy { it.yearOfBirth }
    }
}// nop
