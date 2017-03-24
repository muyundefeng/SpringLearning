package Code;

import java.util.ArrayList;
import java.util.List;

/**(该题目还需要多思考)
 * Created by lisheng on 17-3-16.
 */
public class WordBreak {
    public static boolean wordBreak(String s, List<String> wordDict) {
        int length = s.length();
        boolean dp[] = new boolean[length + 1];
        dp[0] = true;
        for (int i = 1; i <= length; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[length];
    }

    public static void main(String[] args) {
        String s = "leetcode";
        List<String> dicts = new ArrayList<>();
        dicts.add("leet");
        dicts.add("code");
        System.out.println(wordBreak(s, dicts));
    }
}
