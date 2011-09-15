package session


case class UserSession(val userId: String, val attributes: Map[String, String]) {

  override def toString: String = {
    if (userId != "") {
      "userId = " + userId + "\n" +
      "attributes = " +
        attributes.map { attr =>
          (("attr-name = " + attr._1) + "," + ("attr-value = " + attr._2) + "\n")
        }
    } else {
      "SESSION-NOT-INITIATED"
    }
  }

}