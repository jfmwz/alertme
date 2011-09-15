package service

import java.sql.Timestamp
import storage.db.UserDao


class UserService {

  def createUser(id: Long, name: String) = {
    new UserDao().createUser(id,name)
  }

}