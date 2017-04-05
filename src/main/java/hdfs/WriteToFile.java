package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Progressable;

import java.io.*;
import java.net.URI;

/**写入文件数据
 * Created by lisheng on 17-3-29.
 */
public class WriteToFile {
    public static void main(String[] args) throws IOException, InterruptedException {
        String path ="hdfs://localhost:9000/user/hadoop/myTest/test1.txt";
        String localPath = "/home/lisheng/桌面/a";
        InputStream inputStream = new BufferedInputStream(new FileInputStream(localPath));//读取本地文件

        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(path),configuration,"hadoop");//因为hdfs在hadoop用户之下，但是本程序实在lisheng用户目录之下运行，我并没有分配给权限给lisheng
        //所以在写入文件的时候需要指明写入文件的用户
        FSDataOutputStream outputStream = fileSystem.create(new Path(path), new Progressable() {
            public void progress() {
                System.out.println(".");
            }
        });
       /// IOUtils.copyBytes(inputStream,outputStream,4096,true);//复制完数据关闭流
        outputStream.writeUTF("content");
        outputStream.flush();
        FileStatus fileStatus = fileSystem.getFileStatus(new Path(path));
        System.out.println(fileStatus.getLen());//即使刷新之后，数据长度也是为零
    }
}
