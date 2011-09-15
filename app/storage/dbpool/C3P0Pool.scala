package storage.dbpool

import com.mchange.v2.c3p0.ComboPooledDataSource
import java.sql.Connection

object C3POPool {

  val cpds = new ComboPooledDataSource()
  cpds.setDriverClass("org.postgresql.Driver")
  cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/alertmedb")
  cpds.setUser("postgres")
  cpds.setPassword("postgres")

  // the settings below are optional -- c3p0 can work with defaults
  cpds.setMinPoolSize(5)
  cpds.setAcquireIncrement(5)
  cpds.setMaxPoolSize(10)

  def getConnection(): Connection = {
    cpds.getConnection
  }

}