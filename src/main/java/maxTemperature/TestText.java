package maxTemperature;

import org.apache.hadoop.io.Text;

/**
 * Created by lisheng on 17-3-30.
 */
public class TestText {
    public static void main(String[] args) {
        Text text = new Text("hello+");
        System.out.println(text.getLength());
        System.out.println(text.charAt(5));
        System.out.println(text.charAt(0));
        System.out.println(text.find("e"));
    }
}
