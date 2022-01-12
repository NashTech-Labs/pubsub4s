import sbt.Keys._
import sbt._
import sbtcrossproject.CrossPlugin.autoImport._

object BuildHelper {

  val Scala213 = "2.13.7"
  val Scala3   = "3.1.0"

  def projectSettings =
    Seq(
      name := "pubsub4s",
      crossScalaVersions := Seq(Scala213, Scala3),
      ThisBuild / scalaVersion := Scala213,
      scalacOptions := stdOptions ++ extraOptions(scalaVersion.value, optimize = !isSnapshot.value),
      Test / parallelExecution := true,
      incOptions ~= (_.withLogRecompileOnMacro(false)),
      autoAPIMappings := true,
      Compile / unmanagedSourceDirectories ++= {
        CrossVersion.partialVersion(scalaVersion.value) match {
          case Some((2, x)) if x <= 11 =>
            Seq(
              Seq(file(sourceDirectory.value.getPath + "/main/scala-2.11")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-2.11")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "test").toList.map(f => file(f.getPath + "-2.11")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-2.x")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-2.11-2.12"))
            ).flatten
          case Some((2, x)) if x == 12 =>
            Seq(
              Seq(file(sourceDirectory.value.getPath + "/main/scala-2.12")),
              Seq(file(sourceDirectory.value.getPath + "/main/scala-2.12+")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-2.12+")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "test").toList.map(f => file(f.getPath + "-2.12+")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-2.x")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-2.12-2.13")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-2.11-2.12"))
            ).flatten
          case Some((2, x)) if x >= 13 =>
            Seq(
              Seq(file(sourceDirectory.value.getPath + "/main/scala-2.12")),
              Seq(file(sourceDirectory.value.getPath + "/main/scala-2.12+")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-2.12+")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "test").toList.map(f => file(f.getPath + "-2.12+")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-2.x")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-2.12-2.13")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-2.13+"))
            ).flatten
          case Some((3, _))            =>
            Seq(
              Seq(file(sourceDirectory.value.getPath + "/main/scala-2.12")),
              Seq(file(sourceDirectory.value.getPath + "/main/scala-2.12+")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-2.12+")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "test").toList.map(f => file(f.getPath + "-2.12+")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-dotty")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-2.13+"))
            ).flatten
          case _                       =>
            Nil
        }
      },
      Test / unmanagedSourceDirectories ++= {
        CrossVersion.partialVersion(scalaVersion.value) match {
          case Some((2, x)) if x >= 12 =>
            Seq(
              Seq(file(sourceDirectory.value.getPath + "/test/scala-2.12")),
              Seq(file(sourceDirectory.value.getPath + "/test/scala-2.12+")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "test").toList.map(f => file(f.getPath + "-2.x"))
            ).flatten
          case Some((3, _))            =>
            Seq(
              Seq(file(sourceDirectory.value.getPath + "/test/scala-2.12+")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + "-2.12+")),
              CrossType.Full.sharedSrcDir(baseDirectory.value, "test").toList.map(f => file(f.getPath + "-dotty"))
            ).flatten
          case _                       =>
            Nil
        }

      }
    )

  private val stdOptions = Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-feature",
    "-unchecked"
  )

  private val std2xOptions = Seq(
    "-language:higherKinds",
    "-language:existentials",
    "-explaintypes",
    "-Yrangepos",
    "-Xlint:_,-missing-interpolator,-type-parameter-shadow",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard"
  ) ++ customOptions

  private def customOptions =
    if (propertyFlag("fatal.warnings", true))
      Seq("-Xfatal-warnings")
    else
      Nil

  private def propertyFlag(property: String, default: Boolean) =
    sys.props.get(property).map(_.toBoolean).getOrElse(default)

  def extraOptions(scalaVersion: String, optimize: Boolean): Seq[String] =
    CrossVersion.partialVersion(scalaVersion) match {
      case Some((3, _))  =>
        Seq(
          "-language:implicitConversions",
          "-Xignore-scala2-macros"
        )
      case Some((2, 13)) =>
        Seq(
          "-Ywarn-unused:params,-implicits"
        ) ++ std2xOptions
      case Some((2, 12)) =>
        Seq(
          "-opt-warnings",
          "-Ywarn-extra-implicit",
          "-Ywarn-unused:_,imports",
          "-Ywarn-unused:imports",
          "-Ypartial-unification",
          "-Yno-adapted-args",
          "-Ywarn-inaccessible",
          "-Ywarn-infer-any",
          "-Ywarn-nullary-override",
          "-Ywarn-nullary-unit",
          "-Ywarn-unused:params,-implicits",
          "-Xfuture",
          "-Xsource:2.13",
          "-Xmax-classfile-name",
          "242"
        ) ++ std2xOptions
      case _             => Seq.empty
    }

}
