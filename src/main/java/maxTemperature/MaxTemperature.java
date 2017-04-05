package maxTemperature;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapred.*;


/**
 * Created by lisheng on 17-3-28.
 */
public class MaxTemperature {
    public static void main(String[] args) throws Exception {
        String input="mylocal/input/1902";
        String output = "mylocal/output/outpu1";

        //对map任务输出结果进行压缩,减小map任务输出传输到reduce任务输入的时间
//        Configuration configuration = new Configuration();
//        configuration.setBoolean("mapred.compress.map.output",true);
//        configuration.setClass("mapred.map.output.compression.codec",GzipCodec.class, CompressionCodec.class);
        //创建一个jobTracker
        //JobConf conf = new JobConf(configuration);
        JobConf conf = new JobConf();
        conf.setJarByClass(MaxTemperature.class);
        conf.setJobName("Max temperature");

        FileInputFormat.addInputPath(conf,new Path(input));
        FileOutputFormat.setOutputPath(conf,new Path(output));

        //创建两个taskTracker，由jobTracker调度
        conf.setMapperClass(MaxTemperatureMapper.class);
        conf.setReducerClass(MaxTemperatureReducer.class);

        //压缩mapreduce作业的输出
        FileOutputFormat.setCompressOutput(conf,true);
        FileOutputFormat.setOutputCompressorClass(conf, GzipCodec.class);

      //  MultiFileInputFormat.addInputPath(conf);
        conf.setOutputKeyClass(Text.class);
        //设置相关的输出类型
        //conf.setMapOutputKeyClass();
        //conf.setMapOutputValueClass();
        conf.setOutputValueClass(IntWritable.class);
        JobClient.runJob(conf);

        //
    }
}
