import maxTemperature.MaxTemperatureDriver;
import maxTemperature.MaxTemperatureReducer;
import maxTemperatureWIthNewApi.MaxIntMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.ReduceDriver;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * 使用MRUnit执行单元测试
 * Created by lisheng on 17-4-1.
 */
public class MaxTemperatureMapper {

    @Test
    public void processoValidRecord() {
        Text value = new Text("0035029070999991902010106004+64333+023450FM-12+000599999V0201401N011819999999N0000001N9-00941+99999100551ADDGF104991999999999999999999MW1381");
        new MapDriver<LongWritable, Text, Text, IntWritable>()//测试mapper函数，类型需要与mapper类中的类型对应
                .withMapper(new maxTemperature.MaxTemperatureMapper())//配置mapper函数
                .withInputValue(value)//配置mapper的输入
                .withOutput(new Text("1902"), new IntWritable(-11))//配置mapper函数期望的输出值是否是1902 -11
                .runTest();

    }

    /**
     * reducer函数单元测试，而配置信息与上面mapper函数的配置类似，单条测试数据
     */
    @Test
    public void testReducer() {
        new ReduceDriver<Text, IntWritable, Text, IntWritable>()
                .withReducer(new MaxTemperatureReducer())
                .withInputKey(new Text("1950"))
                .withInputValues(Arrays.asList(new IntWritable(23), new IntWritable(24)))
                .withOutput(new Text("1950"), new IntWritable(24))//配置期望的输出
                .runTest();
    }

    /**
     * 使用本地作业运行器运行作业
     */
    @Test
    public void testDriver() throws Exception {
        Configuration configuration = new Configuration();
        configuration.set("fs.default.name","file:///");//使用本地文件系统(明确说明使用本地文件系统，虽然去掉没什么区别，但是部署在集群的时候会有所差别)
        configuration.set("mapred.job.tracker","local");//使用本地作业调度器

        Path input = new Path("/home/lisheng/work/workspace/javaspace/HadoopSpace/数据样本/权威指南/气象数据/1902");
        Path output = new Path("/home/lisheng/work/workspace/javaspace/HadoopSpace/数据样本/权威指南/处理以后的数据/output5");

        FileSystem fileSystem = FileSystem.getLocal(configuration);
        fileSystem.delete(output,true);//确保输出目录不存在

        MaxTemperatureDriver driver = new MaxTemperatureDriver();
        driver.setConf(configuration);

        int exitCode = driver.run(new String[]{input.toString(),output.toString()});
        System.out.println(exitCode);
    }

}
