package Code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lisheng on 17-3-9.
 */
public class LargestDivisibleSubset {

    public static List<Integer> largestDivisibleSubset(int nums[]) {
        List<Integer> integers = new ArrayList<>();
        if (nums == null || nums.length == 0) return integers;
        else {
            Arrays.sort(nums);
            int dp[] = new int[nums.length];//记录数组中每个元素应该属于的最长子序列
            dp[0] = 1;
            for (int i = 1; i < nums.length; i++)
                for (int j = i - 1; j >= 0; j--) {
                    if (nums[i] % nums[j] == 0)
                        dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            //找寻最长满足条件的最长子序列
            int maxValueIndex = 0;
            for (int i = 1; i < nums.length; i++) {
                maxValueIndex = dp[i] > dp[maxValueIndex] ? i : maxValueIndex;
            }
            int maxLength = dp[maxValueIndex];
            int temp = nums[maxValueIndex];
            for (int i = maxValueIndex; i >= 0; i--) {
                if (temp % nums[i] == 0 && dp[i] == maxLength) {
                    integers.add(nums[i]);
                    temp = nums[i];
                    maxLength--;
                }
            }
            return integers;
        }
    }

    public static void main(String[] args) {
        int arrays[] = {1};
        System.out.println(largestDivisibleSubset(arrays));
    }
}
