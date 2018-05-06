package flow;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

// WritableComparable  这是两个接口的合并，一个是 hadoop 的序列化，另外一个是比较
public class FlowBean implements WritableComparable<FlowBean> {
    private String phone;
    private long up;
    private long down;
    private long sum;

    // 为了 hadoop 的反序列化使用反射技术，无参数的  因为下面为了方便构造对象
    public FlowBean() {
    }
    // 方便构造对象
    public FlowBean(String phone, long up, long down) {
        this.phone = phone;
        this.up = up;
        this.down = down;
        this.sum = this.down + this.up;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getUp() {
        return up;
    }

    public void setUp(long up) {
        this.up = up;
    }

    public long getDown() {
        return down;
    }

    public void setDown(long down) {
        this.down = down;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "FlowBean{" +
                "phone='" + phone + '\'' +
                ", up=" + up +
                ", down=" + down +
                ", sum=" + sum +
                '}';
    }

    // 按照 sum 排序
    @Override
    public int compareTo(FlowBean o) {
        return sum > o.sum ? -1 : 1;
    }

    // hadoop 的序列化和反序列化
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(phone);
        dataOutput.writeLong(up);
        dataOutput.writeLong(down);
        dataOutput.writeLong(sum);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        phone = dataInput.readUTF();
        up = dataInput.readLong();
        down = dataInput.readLong();
        sum = dataInput.readLong();
    }
}
