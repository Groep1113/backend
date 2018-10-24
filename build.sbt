ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "2.12.7"
ThisBuild / organization := "nl.ooot.wms"

val dirSubmodules = "sbt_submodules"

val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
val gigahorse = "com.eed3si9n" %% "gigahorse-okhttp" % "0.3.1"
val playJson  = "com.typesafe.play" %% "play-json" % "2.6.9"

lazy val IT_WMS = (project in file("."))
  .aggregate(IT_WMS_Core)
  .dependsOn(IT_WMS_Core)
  .dependsOn(IT_WMS_Networking)
  .enablePlugins(JavaAppPackaging)
  .settings(
  	// project name
    name := "htg-it-wms",
    
    // reduce the maximum number of errors shown by the Scala compiler
    maxErrors := 20,
    
    libraryDependencies += scalaTest % Test,
  )

lazy val IT_WMS_Core = (project in file(s"$dirSubmodules/core"))
  .settings(
    name := "HTG IT Core",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq(gigahorse, playJson),
  )
  
lazy val IT_WMS_Networking = (project in file(s"$dirSubmodules/networking"))
  .settings(
    name := "HTG IT Networking",
    libraryDependencies += scalaTest % Test,
  )
