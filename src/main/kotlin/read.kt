package me.serce.fantastic.impl

import clojure.lang.APersistentMap as PMap
import clojure.lang.PersistentHashMap as PHashMap

interface Read<M> {
  val path: Path
  val domain: Domain<M>
}

class ReadFocus<T>(val p: Path, val dm: Domain<T>) : Focus<Read<T>> {
  override fun narrow(k: String): Focus<Read<T>> = ReadFocus(p.append(k), dm)

  override val op: Read<T> = object : Read<T> {
    override val domain: Domain<T> = dm
    override val path: Path = p
  }
}

inline val <reified V, T> Cursor<Leaf<V>, Read<T>>.value: V
  get() = f.op.path.getIn(f.op.domain.root) as V


class Domain<out T>(val root: PMap = PHashMap.EMPTY) {
  override fun toString() = root.toString()
}

val <T> Domain<T>.cursor: Cursor<T, Read<T>>
  get() = Cursor(ReadFocus(Path.EMPTY, this))
