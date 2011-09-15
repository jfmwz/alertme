package storage.dbpool

import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.PostgreSqlAdapter


object C3P0Database  {

  def startDatabaseSession():Unit = {
    SessionFactory.concreteFactory = Some(() => Session.create(
          C3POPool.getConnection(),
          new PostgreSqlAdapter)
        )
  }

}
