package controllers

import play.mvc._
import session.{UserSession, SessionStore}
import service._
import util.IDGenerator


object Application extends Controller {
  import views.Application._

  def index = {
    val userSession = SessionStore.getSession(this.session.getId)
    println("Get Session value: " + userSession)

    val m = Map("attr1" -> "val1" , "attr2" -> "val2")
    val uSess = new UserSession("alan", m)
    SessionStore.setSession(this.session.getId, uSess)

    val userSession2 = SessionStore.getSession(this.session.getId)
    println("Get Session value 2: " + userSession2)

    SessionStore.deleteSession(this.session.getId)

    html.index("Your Scala application is ready! Session Id = " + this.session.getId)
  }


  def sayHello = {
    val name = params.get("myName")

    val userService = new UserService()
    userService.createUser(util.IDGenerator.generateUUIDLeastSig, name)

    html.sayHello(name)
  }

}
