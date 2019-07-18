package xuk.android.ui.recycler.diffutil

/**
 * @author xuie
 */
class Actor(val id: Int, val name: String?, val rating: Int, val yearOfBirth: Int) {

  override fun equals(o: Any?): Boolean {
    if (this === o) {
      return true
    }
    if (o == null || javaClass != o.javaClass) {
      return false
    }

    val actor = o as Actor?

    if (id != actor!!.id) {
      return false
    }
    return if (rating != actor.rating) {
      false
    } else yearOfBirth == actor.yearOfBirth && if (name != null) name == actor.name else actor.name == null

  }

  override fun hashCode(): Int {
    var result = id
    result = 31 * result + (name?.hashCode() ?: 0)
    result = 31 * result + rating
    result = 31 * result + yearOfBirth
    return result
  }
}
