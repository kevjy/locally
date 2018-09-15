lazy val nobox = (project in file("."))
  .settings(
    name := "nobox",
    organization := "org.theresnobox",
    scalaVersion := "2.12.6",
    version := "0.1.0-SNAPSHOT",
    scalacOptions ++= compilerOptions,
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.7"),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "1.1.0",
      "org.typelevel" %% "cats-effect" % "1.0.0-RC2",
      "org.scalactic" %% "scalactic" % "3.0.5" % Test,
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    )
  )

lazy val compilerOptions = Seq(
  "-target:jvm-1.8",
  "-encoding",
  "UTF-8",
  "-unchecked",
  "-deprecation",
  "-Xfuture",
  "-Yno-adapted-args",
  "-Ypartial-unification",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Ywarn-unused"
)
