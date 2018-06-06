package ploy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuweifeng wrote on 2018/6/4.
 */
public class LineAdder {
    private int lines = 3;
    private int power = 5;

    private List<int[]> resultList = new ArrayList<>();

    private int[] resultArray;

    public List<int[]> lineAdd(int lines, int power) {
        resultArray = new int[lines];
        this.lines = lines;
        this.power = power;
        deal(0);
        return resultList;
    }

    private void deal(int m) {
        for (int i = 0; i <= power; i++) {
            resultArray[m] = i;
            if (m == lines - 1) {
                //如果找到一个解
                if (check()) {
                    print();
                    return;
                }
            } else {
                deal(m + 1);
            }
        }
    }

    /**
     * 判断是否符合结果
     *
     * @return 是否符合
     */
    private boolean check() {
        int total = 0;
        for (int one : resultArray) {
            total += one;
        }
        return power == total;
    }

    private void print() {
        for (int one : resultArray) {
            System.out.print(one);

        }
        System.out.print("\n");
        int[] temp = new int[resultArray.length];
        System.arraycopy(resultArray, 0, temp, 0, resultArray.length);
        resultList.add(temp);
    }
}
