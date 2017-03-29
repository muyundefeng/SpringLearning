package hdfs;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by lisheng on 17-3-29.
 */

/**
 * 使用客户端访问hdfs的时候会出现类似于下面这样的错误：
 * Server IPC version 9 cannot communicate with client version 4
    主要是在pom文件中引入了hadoop-core包(hadoop 1.x)，在hadoop 2.x系列中不存在该jar包，被分成一些列的jar包(hdfs,mapreduce包)
 */
public class ReadFileByUri {
    static {
        //配置URL可以识别hadoop hdfs的地址，但是只能调用一次该方法，如果其他组件已经声明URLStreamHandlerFactory，那么该程序将无法访问hdfs文件系统
        //中的数据。
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
    }

    public static void main(String[] args) {
        String path = "hdfs://localhost:9000/user/hadoop/LICENSE.txt";
        InputStream inputStream = null;
        try {
            inputStream = new URL(path).openStream();
            IOUtils.copyBytes(inputStream,System.out,4096,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            IOUtils.closeStream(inputStream);
        }
    }
}
