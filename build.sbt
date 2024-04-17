name := """tfg-isi-jorgesobrinosolis"""
organization := "com.ceu"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava,SwaggerPlugin)

scalaVersion := "2.13.10"

libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "5.1.0",
  "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0"
)
libraryDependencies += guice
libraryDependencies ++=Seq("com.typesafe.play" %% "play" % "2.8.18")
libraryDependencies += "org.webjars" % "swagger-ui" % "4.1.2"


//Swagger plugin configuration
// Java package containing the entities
swaggerDomainNameSpaces := Seq("entities")
//Using playjava
swaggerPlayJava := true
//using Pretty JSON
swaggerPrettyJson := true