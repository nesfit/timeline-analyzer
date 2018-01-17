package cz.vutbr.fit.tarzan.spark.sclients

import org.apache.spark.sql.SparkSession

object Downloader {
  
    def main(args : Array[String]) {
    val spark = SparkSession.builder()
      .appName("Profile Downloader")
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    val col = sc.parallelize(0 to 100 by 5)
    val smp = col.sample(true, 4)
    val colCount = col.count
    val smpCount = smp.count

    println("orig count = " + colCount)
    println("sampled count = " + smpCount)
  }

}
