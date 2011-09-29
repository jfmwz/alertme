package util

import net.liftweb.json.ParameterNameReader
import scala.collection.JavaConversions._
import java.lang.reflect.Constructor
import play.classloading.enhancers.LocalvariablesNamesEnhancer


object PlayParameterNameReader extends ParameterNameReader {

  def lookupParameterNames(constructor: Constructor[_]) = LocalvariablesNamesEnhancer.lookupParameterNames(constructor)

}