package Code;

import java.util.*;

/**
 * Created by lisheng on 17-3-2.
 */
public class MainTest {

    public static void main(String[] args) {
        Map<String,String> map = new LinkedHashMap<>();
        map.put("dsad","dsadasda");
        map.put("sdasdad","sadd");
        System.out.println(map);
        map.put("dsad","dsadasda111");
        map.put("dsadasda","dsadasda111");
        map.put("1","123131");
        System.out.println(map.size());
        System.out.println(map.keySet());
        List<String> list = new ArrayList<>(map.keySet());
        System.out.println(list);
    }
}
