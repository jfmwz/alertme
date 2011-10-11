package controllers

import play.mvc._
import session.{UserSession, SessionStore}
import service._
import util.IDGenerator
import akka.actor.Actor
import storage.db.User


object Application extends Controller {
  import views.Application._

  val userService = Actor.actorOf[UserService]
  userService.start()

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

    val userCreatedFuture = userService !!! UserCreation(util.IDGenerator.generateUUIDLeastSig, name)
    userCreatedFuture.await

    val userGetFuture1 = userService !!! UserRetrieval(name)
    userGetFuture1.await
    userGetFuture1.result.asInstanceOf[Option[User]] match{
      case Some(res)=> println("Got user : " + res.getName())
      case None => println("No user returned")
    }

    val userGetFuture2 = userService !!! UserRetrieval(name)
    userGetFuture2.await
    userGetFuture2.result.asInstanceOf[Option[User]] match{
      case Some(res)=> println("Got user : " + res.getName())
      case None => println("No user returned")
    }

    html.sayHello(name)
  }


  def findUserAsJson = {
    val name = params.get("userName")
    val otherParam = params.get("otherParam")

    val userGetFuture3 = userService !!! UserRetrieval(name)
    userGetFuture3.await
    userGetFuture3.result.asInstanceOf[Option[User]] match{
      case Some(aUser)=> Json(aUser)
      case None => Json("No user returned")
    }
  }


  def sayHelloSimple = {
    val name = params.get("myName")

    val userServiceSimple = new UserService()
    userServiceSimple.createUserAndCache(util.IDGenerator.generateUUIDLeastSig, name)

    val user1 = userServiceSimple.getUser(name);

    println("Got user : " + user1.getName())

    val user2 = userServiceSimple.getUser(name);

    println("Got user again : " + user2.getName())

    html.sayHello(name)
  }

}
