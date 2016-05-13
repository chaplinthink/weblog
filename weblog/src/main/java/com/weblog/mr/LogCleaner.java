package com.weblog.mr;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import com.weblog.parser.LogParser;
/*
 * mr数据清洗
 * 
 * @author zhangwei
 * */
public class LogCleaner {// extends Configured implements Tool

	static final String INPUT_PATH = "hdfs://hadoop:9000/weblog/";
	static final String OUT_PATH = "hdfs://hadoop:9000/weblog_cleaned";
	
	//驱动
	public static void main(String[] args) throws Exception {
		
		final Job job = new Job(new Configuration(),
				LogCleaner.class.getSimpleName());

		// 打包
		// job.setJarByClass(LogCleaner.class);

		// 1.1输入目录在哪里
		FileInputFormat.setInputPaths(job, INPUT_PATH);// args[0]
		// 指定对输入数据进行格式化处理的类
		job.setInputFormatClass(TextInputFormat.class);
		// 1.2指定自定义的Mapper类
		job.setMapperClass(MyMapper.class);
		// 指定map输出的<k,v>类型
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(Text.class);
		// 1.3分区
		// job.setPartitionerClass(HashPartitioner.class);

		// job.setNumReduceTasks(1);
		// 1.4 TODO 排序 、分组
		// 1.5 TODO 规约 （可选）
		// 2.2指定自定义的Reducer 类
		job.setReducerClass(MyReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		// 2.3指定输出的路径
		FileOutputFormat.setOutputPath(job, new Path(OUT_PATH));// new
																// Path(args[1])
		// 指定输出的格式化类
		job.setOutputFormatClass(TextOutputFormat.class);

		// 把作业提交给JobTracker运行
		job.waitForCompletion(true);
	}
	

	static class MyMapper extends
			Mapper<LongWritable, Text, LongWritable, Text> {

		LogParser logparser = new LogParser();
		Text v2 = new Text();

		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, LongWritable, Text>.Context context)
				throws IOException, InterruptedException {
			final String[] parsed = logparser.parse(value.toString());

			// 过滤静态信息
			if (parsed[2].startsWith("GET /static/")
					|| parsed[2].startsWith("GET /uc_server/")) {
				return;
			}

			// 过滤掉开头的特定格式的字符串
			if (parsed[2].startsWith("GET /")) {
				parsed[2] = parsed[2].substring("GET /".length());
			} else if (parsed[2].startsWith("POST /")) {
				parsed[2] = parsed[2].substring("POST /".length());
			}

			// 过滤掉结尾的特定格式的字符串
			if (parsed[2].endsWith("HTTP/1.1")) {
				parsed[2] = parsed[2].substring(0, parsed[2].length()
						- "HTTP/1.1".length());
			}
			// substring(int beginIndex) substring(int beginIndex, int endIndex)

			v2.set(parsed[0] + "\t" + parsed[1] + "\t" + parsed[2]);
			context.write(key, v2);
		}
	}

	static class MyReducer extends
			Reducer<LongWritable, Text, Text, NullWritable> {
		@Override
		protected void reduce(LongWritable k2, Iterable<Text> v2s,
				Reducer<LongWritable, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			for (Text v2 : v2s) {
				context.write(v2, NullWritable.get());
			}

		}
	}

}
