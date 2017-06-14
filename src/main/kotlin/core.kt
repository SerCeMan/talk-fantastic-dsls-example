package me.serce.fantastic.impl

import kotlin.reflect.KProperty

interface Focus<Op> {
  fun narrow(k: String): Focus<Op>
  val op: Op
}

class Cursor<T, Op>(val f: Focus<Op>)

class Node<T> {
  operator fun <Op> getValue(ref: Cursor<*, Op>, property: KProperty<*>): Cursor<T, Op> {
    return Cursor(ref.f.narrow(property.name))
  }
}

class Leaf<V> {
  operator fun <Op> getValue(ref: Cursor<*, Op>, property: KProperty<*>): Cursor<Leaf<V>, Op> {
    return Cursor(ref.f.narrow(property.name))
  }
}
