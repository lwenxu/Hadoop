package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsConnect {

    private FileSystem fs = null;

    public HdfsConnect() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000");

        fs = FileSystem.get(new URI("hdfs://master:9000"), conf, "root");
    }

    /**
     * 删除HDFS 中指定的目录
     * @param path  需要删除的目录
     * @param is 是否进行递归删除文件
     * @throws IOException
     */
    public boolean delete(String path, boolean is) throws IOException {
        boolean res = true;
        if (exits(path)) {
            res = fs.delete(new Path(path), is);
            fs.close();
        }
        return res;
    }

    /**
     * 查看文件是否存在
     * @param path 文件路径
     * @return 如果文件存在，返回true,否则返回false
     * @throws IOException
     */
    public boolean exits(String path) throws IOException {
        boolean res = fs.exists(new Path(path));
        return res;
    }
}