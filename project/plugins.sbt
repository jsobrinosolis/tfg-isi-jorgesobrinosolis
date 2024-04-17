// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.19")

// Defines scaffolding (found under .g8 folder)
// http://www.foundweekends.org/giter8/scaffolding.html
// sbt "g8Scaffold form"
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8-scaffold" % "0.13.0")

// The https://github.com/iheartradio/play-swagger plugin
addSbtPlugin("com.iheart" % "sbt-play-swagger" % "0.13.5")