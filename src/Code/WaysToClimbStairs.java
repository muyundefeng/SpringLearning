package Code;

/**
 * Created by lisheng on 17-3-1.
 */
public class WaysToClimbStairs {

    public static int diffWaysToClimb(int steps) {
        int dp[] = new int[steps];
        if (steps == 1)
            return 1;
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 1; i < steps - 1; i++) {
            dp[i + 1] = dp[i] + dp[i - 1];
        }
        return dp[steps - 1];
    }

    public static void main(String[] args) {
        System.out.println(diffWaysToClimb(3));
    }
}
