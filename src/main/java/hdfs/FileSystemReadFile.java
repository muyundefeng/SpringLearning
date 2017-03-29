package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.net.URI;

/**使用FileSystem api获得hdfs中文件的数据。
 *
 * Created by lisheng on 17-3-29.
 */
public class FileSystemReadFile {
    public static void main(String[] args) throws IOException {
        String path = "hdfs://localhost:9000/user/hadoop/LICENSE.txt";//访问文件的路径
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(path),configuration);
        FSDataInputStream inputStream = null;//FSDataInputStream实现了Seekable接口，可以实现定位操作
        inputStream = fileSystem.open(new Path(path));
        IOUtils.copyBytes(inputStream,System.out,4096,false);//文件指针移动移动到最后
        inputStream.seek(0);//移动文件指针到文件开始，重新读取该数据
        //inputStream.read()可以读取文件一部分数据
        System.out.println("++++++++++++++++++++++++++++++++++++++");
        IOUtils.copyBytes(inputStream,System.out,4096,false);

        IOUtils.closeStream(inputStream);
    }
}
