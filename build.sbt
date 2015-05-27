name := """IOSR2015-twitter-streaming-data-client"""

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype repo"                    at "https://oss.sonatype.org/content/groups/scala-tools/",
  "Sonatype releases"                at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype snapshots"               at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype staging"                 at "http://oss.sonatype.org/content/repositories/staging",
  "Java.net Maven2 Repository"       at "http://download.java.net/maven/2/",
  "Twitter Repository"               at "http://maven.twttr.com",
  "Websudos releases"                at "http://maven.websudos.co.uk/ext-release-local"
)

libraryDependencies ++= {
  val sprayV = "1.3.3"
  val akkaV = "2.3.10"
  Seq(
    "io.spray"            %% "spray-can"             % sprayV,
    "io.spray"            %% "spray-routing"         % sprayV,
    "io.spray"            %% "spray-client"          % sprayV,
    "io.spray"            %% "spray-json"            % "1.3.1",
    "com.typesafe.akka"   %% "akka-actor"            % akkaV,
    "com.typesafe.akka"   %% "akka-slf4j"            % akkaV,
    "ch.qos.logback"      %  "logback-classic"       % "1.1.2",
    "net.ceedubs"         %% "ficus"                 % "1.1.2",
    "io.github.morgaroth" %% "spray-json-annotation" % "0.4.2"
//    "com.websudos"        %% "phantom-dsl"           % "1.8.12",
//    "com.websudos"        %% "phantom-testkit"       % "1.8.12"
  )
}

Revolver.settings

addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full)
