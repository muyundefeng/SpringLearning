package maxTemperatureWIthNewApi;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**使用新的api定义mapper函数类
 * Created by lisheng on 17-4-5.
 */
public class MaxIntMapper extends Mapper<LongWritable,Text,Text,IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String myValue = line.substring(2,4);
        context.write(new Text(key.toString()),new IntWritable(Integer.parseInt(myValue)));
    }
}
