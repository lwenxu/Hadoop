package partition;

import flow.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

public class AreaPartition<KEY, VALUE> extends Partitioner<KEY, VALUE> {

    private static HashMap<String, Integer> areaMap = new HashMap<>();

    static {
        areaMap.put("134", 0);
        areaMap.put("135", 1);
        areaMap.put("136", 2);
        areaMap.put("137", 3);
        areaMap.put("138", 4);
        areaMap.put("139", 5);
        areaMap.put("151", 6);
    }

    @Override
    public int getPartition(KEY o, VALUE o2, int i) {
        int code = areaMap.get(o.toString().substring(0, 3)) == null ? 7 : areaMap.get(o.toString().substring(0, 3));
        System.out.println(code);
        return code;
    }
}
