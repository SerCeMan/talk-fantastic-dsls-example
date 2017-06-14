package me.serce.fantastic.impl

import clojure.`core$assoc_in` as assocIn
import clojure.`core$get_in` as getIn
import clojure.lang.*

data class Path(private val v: APersistentVector) {
  companion object {
    val EMPTY = Path(PersistentVector.EMPTY)
  }

  fun append(a: String): Path = Path(v.cons(a) as APersistentVector)
  fun getIn(model: Any?): Any? = getIn.invokeStatic(model, v)
  fun assocIn(m: Any?, a: Any?) = assocIn.invokeStatic(m, v, a) as APersistentMap
}
