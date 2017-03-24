package Code;

/**
 * Created by lisheng on 17-3-3.
 */
public class LongestPalindromeSubseq {

    public static int longestPalindromeSubseq(String s) {
        int length = s.length();
        int dp[][] = new int[length][length];
        for (int i = length - 1; i >= 0; i--) {//?????
            dp[i][i] = 1;
            for (int j = i + 1; j < length; j++) {
                if (s.charAt(i) == s.charAt(j))
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                else
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
            }
        }
        return dp[0][length - 1];
    }


    public static int longestPalindromeSubseq2(String s) {
        int length = s.length();
        int dp[][] = new int[length][length];
        for (int i = 0; i < length; i++) {
            dp[i][i] = 1;
        }
        for (int i = 1; i < length; i++) {//?????
            for (int j = i - 1; j >= 0; j--) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[j][i] = dp[j + 1][i - 1] + 2;
                } else {

                    dp[j][i] = Math.max(dp[j + 1][i], dp[j][i - 1]);
                }
            }
        }
        return dp[0][length-1];
    }

    public static void main(String[] args) {
        String str = "cccffffffffgggrewreddd";
        String str1 = "vvv";
        System.out.println(longestPalindromeSubseq2(str1));
    }
}
