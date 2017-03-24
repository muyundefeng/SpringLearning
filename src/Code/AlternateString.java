package Code;

/**
 * Created by lisheng on 17-2-20.
 */
public class AlternateString {

    public static boolean s1AndS2ToTarget(String s1, String s2, String s3) {
        int n = s1.length(), m = s2.length(), s = s3.length();
        if (n + m != s)
            return false;
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[0][0] = true;
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < m + 1; j++) {
                if (dp[i][j] || (i - 1 >= 0 && dp[i - 1][j] == true &&
                        s1.charAt(i - 1) == s3.charAt(i + j - 1)) ||
                        (j - 1 >= 0 && dp[i][j - 1] == true &&
                                s2.charAt(j - 1) == s3.charAt(i + j - 1)))
                    dp[i][j] = true;
                else
                    dp[i][j] = false;
            }
        }
        return dp[n][m];
    }

    public static void main(String[] args) {
        String s1 = "aabcc";
        String s2 = "dbbca";
        String s3 = "aadbbcbcac";
        System.out.println(s1AndS2ToTarget(s1, s2, s3));
    }
}
