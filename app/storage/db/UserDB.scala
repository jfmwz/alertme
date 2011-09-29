package storage.db

import java.sql.Timestamp
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column


class User(val id: Long, val name: String) {

  def this() = this(0L, "")

  def getId(): Long = {
    id
  }

  def getName(): String = {
    name
  }

}


object UserDB extends Schema {
   import org.squeryl.PrimitiveTypeMode._

   val users = table[User]("t_user")

 }
