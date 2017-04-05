package sequenceFile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.net.URI;

/**
 * Created by lisheng on 17-3-31.
 */
public class SequenceFileWriteDemo {

    private static final String[] data = {
                    "Hadoop Common: The common utilities that support the other Hadoop modules.",
                    "Hadoop Distributed File System (HDFS™): A distributed file system that provides high-throughput access to application data."
            };

    public static void main(String[] args) throws IOException, InterruptedException {
        String uri = "hdfs://localhost:9000/user/hadoop/myTest/test4.txt";
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(uri),configuration,"hadoop");
        Path path = new Path(uri);

        IntWritable key = new IntWritable();
        Text value = new Text();
        SequenceFile.Writer writer = null;
        writer = SequenceFile.createWriter(fileSystem,configuration,path,key.getClass(),value.getClass());
        for(int i =0;i<100;i++){
            key.set(100-i);
            value.set(data[i%2]);
            System.out.printf("[%s]\t%s\t%s\n",writer.getLength(),key,value);
            writer.append(key,value);
        }
    }
}
