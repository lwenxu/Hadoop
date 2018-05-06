package wordcount;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

//第一个参数就是行数从0开始 第二个参数是这一行的数据
//第三四参数输出的数据类型
public class WcMap extends Mapper<LongWritable, Text, Text, LongWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] strings = StringUtils.split(value.toString());
        for (String string : strings) {
            context.write(new Text(string), new LongWritable(1));
        }
    }
}
