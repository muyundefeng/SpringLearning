package Code;

/**
 * Created by lisheng on 17-3-7.
 */
public class NumTrees {

    public static int numTrees(int n){
        int dp[] = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for(int i =2;i<=n;i++){
            int tmp = 0;
            for(int j =0;j<i;j++){
                tmp+=dp[i-1-j]*dp[j];
            }
            dp[i]=tmp;
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(numTrees(1));
    }
}
