package Code;

/**
 * Created by lisheng on 17-2-27.
 */
public class RobHouseByMaxMoney {
    /**
     * dp解决数组中不相邻元素最大和问题
     * 首先不能简单地将题目的简化为求解奇数与偶数最大值问题。
     * 首先定义数组maxV[i]表示到i为止，数组的最大和
     * 为了能够使用dp解决该问题，关键要写出相关的状态转移方程
     * maxV[i]=max(maxV[i-2]+num[i],maxV[i-1]),
     * @param nums
     * @return
     */
    public static int rob(int nums[]) {
        if (nums.length == 0)
            return 0;
        if (nums.length <= 1)
            return nums[0];
        int maxV[] = new int[nums.length];
        maxV[0] = nums[0];
        maxV[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++)
            maxV[i] = Math.max(maxV[i - 2] + nums[i], maxV[i - 1]);
        return maxV[nums.length - 1];
    }

    public static void main(String[] args) {
        int array[] = {7, 3, 5, 6, 7};
        System.out.println(rob(array));
    }
}
