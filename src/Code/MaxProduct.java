package Code;

/**
 * Created by lisheng on 17-3-19.
 */
public class MaxProduct {
    public static int maxProduct(int[] nums) {
        int length = nums.length;
        int max = nums[0], min = nums[0], result = nums[0];
        for (int i = 1; i < length; i++) {
            int temp = max;
            max = Math.max(Math.max(max * nums[i], min * nums[i]), nums[i]);
            min = Math.min(Math.min(temp * nums[i], min * nums[i]), nums[i]);
            if (max > result)
                result = max;
        }
        return result;
    }

    public static void main(String[] args) {
        int nums[] = {-2, 3, -4};
        System.out.println(maxProduct(nums));
    }
}
