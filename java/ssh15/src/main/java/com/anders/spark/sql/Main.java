package com.anders.spark.sql;

import java.io.IOException;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

public class Main {
	public static void main(String[] args) throws IOException {
		System.setProperty("hadoop.home.dir", "C:\\Users\\Anders\\git\\hadoop-common-2.2.0-bin");

		SparkConf sparkConf = new SparkConf().setAppName("sparksql").setMaster("spark://192.168.56.101:7077")
				.set("spark.sql.warehouse.dir", "hdfs://192.168.56.101:9000/user/hive/warehouse");
//		.set("spark.ui.port", "4040");

		SparkSession sparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate();
		// sparkSession.sql("select * from hive_test").show();

		SQLContext sqlContext = sparkSession.sqlContext();
		sqlContext.sql("show tables").show();

		System.in.read();
	}
}
