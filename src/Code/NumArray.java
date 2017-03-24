package Code;

/**
 * Created by lisheng on 17-2-27.
 */
public class NumArray {

    int nums[];


    public NumArray(int[] nums) {
        this.nums = nums;
    }

    public int sumRange(int i, int j) {
        int sum = 0;
        for(int h =i;h<=j;h++){
            sum+=nums[h];
        }
        return sum;
    }
}
