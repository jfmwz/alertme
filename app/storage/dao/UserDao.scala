package storage.db

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl._
import storage.dbpool.C3P0Database


class UserDao {

  def createUser(id: Long, name: String): User = {
    C3P0Database.startDatabaseSession()
    transaction {
      val user = UserDB.users.insert(new User(id,name))
      user
    }
  }


  def getUser(name: String): User = {
    C3P0Database.startDatabaseSession()
    transaction {
      val user = from(UserDB.users)(user=> where(user.name === name) select(user))
      user.single
    }
  }


  def setUser(user: User) = {
    C3P0Database.startDatabaseSession()
    transaction {
      update(UserDB.users)(u =>
        where(u.id === user.id)
        set(u.name := user.name)
      )
    }
  }

}
