import sbtbuildinfo.BuildInfoKey
import sbtbuildinfo.BuildInfoKeys.{ buildInfoKeys, buildInfoObject, buildInfoPackage }
import BuildHelper._

inThisBuild(
  List(
    organization := "knoldus",
    homepage := Some(url("https://www.knoldus.com")),
    version := "0.1.1-SNAPSHOT",
    startYear := Some(2022),
    licenses += ("MIT", new URL("https://opensource.org/licenses/MIT")),
    developers := List(
      Developer("PKOfficial", "Prabhat Kashyap", "prabhatkashyap33@gmail.com", url("https://github.com/PKOfficial"))
    ),
    Test / fork := true,
    scmInfo := Some(
      ScmInfo(url("https://github.com/knoldus/pubsub4s"), "scm:git@github.com:knoldus/pubsub4s.git")
    )
  )
)

lazy val `pubsub4s` = project
  .in(file("."))
  .enablePlugins(BuildInfoPlugin, AutomateHeaderPlugin)
  .settings(
    name := "pub-sub-4-s"
  )
  .settings(projectSettings)
  .settings(
    Seq(
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion, isSnapshot),
      buildInfoPackage := "knoldus.pubsub",
      buildInfoObject := "BuildInfo"
    )
  )
  .settings(
    libraryDependencies ++= Seq(
      "com.google.api"      % "api-common"                      % "2.1.1",
      "com.google.api"      % "gax"                             % "2.7.1",
      "com.google.api.grpc" % "proto-google-cloud-pubsub-v1"    % "1.97.0",
      "com.google.auth"     % "google-auth-library-credentials" % "1.3.0",
      "com.google.auth"     % "google-auth-library-oauth2-http" % "1.3.0",
      "com.google.protobuf" % "protobuf-java"                   % "3.19.1",
      "com.google.cloud"    % "google-cloud-pubsub"             % "1.115.0",
      "org.scalatest"      %% "scalatest"                       % "3.2.10"   % Test,
      "org.scalatestplus"  %% "mockito-3-4"                     % "3.2.10.0" % Test,
      "org.mockito"         % "mockito-core"                    % "4.2.0"    % Test
    ),
    testFrameworks := Seq(TestFrameworks.ScalaTest)
  )

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")
