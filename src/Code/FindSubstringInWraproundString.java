package Code;


import java.util.Stack;

/**
 * Created by lisheng on 17-3-24.
 */
public class FindSubstringInWraproundString {

    public static int findSubstringInWraproundString(String s) {
        Stack<Integer> stack = new Stack<>();//使用堆栈存放相关的数据块大小
        stack.push(1);
        int length = s.length();

        for (int i = 1; i < length; i++) {
            if (s.substring(0, i - 1).contains(s.charAt(i) + "")) {
                if (s.charAt(i) - s.charAt(i - 1) == 1
                        || s.charAt(i) - s.charAt(i - 1) == -25) {
                    stack.push(stack.pop() + 1);
                } else {
                    stack.push(1);
                }
            }
        }
        int sum = 0;
        while (!stack.empty()) {
            int num = stack.pop();
            sum += statistic(num);
        }
        return sum;
    }

    public static int statistic(int a){
        int sum = 0;
        for(int i =1;i<=a;i++){
            sum+=i;
        }
        return sum;
    }

    /**关于本题的解法，首先明确该题解法中数组定义的含义，数组表示以某个字母结尾的最大值，
     * 首先明白为什么要这样做?
     * 比如说:acabc这样的字符串，总共的满足条件的字符有:a,c,b,ab,bc,abc这样的六个
     * 再考虑以字母c为结尾的字符串,有abc,bc,c(以字母c为结果的最大个数);以b字母为结尾的字符串有ab,b,以a字母为结尾的字符串有a.
     * 所以对于这个来说上面两个做法是等效的，所以使用数组保留某个字母结尾最大值。
     * 关于程序中循环部分，特别是if语句，还是上面的例子，如果p中存在连续的字母，那么对于最后一个字母来说肯定是前面个数的相加，比如
     * 对于c,c的数目为3,b的数目为2....
     *
     * @param p
     * @return
     */
    public static int findSubstringInWraproundString1(String p) {
        int length =p.length();
        int dp[] = new int[26];
        int curse = 0;
        for(int i =0;i<length;i++){
            if(i>0&&(p.charAt(i)-p.charAt(i-1)==1||p.charAt(i)-p.charAt(i-1)==-25)){
                curse++;
            }else {
                curse = 1;
            }
            int index = p.charAt(i)-'a';
            dp[index] = Math.max(dp[index],curse);
        }
        int sum = 0;
        for(int a:dp){
            sum+=a;
        }
        return sum;
    }
    public static void main(String[] args) {
        System.out.println(findSubstringInWraproundString1("cacacaca"));
    }
}
