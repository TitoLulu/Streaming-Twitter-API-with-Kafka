//import net.snowflake.spark.snowflake.SnowflakeConnectorUtils
package net.snowflake.spark.snowflake
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming._
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame,SaveMode,SparkSession}
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapred.{FileInputFormat, FileOutputFormat, JobConf, JobClient}
import org.apache.hadoop.conf.Configured
import org.apache.hadoop.util.{GenericOptionsParser, Tool, ToolRunner}
//import org.apache.hadoop.native._

//case class TwitterData()

object StreamHandler {
  def main(args: Array[String]): Unit = {
      // start spark session
      val spark = SparkSession
        .builder
        .appName("Stream Handler")
        .getOrCreate()
        
      import spark.implicits._

      // subscribe to the tooic  worldcup 2022
      val inputStreamDF = spark
        .readStream.format("kafka")
        .option("kafka.bootstrap.servers", "localhost:9092")
        .option("subscribe", "worldcup 2022")
        .option("startingOffsets", "earliest")
        .load()
        //.selectExpr("CAST(value AS STRING)")

      val inputStream = inputStreamDF.selectExpr("CAST(value AS STRING)").as[String]

      // declare input steam dataframe headers
      // val df = inputStream.toDDF("user_id","created_at","num_followers","location","num_favourites","num_retweets")

      // establish connection to sink db (snowflake)
      var sfOptions = Map(
          "sfURL" -> "https://gjnedoz-dmb37080.snowflakecomputing.com",
          "sfAccount" -> "<account>",
          "sfUser" -> "<user>",
          "sfPassword" -> "<password>",
          "sfDatabase" -> "TWITTER",
          "sfSchema" -> "PUBLIC",
          "sfRole" -> "ACCOUNTADMIN"
      )

      // write data to table
      inputStream.write
          .format("snowflake")
          .options(sfOptions) 
          .option("dbtable", "TWITTER")
          .mode(SaveMode.Overwrite)
          .save()

    }
    
}

// import com.snowflake.snowpark._
// import com.snowflake.snowpark.functions._

// object Main {
//   def main(args: Array[String]): Unit = {
//     // Replace the <placeholders> below.
//     val configs = Map (
//       "URL" -> "https://gjnedoz-dmb37080.snowflakecomputing.com",
//       "ACCOUNT" -> "<account>",
//       "USER" -> "<user>",
//       "PASSWORD" -> "<password>",
//       "ROLE" -> "ACCOUNTADMIN",
//       "WAREHOUSE" -> "COMPUTE_WH",
//       "DB" -> "TWITTER",
//       "SCHEMA" -> "PUBLIC"
//     )
//     val session = Session.builder.configs(configs).create
//     session.sql("show tables").show()
//   }
// }







