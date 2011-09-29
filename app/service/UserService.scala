package service

import storage.db.{User, UserDao}
import net.liftweb.json._
import util.PlayParameterNameReader
import com.redis.RedisClient

class UserService {

  val dbMemoryCache = new RedisClient("localhost", 6379)

  implicit val formats = new DefaultFormats {
    override val parameterNameReader = PlayParameterNameReader
  }

  def convertUserToJson(user: User): String = {
    val jsonValue =  Extraction.decompose(user)
    val jsonRendered = compact(render(jsonValue))
    println("convertUserToJson jsonRendered = " + jsonRendered)
    jsonRendered
  }


  def convertJsonToUser(userJson: String): User = {
    val json = parse(userJson)
    println("json in convertJsonToUser = " + json)
    val user = json.extract[User]
    user
  }


  def createUser(id: Long, name: String) = {
    new UserDao().createUser(id,name)
  }


  def createUserAndCache(id: Long, name: String) = {
    val user = new UserDao().createUser(id,name)
    dbMemoryCache.setex("user:"+user.getName(),60000, convertUserToJson(user))
  }


  def getUser(name: String): User = {
    val jsonValue =  dbMemoryCache.get(name)

    jsonValue match {
      case Some(userJson) => {
        convertJsonToUser(userJson)
      }
      case None => {
        val user = new UserDao().getUser(name)
        dbMemoryCache.setex("user:"+user.getName(),60000, convertUserToJson(user))
        user
      }
    }
  }


  def setUser(user: User) = {
    new UserDao().setUser(user)
  }


  def setUserAndCache(user: User) = {
    new UserDao().setUser(user)
    dbMemoryCache.setex("user:"+user.getName(),60000, convertUserToJson(user))
  }

}