package Code;

/**
 * Created by lisheng on 17-2-28.
 */
public class BestSellAndBuy {

    public static void maxProfit(int[] prices) {
        int length = prices.length;
        int currentMin = prices[0];
        int maxProfict = 0;
        for (int i = 1; i < length; i++) {
            if (prices[i] > currentMin)
                maxProfict = Math.max(prices[i] - currentMin, maxProfict);
            else
                currentMin = prices[i];
        }
        System.out.println(maxProfict);
    }

    public static void main(String[] args) {
        int price[] = {7, 1, 5, 3, 6, 4};
        maxProfit(price);
    }
}
