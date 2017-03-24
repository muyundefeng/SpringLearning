package Code;

/**
 * Created by lisheng on 17-2-7.
 */
public class Test1 {
    /**
     * @param a      input array
     * @param length array's length
     * @return max multi value of subsequence
     */
    static double MaxProductSubstring(double a[], int length) {
        double maxEnd = a[0];
        double minEnd = a[0];
        double maxResult = a[0];
        for (int i = 1; i < length; ++i) {
            double end1 = maxEnd * a[i], end2 = minEnd * a[i];
            maxEnd = max(max(end1, end2), a[i]);
            minEnd = min(min(end1, end2), a[i]);
            maxResult = max(maxResult, maxEnd);
        }
        return maxResult;
    }

    static double max(double a1, double a2) {
        return a1 > a2 ? a1 : a2;
    }

    static double min(double a1, double a2) {
        return a1 > a2 ? a2 : a1;
    }

    public static void main(String[] args) {
        double a[] = {1, -2, -3, 78, 4, 0, 5};
        System.out.println(MaxProductSubstring(a, a.length));
        System.out.println(MaxProductSubsequence(a, a.length));
    }

    static double MaxProductSubsequence(double array[], int length) {
        double maxResult = 0;
        for (int i = 0; i < length; i++) {
            double max = 1;
            for (int j = 0; j < length; j++) {
                if (i == j)
                    continue;
                else
                    max *= array[j];
            }
            maxResult = max > maxResult ? max : maxResult;
        }
        return maxResult;
    }
}
