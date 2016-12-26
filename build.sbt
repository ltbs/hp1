// ScalaFX dependency
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.102-R11"

// Set the prompt (for this build) to include the project id.
shellPrompt := { state => System.getProperty("user.name") + ":" + Project.extract(state).currentRef.project + "> " }

// Fork a new JVM for 'run' and 'test:run'
fork := true

scalaVersion := "2.11.8"

sbtVersion := "0.13.13"
