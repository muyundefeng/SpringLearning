package Code;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisheng on 17-3-12.
 */
public class MinimumTotal {

    public static int minimumTotal(List<List<Integer>> triangle) {
        //获得三角形的高
        int height = triangle.size();
        List<List<Integer>> dp = new ArrayList<>();
        dp.add(triangle.get(0));
        for (int i = 1; i < height; i++) {
            List<Integer> hisDp = dp.get(i - 1);//上一行的数据
            List<Integer> dpRow = new ArrayList<>();
            List<Integer> row = triangle.get(i);//得到该行的数据
            for (int j = 0; j < row.size(); j++) {
                //找到该j处的上一层的相邻的数据
                Integer value = null;
                if (j >= 1 && j <= hisDp.size()-1)
                    value = row.get(j) + Math.min(hisDp.get(j), hisDp.get(j - 1));
                if (j == 0)
                    value = row.get(j) + hisDp.get(j);
                if (j == hisDp.size())
                    value = row.get(j) + hisDp.get(j - 1);
                dpRow.add(j, value);
            }
            dp.add(dpRow);
        }
        List<Integer> bottomDp = dp.get(height-1);
        int min = Integer.MAX_VALUE;
        for (Integer a : bottomDp) {
            min = a < min ? a : min;
        }
        return min;
    }

    public static void main(String[] args) {
        List<Integer> a= new ArrayList<>();
        a.add(2);
        List<Integer> a1= new ArrayList<>();
        a1.add(3);
        a1.add(4);
        List<Integer> a2= new ArrayList<>();
        a2.add(6);
        a2.add(5);
        a2.add(7);
        List<Integer> a3= new ArrayList<>();
        a3.add(4);
        a3.add(1);
        a3.add(8);
        a3.add(3);
        List<List<Integer>> list = new ArrayList<>();
        list.add(a);
        list.add(a1);
        list.add(a2);
        list.add(a3);
        System.out.println(minimumTotal(list));
    }
}
