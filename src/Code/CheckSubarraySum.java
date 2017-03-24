package Code;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lisheng on 17-3-19.
 */
public class CheckSubarraySum {
    @SuppressWarnings("Duplicates")
    public static boolean checkSubarraySum(int[] nums, int k) {
        int length = nums.length;
        if(length ==1)
            return false;
        boolean flag = false;
        for(int i =0;i<length;i++) {
            int temp = 0;
            for (int j =i; j >=0; j--) {
                temp+=nums[j];
                if((temp==0&&k==0)||(k!=0&&temp %k ==0)){
                    if(i-j>=1) {
                        flag = true;
                        break;
                    }
                }
            }

        }
        return flag;
    }

    /**该问题可以不作为规划来解决，上面采用的是使用两层循环来解决相关的问题，但是会出现超时问题
     * 下面是参考答案所给出的解决问题的思路，首先要明白一个数学问题，假设存在一个数列，从i到j处的和为sum1，
     * 从i到h(h>j)处的和为sum2,且sum1%k==sum2%k，那么(sum2-sum1)%k=0;
     *其实还变相的考察这样的一个等式：如果(a1+..+an)%k=b,且a1+a2...+an>k,那么下面的等式也成立
     * (a1+a2+...+an+a(n+1))%k=(b)+a(n+1)%k,所以确定的说runningSum每次保留是从0到i数组的余数
     *
     * 下面的解决思路就是通过该思想来解决的，首先使用map存放历史数据，可以省去双重循环。
     * map中记录历史相同余数存放的位置。
     * 如果历史余数与当前余数相同且索引至少相差2，那么即可返回true;否则余数添加进入map
     * @param nums
     * @param k
     * @return
     */
    public static boolean checkSubarraySum1(int[] nums, int k){
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(){{put(0,-1);}};;
        int runningSum = 0;
        for (int i=0;i<nums.length;i++) {
            runningSum += nums[i];
            if (k != 0) runningSum %= k;
            Integer prev = map.get(runningSum);
            if (prev != null) {
                if (i - prev > 1) return true;
            }
            else map.put(runningSum, i);
        }
        return false;
    }
    @SuppressWarnings("Duplicates")
    public static boolean checkSubarraySum2(int[] nums, int k) {
        int length = nums.length;
        if(length ==1)
            return false;
        boolean flag = false;
        for(int i =0;i<length;i++) {
            int temp = 0;
            for (int j =i; j >=0; j--) {
                temp+=nums[j];
                if((temp==0&&k==0)||(k!=0&&temp %k ==0)){
                    if(i-j>=1) {
                        flag = true;
                        break;
                    }
                }
            }

        }
        return flag;
    }

    public static void main(String[] args) {
        int nums[]={3,0,5};
        int k =3;
        System.out.println(checkSubarraySum1(nums,k));
    }
}
