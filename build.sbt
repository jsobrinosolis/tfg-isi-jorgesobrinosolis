name := """tfg-isi-jorgesobrinosolis"""
organization := "com.ceu"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "5.1.0",
  "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0"
)
libraryDependencies += guice
libraryDependencies ++=Seq("com.typesafe.play" %% "play" % "2.8.18")
