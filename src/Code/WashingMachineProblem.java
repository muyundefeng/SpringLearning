package Code;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisheng on 17-2-21.
 */
public class WashingMachineProblem {

    public int solution(int array[]) {
        int times = 0;
        int sum = 0;
        bubbleSort(array);
        int length = array.length;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            sum += array[i];
            list.add(array[i]);
        }
        if (sum % length != 0) {
            return -1;
        }
        int avg = sum / length;
        boolean flag = true;
        for (Object a : list) {
            if ((Integer) a == avg)
                flag = flag || true;
            else
                flag = false;
        }
        if (flag == true)
            return 0;
        else {
            boolean dp[] = new boolean[length + 1];
            dp[0] = true;
            int start = 0;
            for (int i = 1; i < length + 1; i++) {
                for (int j = length - start; j > i; j--) {
                    if (list.get(j - 1) == avg && (j - 1) == length - start-1) {
                        dp[j] = true;
                        start++;
                        continue;
                    }
                    list.set(j - 1, list.get(j - 1) - 1);
                    int tmp = list.get(j - 2);
                    list.set(j - 2, list.get(j - 2) + 1);
                    if (tmp == 0)
                        break;

                }

                if (dp[i] == false)
                    times++;
                for(int h =length-start;h>0;h--){
                    if (list.get(h - 1) == avg && (h - 1) == length - start-1) {
                        dp[h] = true;
                        start++;
                        continue;
                    }
                }
                if (dp[i - 1] == true && list.get(i - 1) == avg) {
                    dp[i] = true;
                } else {
                    i = 0;
                }
            }
        }
        return times;
    }

    public void bubbleSort(int a[]) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int array[] = {4, 0, 0,4};
        WashingMachineProblem machineProblem = new WashingMachineProblem();
        System.out.println(machineProblem.solution(array));
    }

}
