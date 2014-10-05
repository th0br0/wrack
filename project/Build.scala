package summingbird

import sbt._
import Keys._
import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform._
import com.twitter.scrooge.ScroogeSBT

import sbtassembly.Plugin._
import AssemblyKeys._

object JHBuild extends Build {
  val extraSettings = Project.defaultSettings ++ scalariformSettings

  val sharedSettings = extraSettings ++ Seq(
    organization := "jacobshack",
    version := "0.0.1",
    scalaVersion := "2.10.4",
    javacOptions ++= Seq("-source", "1.7", "-target", "1.7"),

    libraryDependencies ++= Seq(
      "org.slf4j" % "slf4j-api" % slf4jVersion,
      "org.scalacheck" %% "scalacheck" % "1.11.4" % "test",
      // These satisify's scaldings log4j needs when in test mode
      "log4j" % "log4j" % log4jVersion % "test",
      "org.slf4j" % "slf4j-log4j12" % slf4jVersion % "test",
      "org.specs2" %% "specs2" % "2.3.13" % "test"
    ),

    resolvers ++= Seq(
      Opts.resolver.sonatypeSnapshots,
      Opts.resolver.sonatypeReleases,
      "Clojars Repository" at "http://clojars.org/repo",
      "Conjars Repository" at "http://conjars.org/repo",
      "Twitter Maven" at "http://maven.twttr.com"
    ),

    parallelExecution in Test := true,

    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-Yresolve-term-conflict:package"
    ),

    scalacOptions in Test ++= Seq("-Yrangepos")
  )

  lazy val formattingPreferences = {
    import scalariform.formatter.preferences._
    FormattingPreferences().
      setPreference(AlignParameters, false).
      setPreference(PreserveSpaceBeforeArguments, true)
  }

  lazy val jacobshack = Project(
    id = "jacobshack",
    base = file("."),
    settings = sharedSettings
  ).settings(
      test := {},
      publish := {}, // skip publishing for this root project.
      publishLocal := {}
    ).aggregate(
      jacobshackAvro,
      jacobshackCore,
      jacobshackGenerator,
      jacobshackCzml,
      czmlWriter
    )

  val bijectionVersion = "0.6.3"
  val scaldingVersion = "0.11.2"
  val chillVersion = "0.4.0"
  val hadoopVersion = "2.4.0"
  val cascadingVersion = "2.5.6"
  val cascadingJdbcVersion = "2.5.4"
  // 2.5.4 jdbc uses cascading 2.5.5 -.-
  val mongoHadoopVersion = "1.3.0"
  val postgresVersion = "9.3-1102-jdbc41"


  lazy val slf4jVersion = "1.6.6"
  lazy val log4jVersion = "1.2.17"

  def module(name: String) = {
    val id = "jacobshack-%s".format(name)
    Project(id = id, base = file(id), settings = sharedSettings ++ Seq(
      Keys.name := id
    )
    )
  }

  lazy val czmlWriter = Project(id="czml-writer", base=file("czml-writer"), settings = sharedSettings ++ Seq(
    Keys.name := "czml-writer",
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.4"
    )
  ))

  lazy val jacobshackCzml = module("czml").settings().dependsOn(czmlWriter)

  lazy val jacobshackAvro = module("avro").settings(sbtavro.SbtAvro.avroSettings: _*).settings(
    libraryDependencies ++= Seq(
      "com.twitter" %% "scalding-avro" % scaldingVersion,
      "com.twitter" %% "bijection-avro" % bijectionVersion,
      "com.twitter" % "chill-avro" % chillVersion,
      "cascading" % "cascading-core" % cascadingVersion,
      "cascading" % "cascading-local" % cascadingVersion,
      "cascading" % "cascading-hadoop2-mr1" % cascadingVersion,

      "org.apache.hadoop" % "hadoop-common" % hadoopVersion % "provided",
      "org.apache.hadoop" % "hadoop-mapreduce-client-core" % hadoopVersion % "provided",

      "com.twitter" %% "scalding-core" % scaldingVersion exclude("cascading", "cascading-local") exclude("cascading", "cascading-hadoop")
    )
  )

  lazy val jacobshackGenerator = module("generator").settings(assemblySettings: _*).settings(
    version := "0.0.10",
    libraryDependencies ++= Seq(
      "cascading" % "cascading-core" % cascadingVersion,
      "cascading" % "cascading-local" % cascadingVersion,
      "cascading" % "cascading-hadoop2-mr1" % cascadingVersion,
      "cascading" % "cascading-jdbc-core" % cascadingJdbcVersion,
      "cascading" % "cascading-jdbc-postgresql" % cascadingJdbcVersion,

      "org.apache.hadoop" % "hadoop-common" % hadoopVersion % "provided",
      "org.apache.hadoop" % "hadoop-mapreduce-client-core" % hadoopVersion % "provided",
      "org.mongodb" % "mongo-hadoop-core" % mongoHadoopVersion,

      "com.twitter" %% "scalding-core" % scaldingVersion exclude("cascading", "cascading-local") exclude("cascading", "cascading-hadoop"),
      "com.twitter" %% "scalding-args" % scaldingVersion,
      "com.twitter" %% "scalding-date" % scaldingVersion,
      "com.twitter" %% "scalding-commons" % scaldingVersion,
      "com.twitter" %% "scalding-avro" % scaldingVersion,
      "com.twitter" %% "bijection-avro" % bijectionVersion,
      "com.twitter" %% "chill" % chillVersion,
      "com.twitter" % "chill-avro" % chillVersion,
      "com.twitter" % "chill-hadoop" % chillVersion,

      "org.postgresql" % "postgresql" % postgresVersion
    ),
    scalacOptions ++= Seq(
      "-optimise"
    ),

    // Drop these jars
    excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
      val excludes = Set(
        "hadoop",
        "minlog",
        "jsp",
        "jasper-compiler",
        "cascading-hadoop-2.5.5",
        "servlet-api",
        "commons-beanutils-1.7.0",
        "commons-beanutils-core-1.8.0",
        "asm-3.2",
        "postgresql-9.1"
      )
      cp filter { jar => excludes.map(jar.data.getName.startsWith(_)).find(_ == true).getOrElse(false)}
    }
  ).dependsOn(jacobshackAvro, jacobshackCore)

  lazy val jacobshackCore = module("core").settings(
    libraryDependencies ++= Seq(
      "cascading" % "cascading-core" % cascadingVersion,
      "cascading" % "cascading-local" % cascadingVersion,
      "cascading" % "cascading-hadoop2-mr1" % cascadingVersion,
      "cascading" % "cascading-jdbc-core" % cascadingJdbcVersion,
      "cascading" % "cascading-jdbc-postgresql" % cascadingJdbcVersion,

      "org.apache.hadoop" % "hadoop-common" % hadoopVersion % "provided",
      "org.apache.hadoop" % "hadoop-mapreduce-client-core" % hadoopVersion % "provided",
      "org.mongodb" % "mongo-hadoop-core" % mongoHadoopVersion,

      "com.twitter" %% "scalding-core" % scaldingVersion exclude("cascading", "cascading-local") exclude("cascading", "cascading-hadoop"),
      "com.twitter" %% "scalding-args" % scaldingVersion,
      "com.twitter" %% "scalding-date" % scaldingVersion,
      "com.twitter" %% "scalding-commons" % scaldingVersion,

      "org.postgresql" % "postgresql" % postgresVersion
    )
  )
}
