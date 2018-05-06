package flow;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowMapper extends Mapper<LongWritable, Text,FlowBean,NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] split = StringUtils.split(value.toString(),"\t");
        String phone = split[1];
        Long up = Long.parseLong(split[7]);
        Long down = Long.parseLong(split[8]);
        FlowBean flowBean = new FlowBean(phone, up, down);
        context.write(flowBean,NullWritable.get());
    }
}
