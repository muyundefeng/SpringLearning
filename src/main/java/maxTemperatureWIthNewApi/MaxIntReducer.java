package maxTemperatureWIthNewApi;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;

/**使用新的api定义reducer函数
 * Created by lisheng on 17-4-5.
 */
public class MaxIntReducer extends Reducer<Text,IntWritable,Text,IntWritable>{

    private MultipleOutputs<Text, IntWritable> multipleOutputs;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        multipleOutputs = new MultipleOutputs<Text, IntWritable>(context);
    }

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int max = Integer.MIN_VALUE;
        for(IntWritable intWritable:values){
            max = Math.max(intWritable.get(),max);
        }
        //输出到多个文件,不使用context输出到文件,使用定义的multipleOutputs
        multipleOutputs.write(key,new IntWritable(max),"aa.txt");
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        multipleOutputs.close();
    }
}
