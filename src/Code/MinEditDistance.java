package Code;

/**
 * Created by lisheng on 17-2-19.
 */
public class MinEditDistance {

    static int minEditDistance(String source, String target) {
        int SLen = source.length();
        int TLen = target.length();
        int mep[][] = new int[SLen][TLen];
        for (int i = 0; i < SLen; i++) {
            mep[i][0] = i;
        }
        for (int j = 0; j < TLen; j++) {
            mep[0][j] = j;
        }
        for (int i = 1; i < SLen; i++) {
            for (int j = 1; j < TLen; j++) {
                if (source.charAt(i) == target.charAt(j)) {
                    mep[i][j] = mep[i - 1][j - 1];
                } else {
                    //mep[i][j] = min(mep[i - 1][j] + 1, mep[i][j - 1] + 1);没有"改"操作时的算法
                    mep[i][j] = min(mep[i-1][j-1]+1,min(mep[i - 1][j] + 1, mep[i][j - 1] + 1));
                }
            }
        }
        return mep[SLen - 1][TLen - 1];
    }

    static int min(int a, int b) {
        return a > b ? b : a;
    }

    public static void main(String[] args) {
//        String str = "ALGORITHM";
//        String str1 = "ALTRUISTIC";
        String str = "math";
        String str1 = "mouth";
        System.out.println(minEditDistance(str, str1));
    }
}
