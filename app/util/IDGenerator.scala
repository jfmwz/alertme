package util

import java.util.UUID


object IDGenerator {

  def generateUUID(): UUID = {
    UUID.randomUUID()
  }

  def generateUUIDLeastSig(): Long = {
    UUID.randomUUID().getLeastSignificantBits.longValue
  }

}