package Code;

/**
 * Created by lisheng on 17-3-2.
 */
public class CollectNum {

    public static int collectNum(int grid[][]) {
        int length = grid[0].length;
        int width = grid.length;
        int dp[][] = new int[width][length];
        dp[0][0] = grid[0][0];
        int temp = 0;
        for (int i = 0; i < width; i++) {
            temp += grid[i][0];
            dp[i][0] = temp;
        }
        temp = 0;
        for (int i = 0; i < length; i++) {
            temp += grid[0][i];
            dp[0][i] = temp;
        }
        for (int i = 1; i < width; i++)
            for (int j = 1; j < length; j++) {
                dp[i][j] = Math.min(dp[i - 1][j] + grid[i][j], dp[i][j - 1] + grid[i][j]);
            }
        return dp[width - 1][length - 1];
    }

    public static void main(String[] args) {
        int a[][] = {{1, 2},
                {1,1}};
        System.out.println(collectNum(a));
    }
}
