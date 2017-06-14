package me.serce.fantastic

import me.serce.fantastic.impl.*


interface Transaction
val <F> Cursor<Transaction, F>.payment by Node<Payment>()
val <F> Cursor<Transaction, F>.parts by Node<Parts>()

interface Payment
val <F> Cursor<Payment, F>.currency by Leaf<String>()
val <F> Cursor<Payment, F>.amount by Leaf<Int>()

interface Parts
val <F> Cursor<Parts, F>.to by Node<Person>()
val <F> Cursor<Parts, F>.from by Node<Person>()

interface Person
val <F> Cursor<Person, F>.id by Leaf<Int>()
val <F> Cursor<Person, F>.name by Leaf<String>()


fun main(args: Array<String>) {
  val trans = domain<Transaction> {
    (payment) {
      currency.set("AUD")
      amount.set(12)
    }
    (parts) {
      (from) {
        id.set(0)
        name.set("alex")
      }
      (to) {
        id.set(1)
        name.set("ben")
      }
    }
  }

  assertEquals("alex", trans.cursor.parts.from.name.value)


  val trans2 = trans.cursor.parts.from.update {
    name.set("john")
  }

  assertEquals("alex", trans.cursor.parts.from.name.value)
  assertEquals("john", trans2.cursor.parts.from.name.value)
}

fun assertEquals(a: Any, b: Any) {
  if (a != b) {
    throw AssertionError()
  }
}
