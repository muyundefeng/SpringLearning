package maxTemperature;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * 作业驱动程序，驱动作业的运行，这与MaxTemperature的区别在于
 * 本类引入相关的配置参数
 * Created by lisheng on 17-4-1.
 */
public class MaxTemperatureDriver extends Configured implements Tool {

    public int run(String[] strings) throws Exception {
        if (strings.length != 2) {
            System.err.printf("usage:%s [generic options] <input> <output> \n", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);//使用Tool接口的主要目的是简化命令行参数的输出
            /**正如上面所示，当传入的参数个数不等于2，将会打印命令的使用，除此之外还可以进行相关配置参数的处理,
             *Generic options supported are
             -conf <configuration file>     specify an application configuration file
             -D <property=value>            use value for given property
             -fs <local|namenode:port>      specify a namenode
             -jt <local|resourcemanager:port>    specify a ResourceManager
             -files <comma separated list of files>    specify comma separated files to be copied to the map reduce cluster
             -libjars <comma separated list of jars>    specify comma separated jar files to include in the classpath.
             -archives <comma separated list of archives>    specify comma separated archives to be unarchived on the compute machines.
             */
            return -1;
        }

        JobConf jobConf = new JobConf(getConf());
        jobConf.setJarByClass(getClass());

        FileInputFormat.addInputPath(jobConf, new Path(strings[0]));
        FileOutputFormat.setOutputPath(jobConf, new Path(strings[1]));

        jobConf.setMapperClass(MaxTemperatureMapper.class);

        //jobConf.setPartitionerClass(HashPartitioner.class);
        jobConf.setCombinerClass(MaxTemperatureReducer.class);
        jobConf.setReducerClass(MaxTemperatureReducer.class);

        jobConf.setOutputKeyClass(Text.class);
        jobConf.setOutputValueClass(IntWritable.class);

        Job job = new Job(jobConf);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) {
        String input = "/home/lisheng/work/workspace/javaspace/HadoopSpace/数据样本/权威指南/气象数据/1902";
        String output = "/home/lisheng/work/workspace/javaspace/HadoopSpace/数据样本/权威指南/处理以后的数据/气象数据/ouput2";
        String files[] = {output};
        int exitCode = 0;
        try {
            exitCode = ToolRunner.run(new MaxTemperatureDriver(), files);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(exitCode);
    }
}
