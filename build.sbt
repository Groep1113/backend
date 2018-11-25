ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "2.12.7"
ThisBuild / organization := "nl.ooot.wms"
routesGenerator := InjectedRoutesGenerator

val dirSubmodules = "sbt_submodules"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"

lazy val IT_WMS = (project in file("."))
  // .dependsOn(Core) // @TODO: dependsOn vs aggregate ?
  .dependsOn(Core)
  .dependsOn(Networking)
  .dependsOn(GraphQL)
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(PlayScala, PlayEbean)
  .settings(
  	// project name
    name := "htg-it-wms",

    // reduce the maximum number of errors shown by the Scala compiler
    maxErrors := 20,

    libraryDependencies += scalaTest % Test,
    libraryDependencies += guice,
    libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.13",
    libraryDependencies += "com.github.t3hnar" %% "scala-bcrypt" % "3.1",
  )
playEbeanModels in Compile := Seq("nl.ooot.wms.models.*")

lazy val Core = (project in file(s"$dirSubmodules/core"))
  .settings(
    name := "HTG IT Core",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq(
      // These libraries are only used for the Weather example/test
      "com.eed3si9n" %% "gigahorse-okhttp" % "0.3.1",
      "com.typesafe.play" %% "play-json" % "2.6.9"
    ),
  )

lazy val Networking = (project in file(s"$dirSubmodules/networking"))
  .settings(
    name := "HTG IT Networking",
    libraryDependencies += scalaTest % Test,
  )

lazy val GraphQL = (project in file(s"$dirSubmodules/graphql"))
  .settings(
    name := "HTG IT GraphQL",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq(
      // Sangria is our GraphQL backend library
      "org.sangria-graphql" %% "sangria" % "1.4.2",
      "org.sangria-graphql" %% "sangria-slowlog" % "0.1.8",
      "org.sangria-graphql" %% "sangria-play-json" % "1.0.4",
    )
  )