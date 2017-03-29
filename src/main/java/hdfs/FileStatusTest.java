package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.IOException;
import java.net.URI;


/**得到文件的status
 * Created by lisheng on 17-3-29.
 */
public class FileStatusTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        String path = "hdfs://localhost:9000/user/hadoop/myTest";
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(path), configuration,"hadoop");

        FileStatus[] status = fs.listStatus(new Path(path));
        Path[] paths = FileUtil.stat2Paths(status);
        for(Path path1:paths){
            System.out.println(path1);
        }

        //globStatus可以匹配一些正则表达式，筛选出符合条件的文件
        FileStatus[] status1 =fs.globStatus(new Path("/*"), new PathFilter() {
            public boolean accept(Path path) {
                return !path.toString().matches(".*user.*");//不匹配含有user
            }
        });
        Path[] paths1 = FileUtil.stat2Paths(status1);
        for(Path path1:paths1){
            System.out.println("+++"+path1);
        }
    }
}
