package Code;

/**
 * Created by lisheng on 17-2-27.
 */

/**
 * 自己第一次尝试解决方法
 */
public class FindMaxSubArray {
    public static int maxSubArray(int[] nums) {
        int length = nums.length;
        int sum[][] = new int[length][length];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < length; i++) {
            int temp = 0;
            for (int j = i; j < length; j++) {
                temp += nums[j];
                sum[i][j] = temp;
                if (sum[i][j] > max)
                    max = sum[i][j];
            }
        }
        return max;
    }

    /**
     * 使用动态规划解决最大子数组求和问题
     * 解决动态规划的关键问题是求得相关的状态转移方程。首先因为解决的是
     * 连续子数组问题，首先想到的是两层循环，如果采用两层循环，则完全可以不考虑
     * DP问题。正如上面的解题方法，但是当数组的维数过大时就会出现超时的问题。
     * 定义一个数组dp[i]，表示以A[i]结尾的数组之和。如果dp[i]>0，则dp[i+1]=dp[i]+A[i+1]
     * 因为dp[i]是大于零，所以需要这个dp[i]的结果。如果dp[i]<0,dp[i+1]=A[i]+0;
     * 因为dp[i-1]是小于零的，所以在继续加肯定更小，所以直接取零。
     * @param nums
     * @return
     */
    public static int maxSubArrayByDp(int[] nums) {
        int length = nums.length;
        int dp[] = new int[length];
        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 1; i < length; i++) {
            dp[i] = nums[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0);
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        int array[] = {1};
        System.out.println(maxSubArrayByDp(array));
    }
}
