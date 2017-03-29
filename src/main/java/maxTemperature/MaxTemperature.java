package maxTemperature;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;



/**
 * Created by lisheng on 17-3-28.
 */
public class MaxTemperature {
    public static void main(String[] args) throws Exception {
        String input="/home/lisheng/work/workspace/javaspace/HadoopSpace/数据样本/权威指南/气象数据/1902";
        String output = "/home/lisheng/work/workspace/javaspace/HadoopSpace/数据样本/权威指南/处理以后的数据/气象数据/ouput1";

        //创建一个jobTracker
        JobConf conf = new JobConf();
        conf.setJarByClass(MaxTemperature.class);
        conf.setJobName("Max temperature");

        FileInputFormat.addInputPath(conf,new Path(input));
        FileOutputFormat.setOutputPath(conf,new Path(output));

        //创建两个taskTracker，由jobTracker调度
        conf.setMapperClass(MaxTemperatureMapper.class);
        conf.setReducerClass(MaxTemperatureReducer.class);

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        JobClient.runJob(conf);
    }
}
