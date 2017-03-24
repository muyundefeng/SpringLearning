package Code;

/**
 * Created by lisheng on 17-3-3.
 */
public class CountNumbersWithUniqueDigits {

    public static int countNumbers(int n) {
        int dp[] = new int[n+1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            if (i == 1 && i == 2)
                dp[i] = dp[i - 1] + (int) Math.pow(9, i);
            else {
                int end = 9 - i + 2;
                int multi = 9;
                for (int j = 9; j >= end; j--) {
                    multi *= j;
                }
                dp[i] = dp[i - 1] + multi;
            }

        }
        return  dp[n];
    }

    public static void main(String[] args) {
        System.out.println(countNumbers(3));
    }
}
