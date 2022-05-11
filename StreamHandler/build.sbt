name := "Stream Handler"

version := "1.0"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
	"org.apache.spark" %% "spark-core" % "2.4.5" % "provided",
	"org.apache.spark" %% "spark-sql" % "2.4.5" % "provided",
	"net.snowflake" % "snowflake-ingest-sdk" % "0.10.3",
  	"net.snowflake" % "snowflake-jdbc" % "3.13.14",
	"org.apache.hadoop" % "hadoop-core" % "1.2.0",
	//"net.snowflake" % "spark-snowflake" % "2.9.2-spark_2.4"
  
  )
  
// scalaVersion := "2.12.13"

// libraryDependencies += "com.snowflake" % "snowpark" % "1.4.0"

// Compile/console/scalacOptions += "-Yrepl-class-based"
// Compile/console/scalacOptions += "-Yrepl-outdir"
// Compile/console/scalacOptions += "repl_classes"