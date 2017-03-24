package Code;

/**
 * Created by lisheng on 17-3-5.
 */
public class NumDecodings {

    public static int numDecodings(String s) {
        if (s.isEmpty() || (s.length() > 1 && s.charAt(0) == '0')) return 0;
        int[] dp = new int[s.length()+1];
        dp[0] = s.charAt(0) == '0' ? 0 : 1;
        for (int i = 1; i <= s.length(); ++i) {
            dp[i] = (s.charAt(i - 1) == '0') ? 0 : dp[i - 1];
            if (i > 1 && (s.charAt(i - 2) == '1' || (s.charAt(i - 2) == '2' && s.charAt(i - 1) <= '6'))) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[s.length()];
    }

    public static void main(String[] args) {
        System.out.println(numDecodings("11"));
    }
}
