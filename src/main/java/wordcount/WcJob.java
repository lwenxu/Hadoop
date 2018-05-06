package wordcount;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URISyntaxException;

public class WcJob {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {

        // Configuration configuration = new Configuration();
        // configuration.set("fs.defaultFS", "hdfs://master:9000");
        // FileSystem fileSystem = FileSystem.get(configuration);
        // Path out = new Path("/wc/out");
        // if (fileSystem.exists(out)) {
        //     fileSystem.delete(out, true);
        // }

        // Configuration configuration = new Configuration();
        // configuration.set("hadoop.job.ugi","root");
        // System.setProperty("USER_NAME", "root");
        // System.out.println(System.getProperty("HADOOP_USER_NAME"));
        Job job = Job.getInstance();

        job.setReducerClass(WcReduce.class);
        job.setMapperClass(WcMap.class);

    //    指定 map 参数类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
    //    指定 reduce 参数类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        // MapReduce 的过程其实我们数据放在哪无所谓
        // 所以我们可以在 hdfs 或者在本地都可以
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
    // //    指定输入数据
    //     FileInputFormat.setInputPaths(job, new Path("hdfs://master:9000/wc/data/wc-input.txt"));
    // //    指定输出数据
    //     FileOutputFormat.setOutputPath(job, new Path("hdfs://master:9000/wc/out"));
        // 设置主类
        job.setJarByClass(WcJob.class);
        // 提交代码到集群
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}
