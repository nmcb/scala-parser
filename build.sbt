def buildBase      = baseDirectory in LocalRootProject
def scratchSources = Def task (buildBase.value / "scratch" * "*.scala" get)

def common = Seq(
         scalaVersion :=  "2.11.4",
        scalacOptions +=  "-language:_",
  libraryDependencies ++= Seq(
    "org.improving" %% "psp-std"   % "0.5.0",
    "org.parboiled" %% "parboiled" % "2.0.2-SNAPSHOT"
  )
)

val macros = project settings (common: _*) settings (
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided"
)

val parser = project dependsOn macros settings (common: _*) settings (
          name :=  "scala-parser",
          test :=  (run in Test toTask "").value
)

run in Test <<= run in Test in parser

run <<= run in Compile in parser

watchSources ++= scratchSources.value
