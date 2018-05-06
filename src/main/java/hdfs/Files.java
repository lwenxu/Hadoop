package hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Files {
    FileSystem fileSystem = null;

    @Before
    public void init() throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://master:9000");
        fileSystem = FileSystem.get(configuration);
    }

    @Test
    public void upload() throws IOException {
        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/Files.java"));
        InputStream fileInputStream = new FileInputStream("/Users/lwen/IdeaProjects/Hadoop/hdfs/src/main/java/hdfs/Files.java");
        IOUtils.copy(fileInputStream, fsDataOutputStream);
    }

    public static void main(String[] args) throws IOException {
        // FSDataInputStream fsDataInputStream = fileSystem.open(new Path("/start-hadoop.sh"));
        // FileOutputStream fileOutputStream = new FileOutputStream("test.xml");
        // // FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/test/mapred-env.cmd"));
        // IOUtils.copy(fsDataInputStream, fileOutputStream);
    }
}
