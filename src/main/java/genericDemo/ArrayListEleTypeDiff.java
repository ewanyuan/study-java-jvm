package genericDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ewan on 28/08/2017.
 */

//结论：ArrayList可以装不同类型的元素
public class ArrayListEleTypeDiff {
    public static void main(String[] args) {
        List arrayList = new ArrayList();
        arrayList.add("aaaa");
        arrayList.add(100);

        //会输出为class java.lang.String
        System.out.println(arrayList.get(0).getClass());
        //会输出为class java.lang.Integer
        System.out.println(arrayList.get(1).getClass());
    }


}
