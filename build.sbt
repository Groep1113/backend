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
  )

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
//    libraryDependencies ++= Seq(
//      "com.typesafe.akka" %% "akka-http"   % "10.1.5",
//      "com.typesafe.akka" %% "akka-stream" % "2.5.12"
//    ),
  )

lazy val GraphQL = (project in file(s"$dirSubmodules/graphql"))
  .settings(
    name := "HTG IT GraphQL",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq(
      // Sangria is our GraphQL backend library
      "org.sangria-graphql" %% "sangria" % "1.4.2",
      "org.sangria-graphql" %% "sangria-slowlog" % "0.1.8",
      "org.sangria-graphql" %% "sangria-circe" % "1.2.1",

      // Akka HTTP is used for the webserver
      "com.typesafe.akka" %% "akka-http" % "10.1.5",
      "de.heikoseeberger" %% "akka-http-circe" % "1.21.0",

      // JSON Library; circe.github.io/circe/
      "io.circe" %%	"circe-core" % "0.9.3",
      "io.circe" %% "circe-parser" % "0.9.3",
      "io.circe" %% "circe-optics" % "0.9.3",
    )
  )