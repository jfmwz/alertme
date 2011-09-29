package session

import com.redis.cluster.RedisCluster
import com.redis._
import net.liftweb.json._
import util.PlayParameterNameReader


object SessionStore {

  val r = new RedisClient("localhost", 6379)

  implicit val formats = new DefaultFormats {
    override val parameterNameReader = PlayParameterNameReader
  }

  def setSession(sId: String, userSession: UserSession) = {
    val jsonValue =  Extraction.decompose(userSession)
    val jsonRendered = compact(render(jsonValue))
    println("jsonRendered = " + jsonRendered)
    r.set(sId, jsonRendered)
  }


  def getSession(sId: String): UserSession = {
    val jsonValue = r.get(sId)

    jsonValue match {
      case Some(sessionData) => {
        val json = parse(sessionData)
        println("json in getSession = " + json)
        val userSession = json.extract[UserSession]
        userSession
      }
      case None => {
        UserSession("", Map.empty)
      }
    }
  }


  def deleteSession(sId: String) = {
     r.del(sId)
  }

}