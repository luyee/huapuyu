package com.anders.hadoop.test2;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Count {

	static class NewMaxTemperatureMapper extends
			Mapper<LongWritable, Text, Text, IntWritable> {

		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			System.out.println("****************map********************");
			String line = value.toString();
			System.out.println("row : " + line);

			String[] values = line.split(",");

			context.write(new Text(values[3]),
					new IntWritable(Integer.parseInt(values[0])));
		}
	}

	static class NewMaxTemperatureReducer extends
			Reducer<Text, IntWritable, Text, IntWritable> {

		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			System.out.println("****************reduce********************");
			int sum = 0;
			for (IntWritable value : values) {
				System.out.println("key : " + key + "; value : " + value);
				sum++;
			}
			context.write(key, new IntWritable(sum));
		}
	}

	public static void main(String[] args) throws Exception {
		System.setProperty("hadoop.home.dir",
				"C:\\Users\\Anders\\git\\hadoop-common-2.2.0-bin");
		Configuration config = new Configuration();

		Job job = new Job(config);
		job.setJarByClass(Count.class);

		FileInputFormat.addInputPath(job, new Path(
				"hdfs://anders1:9000/sqoop/part-m-00000"));
		FileOutputFormat.setOutputPath(job, new Path(
				"hdfs://anders1:9000/sqoop/output"));

		job.setMapperClass(NewMaxTemperatureMapper.class);
		job.setCombinerClass(NewMaxTemperatureReducer.class);
		job.setReducerClass(NewMaxTemperatureReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}