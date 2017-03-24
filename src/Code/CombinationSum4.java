package Code;

/**
 * Created by lisheng on 17-3-16.
 */
public class CombinationSum4 {

    public static int combinationSum4ByDp(int[] nums, int target) {
        int length = nums.length;
        int dp[] = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < length; j++) {
                if (i >= nums[j])
                    dp[i] += dp[i - nums[j]];
            }
        }
        return dp[target];
    }

    public static int combinationSum4ByRecurse(int[] nums, int target){
        if(target == 0)
            return 1;
        int res = 0;
        for(int i =0;i<nums.length;i++) {
            if (target >= nums[i])
                res += combinationSum4ByRecurse(nums, target - nums[i]);
        }
        return res;

    }
    public static void main(String[] args) {
        int nums[] = {1, 2, 3};
        int target = 32;
        System.out.println(combinationSum4ByDp(nums, target));
    }
}
