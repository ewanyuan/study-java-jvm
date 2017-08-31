package genericDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ewan on 28/08/2017.
 */
public class BoxingPerf {
    private static int sum (List<Integer> ints) {
        int s = 0;
        for (int n : ints) { s += n; }
        return s;
    }

    private static Integer sumInteger(List<Integer> ints) {
        Integer s = 0;
        for (Integer n : ints) { s += n; }
        return s;
    }

    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();
        for (Integer i = 0; i < 10000000; i++) {
            integerList.add(i);
        }

        long startTime = System.currentTimeMillis();
        sum(integerList);
        System.out.println("sum:"+(System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        sumInteger(integerList);
        System.out.println("sum:"+(System.currentTimeMillis() - startTime));
        //sum:unboxes each ele; sumInteger: unboxes each ele to perform the addition,then boxes each ele.
    }
}
