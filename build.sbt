ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "2.12.7"
ThisBuild / organization := "nl.ooot.wms"

val dirSubmodules = "sbt_submodules"

val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
val gigahorse = "com.eed3si9n" %% "gigahorse-okhttp" % "0.3.1"
val playJson  = "com.typesafe.play" %% "play-json" % "2.6.9"

val circeVersion = "0.10.0"

lazy val IT_WMS = (project in file("."))
  // .dependsOn(IT_WMS_Core) // dependsOn vs aggregate ?
  .dependsOn(IT_WMS_Core)
  .dependsOn(IT_WMS_Networking)
  .dependsOn(IT_WMS_GraphQL)
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

lazy val IT_WMS_GraphQL = (project in file(s"$dirSubmodules/graphql"))
  .settings(
    name := "HTG IT GraphQL",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.sangria-graphql" %% "sangria" % "1.4.2",
    libraryDependencies += "org.sangria-graphql" %% "sangria-circe" % "1.2.1",
    // JSON Library; circe.github.io/circe/
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % circeVersion)
  )
