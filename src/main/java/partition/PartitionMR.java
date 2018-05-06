package partition;

import flow.FlowBean;
import flow.FlowReducer;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 这个程序必须到集群上面跑  因为需要多个 reducer 本地跑不通
 * reduce 个数少于 partition 的个数抛异常，如果为 1 不会抛，因为是默认的
 * 过多产生空文件
 */
public class PartitionMR {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance();
        job.setJarByClass(PartitionMR.class);
        job.setReducerClass(PartitionReducer.class);
        job.setMapperClass(PartitionMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);
        // 设置 reduce 的并发数，每一个 reduce 就会产生一个结果文件
        // map 的结果具体被提交给哪个 reduce 任务是由 partition 来决定的
        job.setNumReduceTasks(10);
        job.setPartitionerClass(AreaPartition.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }

    public static class PartitionMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] strings = StringUtils.split(value.toString(), "\t");
            String phone = strings[1];
            Long up = Long.valueOf(strings[7]);
            Long down = Long.valueOf(strings[8]);
            context.write(new Text(phone), new FlowBean(phone, up, down));
        }
    }

    public static class PartitionReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
        @Override
        protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
            long up = 0;
            long down = 0;
            for (FlowBean flowBean : values) {
                up += flowBean.getUp();
                down += flowBean.getDown();
            }
            context.write(key, new FlowBean(key.toString(), up, down));
        }
    }


}
