package Code;

/**@63. Unique Paths II
 * Created by lisheng on 17-3-14.
 */
public class UniquePathsWithObstacles {

    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int width = obstacleGrid.length;
        int height = obstacleGrid[0].length;
        int dp[][] = new int[width][height];
        boolean flag = false;
        for (int i = 0; i < width; i++) {
            int temp = obstacleGrid[i][0];
            if (temp == 1)
                flag = true;
            if (flag == true)
                dp[i][0] = 0;
            else
                dp[i][0] = 1;
        }
        flag = false;
        for (int i = 0; i < height; i++) {
            int temp = obstacleGrid[0][i];
            if (temp == 1)
                flag = true;
            if (flag == true)
                dp[0][i] = 0;
            else
                dp[0][i] = 1;
        }
        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                if (obstacleGrid[i][j] == 1)
                    dp[i][j] = 0;
                else
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[width - 1][height - 1];
    }

    public static void main(String[] args) {
        int array[][] = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        System.out.println(uniquePathsWithObstacles(array));
    }
}
