package Code;

/**
 * Created by lisheng on 17-3-19.
 */
public class IsSubsequence {

    public static boolean isSubsequence(String s, String t) {
        if (s.length() == 0)
            return true;
        if (t.length() == 0)
            return false;
        boolean dp[] = new boolean[s.length()];
        if (t.contains(s.charAt(0) + ""))
            dp[0] = true;
        else
            dp[0] = false;
        t = t.substring(1);
        for (int i = 1; i < s.length(); i++) {
            if (dp[i - 1] && t.contains(s.charAt(i) + "")) {
                dp[i] = true;
                int start = t.indexOf(s.charAt(i));
                t = t.substring(start + 1);
            } else {
                dp[i] = false;
                break;
            }
        }
        return dp[s.length() - 1];
    }

    public static void main(String[] args) {
        System.out.println(isSubsequence("abc", "ahbgdc"));
    }
}
