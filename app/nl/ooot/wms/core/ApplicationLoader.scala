package nl.ooot.wms.core

import play.api.Configuration

import play.api.inject.guice._

class ApplicationLoader extends GuiceApplicationLoader() {
  override def builder(context: play.api.ApplicationLoader.Context): GuiceApplicationBuilder = {
    println("Hello world, i happen before play")
//    val extra = Configuration("play.http.filters" -> "nl.ooot.wms.networking.LoginFilter")

    initialBuilder
      .in(context.environment)
      .loadConfig(context.initialConfiguration)
//      .loadConfig(context.initialConfiguration ++ extra)
      .overrides(overrides(context): _*)
  }
}