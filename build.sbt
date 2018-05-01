enablePlugins(ScalaJSPlugin)

name := "com.baierl.magicCircle"
scalaVersion := "2.12.3"

scalacOptions += "-feature"
scalacOptions += "-deprecation"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.2"
libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.9.1"
libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.6.7"
libraryDependencies += "org.singlespaced" %%% "scalajs-d3" % "0.3.4"
libraryDependencies += "com.lihaoyi" %%% "scalarx" % "0.3.2"
libraryDependencies += "com.timushev" %%% "scalatags-rx" % "0.3.0"
libraryDependencies += "org.querki" %%% "jquery-facade" % "1.2"