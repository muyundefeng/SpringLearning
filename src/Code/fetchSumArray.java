package Code;

/**
 * Created by lisheng on 17-2-19.
 */
public class fetchSumArray {

    static int sumArrayFromGrid(int[][] inputArray, int width, int height) {
        int[][] sum = new int[width][height];
        sum[0][0] = inputArray[0][0];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 && j != 0)
                    sum[i][j] = sum[i][j - 1] + inputArray[i][j];
                else if (j == 0 && i != 0)
                    sum[i][j] = sum[i - 1][j] + inputArray[i][j];
                else if (j != 0 && i != 0)
                    sum[i][j] = max(sum[i - 1][j] + inputArray[i][j], sum[i][j - 1] + inputArray[i][j]);
            }
        }
        return sum[width - 1][height - 1];
    }

    static int max(int a, int b) {
        return a > b ? a : b;
    }

    public static void main(String[] args) {
        int a[][] = {
                {1, 2, 3},
                {2, 3, 10},
                {100, 3, 2}
        };
        System.out.println(sumArrayFromGrid(a, 3, 3));
    }

}
