package maxTemperature;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.Iterator;

/**一个reducer函数只能处理相同key值的数据
 * Text,IntWritable,Text,IntWritable 前两个参数分别为输入的键值对的类型，后两个类型输出键值对的类型
 * Created by lisheng on 17-3-28.
 */
public class MaxTemperatureReducer implements Reducer<Text,IntWritable,Text,IntWritable> {
    public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> outputCollector, Reporter reporter) throws IOException {
        int max =Integer.MAX_VALUE;
        while(values.hasNext()){
            max = Math.max(values.next().get(),max);
        }
        outputCollector.collect(new Text(key),new IntWritable(max));
    }

    public void close() throws IOException {

    }

    public void configure(JobConf jobConf) {

    }
//    @Override
//    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
//        int max =Integer.MAX_VALUE;
//        for(IntWritable value:values){
//            max = Math.max(value.get(),max);
//        }
//        context.write(key,new IntWritable(max));
//    }
}
