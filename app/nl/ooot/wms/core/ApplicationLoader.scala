package nl.ooot.wms.core

import play.api.Configuration

import play.api.inject.guice._

class ApplicationLoader extends GuiceApplicationLoader() {
  override def builder(context: play.api.ApplicationLoader.Context): GuiceApplicationBuilder = {
    println("Hello world, i happen before play")
    val extra = Configuration("a" -> 1)
    initialBuilder
      .in(context.environment)
      .loadConfig(extra ++ context.initialConfiguration)
      .overrides(overrides(context): _*)
  }
}