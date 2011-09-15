package storage.db

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl._
import storage.dbpool.C3P0Database


class UserDao {

  def createUser(id: Long, name: String) = {
    C3P0Database.startDatabaseSession()

    transaction {
      UserDB.users.insert(new User(id,name))
    }
  }


}
